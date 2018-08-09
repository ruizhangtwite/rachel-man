package com.grpc.zr;

import com.grpc.zhangrui.GrpcReq;
import com.grpc.zhangrui.GrpcResp;
import com.grpc.zhangrui.GrpcServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Desp:
 * 2018-08-09 22:00
 * Created by zhangrui.
 */
public class GrpcServiceImpl extends GrpcServiceGrpc.GrpcServiceImplBase {
    @Override
    public void predict(GrpcReq request, StreamObserver<GrpcResp> responseObserver) {
        
        GrpcResp resp = GrpcResp.newBuilder().setCategory("我是从服务器传来的>>" + request.getContent()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
        
    }
    
}
