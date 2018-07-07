package com.zhangrui.tomcat;

import com.zhangrui.tomcat.http.ReponseFactory;
import com.zhangrui.tomcat.http.RequestFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.Socket;

/**
 * Desp:
 * 2018-05-20 0:15
 * Created by zhangrui.
 */
public class StandSocketAdaptor {

    public void service(Socket socket) throws Exception{

        HttpServletRequest request = (HttpServletRequest) RequestFactory.getServletRequest(socket);

        HttpServletResponse response = (HttpServletResponse) ReponseFactory.getServletResponse(socket);

        StandEngine.standHost.doDispatch(request, response);

    }
}
