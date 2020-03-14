package com.finance.geex.statisticslibrary.upload.http;

import android.util.Base64;
import android.util.Log;

import com.finance.geex.statisticslibrary.GeexDataTracker;
import com.finance.geex.statisticslibrary.data.Constant;
import com.finance.geex.statisticslibrary.db.GeexDataBaseUtil;
import com.finance.geex.statisticslibrary.db.GeexDataBean;
import com.finance.geex.statisticslibrary.db.GeexErrDataBean;
import com.finance.geex.statisticslibrary.db.GeexNetworkRequestBean;
import com.finance.geex.statisticslibrary.upload.DataUploadService;
import com.finance.geex.statisticslibrary.util.RSAUtil;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.PublicKey;
import java.util.List;

import javax.crypto.Cipher;

/**
 * Created on 2019/8/21 10:36.
 * android原生网络请求库
 * @author Geex302
 */
public class HttpUtil {

    public static int upload(int strategy){

        //线程工作
//        boolean result = false;
        int result = 0;
        List<GeexDataBean> geexDataBeans = GeexDataBaseUtil.queryAll(DataUploadService.TABLE_EVENTS_DATA);
        if(strategy == DataUploadService.UPLOAD_STRATEGY_TWO) {
            //数据库记录大于某个数量上传
            if (geexDataBeans != null) {
                if (geexDataBeans.size() < DataUploadService.UPLOAD_STATISTICS_DATA_THRESHOLD_VALUE) {
                    return result;
                }
            } else {
                return result;
            }
        }

        //服务上传
        if(geexDataBeans != null){
            if(geexDataBeans.size() == 0){
                return result;
            }
        }else {
            return result;
        }


        //上传
        URL infoUrl = null;
//        InputStream inStream = null;
        HttpURLConnection httpConnection = null;
        PrintStream ps = null;

//        ByteArrayOutputStream baos = null;
//        ObjectOutputStream oos = null;
        OutputStream outputStream = null;

        try {
            //将集合数据变为byte
//            baos = new ByteArrayOutputStream();
//            oos = new ObjectOutputStream(baos);
//            oos.writeObject(geexDataBeans);
//            oos.flush();
//            byte[] trackerData = baos.toByteArray(); //byte
            Gson gson = new Gson();
            String dataString = gson.toJson(geexDataBeans);

            byte[] trackerData = dataString.getBytes(); //byte
            //对数据进行rsa加密
            //生成公钥
//            String publicKeyTxt = FileUtil.getStringByAssetsFile(GeexDataApi.mContext);
            PublicKey publicKey = RSAUtil.getPublicKey(RSAUtil.PUBLIC_KEY);
            byte[] encryptData = RSAUtil.rsaByPublicKey(trackerData, publicKey, Cipher.ENCRYPT_MODE);
//            byte[] encryptData = RSAUtil.encryptByPublicKeyForSpilt(trackerData, publicKeyTxt);
            if(encryptData == null){
                return result;
            }

            infoUrl = new URL(Constant.DataUrl.receiveData);
            URLConnection connection = infoUrl.openConnection();
            httpConnection = (HttpURLConnection) connection;
            ((HttpURLConnection) connection).setRequestMethod("POST");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setConnectTimeout(20*1000);
            httpConnection.setRequestProperty("Content-Type", "application/x-protobuf");
            httpConnection.setRequestProperty("Accept", "application/x-protobuf");
            outputStream = httpConnection.getOutputStream();

            //protobuf
            //生成protobuf的builder
            ByteString bytes = ByteString.copyFrom(encryptData);
            GeexDataTracker.DataTracker.Builder builder = GeexDataTracker.DataTracker
                    .newBuilder()
                    .setBody(bytes);
            //生成body 即在GeexData.proto中的byte字段值
            GeexDataTracker.DataTracker body = builder.build();
//            body.writeTo(outputStream);
            ps = new PrintStream(outputStream);
            ps.write(Base64.encode(body.toByteArray(),Base64.DEFAULT));
            ps.flush();



            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
//                inStream = httpConnection.getInputStream();
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(inStream, "utf-8"));
//                StringBuilder strber = new StringBuilder();
//                String line = null;
//                while ((line = reader.readLine()) != null){
//                    strber.append(line + "\n");
//                }
//                String s = strber.toString();

                result = geexDataBeans.size();

            }
        } catch (Exception e) {
            ThreadPoolUtil.threadPoolIsWork = false;
            e.printStackTrace();
        }finally {
            try {
                if(ps != null){
                    ps.close();
                }
                if(httpConnection != null){
                    httpConnection.disconnect();
                }
//                if(baos != null){
//                    baos.close();
//                }
//                if(oos != null){
//                    oos.close();
//                }
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return result;

    }


    /**
     * 上传网络请求日志
     * @param strategy
     * @return
     */
    public synchronized static int uploadNetworkLog(int strategy){

        //线程工作
        int result = 0;
        List<GeexNetworkRequestBean> geexNetworkRequestBeans = GeexDataBaseUtil.queryAll(DataUploadService.TABLE_NETWORK_REQUEST_DATA);
        if(strategy == DataUploadService.UPLOAD_STRATEGY_TWO) {
            //数据库记录大于某个数量上传
            if (geexNetworkRequestBeans != null) {
                if (geexNetworkRequestBeans.size() < DataUploadService.UPLOAD_NETWORK_DATA_THRESHOLD_VALUE) {
                    return result;
                }
            } else {
                return result;
            }
        }

        //服务上传
        if(geexNetworkRequestBeans != null){
            if(geexNetworkRequestBeans.size() == 0){
                return result;
            }
        }else {
            return result;
        }




        //上传
        URL infoUrl = null;
        HttpURLConnection httpConnection = null;
        PrintStream ps = null;
        OutputStream outputStream = null;

        try {

            Gson gson = new Gson();
            String dataString = gson.toJson(geexNetworkRequestBeans);

            //将集合数据变为byte
            byte[] trackerData = dataString.getBytes(); //byte
            //对数据进行rsa加密
            //生成公钥
            PublicKey publicKey = RSAUtil.getPublicKey(RSAUtil.PUBLIC_KEY);
            byte[] encryptData = RSAUtil.rsaByPublicKey(trackerData, publicKey, Cipher.ENCRYPT_MODE);
            if(encryptData == null){
                return result;
            }

            infoUrl = new URL(Constant.DataUrl.collectorRequest);
            URLConnection connection = infoUrl.openConnection();
            httpConnection = (HttpURLConnection) connection;
            ((HttpURLConnection) connection).setRequestMethod("POST");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setConnectTimeout(20*1000);
            httpConnection.setRequestProperty("Content-Type", "application/x-protobuf");
            httpConnection.setRequestProperty("Accept", "application/x-protobuf");
            outputStream = httpConnection.getOutputStream();

            //protobuf
            //生成protobuf的builder
            ByteString bytes = ByteString.copyFrom(encryptData);
            GeexDataTracker.DataTracker.Builder builder = GeexDataTracker.DataTracker
                    .newBuilder()
                    .setBody(bytes);
            //生成body 即在GeexData.proto中的byte字段值
            GeexDataTracker.DataTracker body = builder.build();
            ps = new PrintStream(outputStream);
            ps.write(Base64.encode(body.toByteArray(),Base64.DEFAULT));
            ps.flush();



            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                result = geexNetworkRequestBeans.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(ps != null){
                    ps.close();
                }
                if(httpConnection != null){
                    httpConnection.disconnect();
                }
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return result;

    }

    /**
     * 上传app crash信息
     * @return
     */
    public synchronized static int uploadAppCrashLog(){

        //线程工作
        int result = 0;
        List<GeexErrDataBean> geexErrDataBeans = GeexDataBaseUtil.queryAll(DataUploadService.TABLE_APP_CRASH_DATA);

        //服务上传
        if(geexErrDataBeans != null){
            if(geexErrDataBeans.size() == 0){
                return result;
            }
        }else {
            return result;
        }

        //上传
        URL infoUrl = null;
        HttpURLConnection httpConnection = null;
        PrintStream ps = null;
        OutputStream outputStream = null;

        try {

            Gson gson = new Gson();
            String dataString = gson.toJson(geexErrDataBeans);

            //将集合数据变为byte
            byte[] trackerData = dataString.getBytes(); //byte
            //对数据进行rsa加密
            //生成公钥
            PublicKey publicKey = RSAUtil.getPublicKey(RSAUtil.PUBLIC_KEY);
            byte[] encryptData = RSAUtil.rsaByPublicKey(trackerData, publicKey, Cipher.ENCRYPT_MODE);
            if(encryptData == null){
                return result;
            }

            infoUrl = new URL(Constant.DataUrl.collectorCrash);
            URLConnection connection = infoUrl.openConnection();
            httpConnection = (HttpURLConnection) connection;
            ((HttpURLConnection) connection).setRequestMethod("POST");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setConnectTimeout(20*1000);
            httpConnection.setRequestProperty("Content-Type", "application/x-protobuf");
            httpConnection.setRequestProperty("Accept", "application/x-protobuf");
            outputStream = httpConnection.getOutputStream();

            //protobuf
            //生成protobuf的builder
            ByteString bytes = ByteString.copyFrom(encryptData);
            GeexDataTracker.DataTracker.Builder builder = GeexDataTracker.DataTracker
                    .newBuilder()
                    .setBody(bytes);
            //生成body 即在GeexData.proto中的byte字段值
            GeexDataTracker.DataTracker body = builder.build();
            ps = new PrintStream(outputStream);
            ps.write(Base64.encode(body.toByteArray(),Base64.DEFAULT));
            ps.flush();



            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                result = geexErrDataBeans.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(ps != null){
                    ps.close();
                }
                if(httpConnection != null){
                    httpConnection.disconnect();
                }
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return result;

    }




}
