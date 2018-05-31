package com.zhangrui.sentinel;

import redis.clients.jedis.Jedis;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义一个Redis哨兵客户端
 *
 * */
public class MySentinelServer {

    private String master;
    private List<String> slaveRedisLists = new ArrayList<String>(); //从服务器列表
    private List<String> badRedisLists = new ArrayList<String>(); //异常服务器列表

    public void config(String master){
        this.master = master;
        System.out.println("主服务器是： " + master);
    }


    public void checkSlave(){

        slaveRedisLists.removeAll(slaveRedisLists);

        String[] masterPort = master.split(":");
        Jedis jedis = new Jedis(masterPort[0], Integer.parseInt(masterPort[1]));
        String info = jedis.info("replication");

        String[] replications = info.split("\r\n");
        for (String replication : replications){
            if (replication.startsWith("slave")){
                String[] slaves = replication.split(":")[1].split(",");

                String slave = slaves[0].split("=")[1] + ":" + slaves[1].split("=")[1];
                if (!slaveRedisLists.contains(slave)){
                    slaveRedisLists.add(slave);
                }
            }
        }

        if (slaveRedisLists.size() > 0){
            System.out.println("从服务器信息： " + slaveRedisLists);
        }

    }

    public void checkBad(){
        if (badRedisLists.size() > 0){
            for (String bad : badRedisLists){
                String[] slavePort = bad.split(":");
                Jedis jedis = new Jedis(slavePort[0], Integer.parseInt(slavePort[1]));
                try{
                    //ping得通得话
                    String ping = jedis.ping();
                    String[] masterPort = master.split(":");
                    jedis.slaveof(masterPort[0], Integer.parseInt(masterPort[1]));
                    if (!slaveRedisLists.contains(bad)){
                        slaveRedisLists.add(bad);
                    }
                } catch (Exception e){

                } finally {
                    jedis.close();
                }

            }
        }
    }


    /**
     * 检测master是否有效
     */
    public void checkMaster(){

        String[] masterPort = master.split(":");
        Jedis jedis = new Jedis(masterPort[0], Integer.parseInt(masterPort[1]));
        try{
            jedis.ping(); //检测master是否正常
        } catch (Exception e){
            //不正常添加到异常列表中
            badRedisLists.add(master);
            //选举产生新得主服务器
            selectMaster();


        } finally {
            jedis.close();
        }

    }

    private void selectMaster() {

        if (slaveRedisLists.size() > 0){
            for (String slave : slaveRedisLists) {
                try{
                    String[] slavePort = slave.split(":");
                    Jedis jedis = new Jedis(slavePort[0], Integer.parseInt(slavePort[1]));
                    jedis.slaveofNoOne(); //选举为Master
                    jedis.close();
                    master = slave;
                    slaveRedisLists.remove(slave);
                    System.out.println("主服务器变更为： " + master);
                    break;
                } catch (Exception e){
                    if (!badRedisLists.contains(slave)){
                        badRedisLists.add(slave);
                    }
                }

            }
        }

        //将从服务器中变换主从关系
        if (slaveRedisLists.size() > 0){
            for (String slave : slaveRedisLists) {
                try{
                    String[] slavePort = slave.split(":");
                    String[] masterPort = master.split(":");
                    Jedis jedis = new Jedis(slavePort[0], Integer.parseInt(slavePort[1]));
                    jedis.slaveof(masterPort[0], Integer.parseInt(masterPort[1])); //选举为Master
                    jedis.close();

                    break;
                } catch (Exception e){
                    //badRedisLists.add(slave);
                }

            }

            System.out.println("从服务器为：" + slaveRedisLists);
        }
    }

    public void openSentinel(){
        Socket socket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(8888);

            while (true){
                socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                byte[] b = new byte[1024];
                inputStream.read(b);
                String read = new String(b);
                /**
                 * *3
                 * $8
                 * SENTINEL
                 * $23
                 * get-master-addr-by-name
                 * $8
                 * myMaster
                 */
                System.out.println(read);
                if (read.split("\r\n")[4].equals("get-master-addr-by-name")){
                    String reply = "*2\r\n$9\r\n127.0.0.1\r\n$4\r\n6380\r\n";
                    outputStream.write(reply.getBytes());
                }

                inputStream.close();
                outputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null){
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        final MySentinelServer sentinelServer = new MySentinelServer();
        sentinelServer.config("127.0.0.1:6380");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                sentinelServer.checkMaster();

                sentinelServer.checkSlave();

                sentinelServer.checkBad();
            }
        }, 2000, 2000);


        sentinelServer.openSentinel();
    }

}
