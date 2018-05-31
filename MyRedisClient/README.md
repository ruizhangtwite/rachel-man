
1、Redis遵循的协议是RESP协议
com.zhangrui.redis包是为了测试Redis协议，为Redis哨兵业务的编写提供依据
com.zhangrui.sentinel包是哨兵机制的模拟，并测试

2、Redis哨兵业务的编写

1）启动定时任务
2）轮询检测Master，ping()不正常的话加入到列表中，并通过选举的方式选择第一个Slave
服务器作为主服务器Master（slaveofNoOne方法），其他从服务器通过slaveOf()方法切换到新的Master
3）轮询获取Master的从服务器信息info("replication"),更新Slave列表
4）轮询Bad列表中的服务器是否正常启动，如果正常启动就切换到新的Master


3、测试应用
1）开启一个ServerSocket，用于监控JedisSentinelPool中生成的Jedis实例

注意点：借用jedis，操作Redis，必须提供回应机制，否则将报错


