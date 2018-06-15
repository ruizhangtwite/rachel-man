package com.zhangrui.webservices.endpoint;

import com.zhangrui.webservices.domain.HumanRequest;
import com.zhangrui.webservices.domain.HumanResponse;
import com.zhangrui.webservices.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Desp: Human的EndPoint
 * 2018-06-15 18:59
 * Created by zhangrui.
 */

@Endpoint
public class HumanEndPoint {
    
    @Autowired
    private HumanRepository repository;
    
    
    @PayloadRoot(localPart = "HumanRequest", namespace = "http://com.zhangrui/com.zhangrui.webservices")
    @ResponsePayload
    public HumanResponse getHuman(@RequestPayload HumanRequest request){
        HumanResponse response = repository.getResponse(request);
        System.out.println("我是服务器：" + response);
        return response;
    }
}
