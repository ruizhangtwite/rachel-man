package com.zhangrui.http;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Desp:封装OkHttp客户端
 * 2019-05-21 22:36
 * Created by zhangrui.
 */
public class HttpUtil {
    
    public void multipartPost(OkHttpClient httpClient, String url, Map<String, Object> data, Map<String, String> headers){
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        
        if (data != null){
            for (Map.Entry<String, Object> entry: data.entrySet()){
                Class<?> aClass = entry.getValue().getClass();
                if (String.class.equals(aClass)){
                    bodyBuilder.addPart(
                            Headers.of("Content-Disposition", "form-data; name=\""+ entry.getKey() +"\""),
                            RequestBody.create(null, entry.getValue().toString())
                    );
                }
                
                if (File.class.equals(aClass)){
                    bodyBuilder.addPart(
                            Headers.of("Content-Disposition", "form-data; name=\""+ entry.getKey() +"\""),
                            RequestBody.create(null, (File) entry.getValue())
                    );
                }
               
            }
        }
        MultipartBody body = bodyBuilder.build();

        Request.Builder requestBuilder = new Request.Builder().url(url)
                .post(body);

        if (headers != null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = requestBuilder.build();

        try {

            Response response = httpClient.newCall(request).execute();
        } catch (Exception e) {

        }



    }

    public void formPost(OkHttpClient httpClient, String url, Map<String, String> data, Map<String, String> headers) {

        FormBody.Builder builder = new FormBody.Builder();
        if (data != null) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }

        FormBody body = builder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url)
                .post(body);
        
        if (headers != null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        
        Request request = requestBuilder.build();
                
        try {

            Response response = httpClient.newCall(request).execute();
        } catch (Exception e) {
    
        }

    }

    public void jsonPost(OkHttpClient httpClient, String url, String content) {
        post(httpClient, url, "application/json;charset=utf-8", content);
    }

    private void post(OkHttpClient httpClient, String url, String mediaType, String content) {
        RequestBody body = RequestBody.create(MediaType.parse(mediaType), content);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {

            Response response = httpClient.newCall(request).execute();
        } catch (Exception e) {

        }
    }


    public Object get(OkHttpClient httpClient, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = httpClient.newCall(request).execute();
            String result = response.body().string();
            return result;
        } catch (Exception e) {

        }

        return null;
    }

    public void getSync(OkHttpClient httpClient, String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    response.body().string();
                }
            });
        } catch (Exception e) {

        }
    }
}
