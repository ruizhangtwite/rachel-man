package com.grpc.zr;

import com.grpc.zhangrui.GrpcReq;
import com.grpc.zhangrui.GrpcResp;
import com.grpc.zhangrui.GrpcServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Desp:
 * 2018-08-09 22:11
 * Created by zhangrui.
 */
public class MyClient {
    
    private ManagedChannel channel;
    private GrpcServiceGrpc.GrpcServiceBlockingStub blockingStub;
    
    public MyClient(String host, int port){
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
        blockingStub = GrpcServiceGrpc.newBlockingStub(channel);
    }
    
    public void shutdown() throws InterruptedException {
        if (channel != null){
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        }
    }
    
    public void predit(String category){
        GrpcReq req = GrpcReq.newBuilder().setContent("客户端的>>" + category).build();
        GrpcResp resp = blockingStub.predict(req);
        System.out.println(resp.getCategory());
    }

    public static void main(String[] args) throws Exception{
        MyClient client = new MyClient("127.0.0.1", 8888);
        client.predit("第一个Grpc");
        client.shutdown();
    }
}
