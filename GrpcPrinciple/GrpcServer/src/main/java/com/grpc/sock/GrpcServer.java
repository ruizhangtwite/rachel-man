package com.grpc.sock;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Desp: Socket用于传输Service
 * 2018-08-09 23:19
 * Created by zhangrui.
 */
public class GrpcServer {
    
    private ServerSocket serverSocket;
    private int port;
    private Object obj;
    
    public GrpcServer(Object obj, int port){
        this.obj = obj;
        this.port = port;
    }

    public void start() throws  Exception{
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        while (true){
            try {
                socket = serverSocket.accept();
                InputStream i = socket.getInputStream();
                ObjectInputStream objectStream = new ObjectInputStream(i);
                GrpcRequest request = (GrpcRequest) objectStream.readObject();
                
                Object o = invoke(request);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(o);
                
                outputStream.flush();
                
                outputStream.close();
                objectStream.close();
                
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                if (socket != null){
                    socket.close();
                }
                
            }
        }
    }
    
    private Object invoke(GrpcRequest request) throws  Exception{
        String className = request.getClassName();
        String methodName = request.getMethodName();
        Object[] params = request.getParams();
        Class<?>[] types = new Class[params.length];
        for (int i = 0; i< params.length; i++){
            types[i] = params[i].getClass();
        }
        Method method = Class.forName(className).getMethod(methodName, types);
        return method.invoke(obj, params);
    }
    
}
