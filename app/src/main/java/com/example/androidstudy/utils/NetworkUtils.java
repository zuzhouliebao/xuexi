package com.example.androidstudy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class NetworkUtils {
    public static String get(String urlPath){
        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            URL url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); //设置请求方法
            connection.setConnectTimeout(8000); //设置链接超时的时间
//将读超时设置为指定的超时值，以毫秒为单位。用一个非零值指定在建立到资源的连接后从input流读入时的超时时间。
//如果在数据可读取之前超时期满，则会引发一个 java.net.sockettimeoutexception。超时时间为零表示无穷大超时。
            connection.setReadTimeout(8000);
            connection.setDoInput(true); //允许输入流，即允许下载
            connection.setDoOutput(true); //允许输出流，即允许上传
            connection.setUseCaches(false); //设置是否使用缓存

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null){
                    response.append(line);
                }
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.disconnect();
            }
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static StringBuffer getParamString(Map<String,String> params){
        StringBuffer result = new StringBuffer();
        Iterator<Map.Entry<String,String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> param = iterator.next();
            String key = param.getKey();
            String value = param.getValue();
            result.append(key).append('=').append(value);
            if (iterator.hasNext()){
                result.append('&');
            }
        }
        return result;
    }

    public static String post(String urlPath,Map<String, String> params){
        if (params == null || params.size() == 0){
            return get(urlPath);
        }
        OutputStream os = null;
        InputStream is = null;
        HttpURLConnection connection = null;
        StringBuffer body = getParamString(params);
        byte[] data = body.toString().getBytes();

        URL url = null;
        try {
            url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); //设置请求方法
            connection.setConnectTimeout(8000); //设置链接超时的时间
//将读超时设置为指定的超时值，以毫秒为单位。用一个非零值指定在建立到资源的连接后从input流读入时的超时时间。
//如果在数据可读取之前超时期满，则会引发一个 java.net.sockettimeoutexception。超时时间为零表示无穷大超时。
            connection.setReadTimeout(8000);
            connection.setDoInput(true); //允许输入流，即允许下载
            connection.setDoOutput(true); //允许输出流，即允许上传
            connection.setUseCaches(false); //设置是否使用缓存

            connection.setRequestProperty("content-Type","application/x-www-from-urlencoded");
            connection.setRequestProperty("content-Length",String.valueOf(data.length));
            os = connection.getOutputStream();
            os.write(data);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null){
                    response.append(line);
                }
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                connection.disconnect();
            }
        }
            return null;
    }

}
