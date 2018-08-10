package com.grpc.sock;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Desp:
 * 2018-08-09 23:41
 * Created by zhangrui.
 */
public class GrpcClient {

    private String host;
    private int port;

    public GrpcClient(String host, int port){
        this.host = host;
        this.port = port;
    }
    
    public Object invoke(GrpcRequest request) throws Exception {
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        try {
            socket = new Socket(host, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);

            inputStream = new ObjectInputStream(socket.getInputStream());
            Object o = inputStream.readObject();
            return o;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
        
        return null;
    }
       
   
}
