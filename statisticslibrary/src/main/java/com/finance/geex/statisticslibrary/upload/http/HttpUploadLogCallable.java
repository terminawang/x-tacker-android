package com.finance.geex.statisticslibrary.upload.http;

import android.util.Log;

import com.finance.geex.statisticslibrary.db.GeexDataBaseUtil;
import com.finance.geex.statisticslibrary.upload.DataUploadService;

import java.util.concurrent.Callable;

/**
 * Created on 2019/9/10 13:51.
 * 上传网络请求log Runnable
 * @author Geex302
 */
public class HttpUploadLogCallable implements Callable<String> {

    private int message;

    public HttpUploadLogCallable(){


    }

    public HttpUploadLogCallable(int message){
        this.message = message;
    }


    @Override
    public String call() throws Exception {

        //HttpUploadLogCallable对象中的这个代码块只能有一个线程进入
        // (每次new HttpUploadLogCallable会产生新的对象，故锁不住。所以用Object锁)
        synchronized (Object.class){

            int result = HttpUtil.uploadNetworkLog(message);
            if(result != 0){
                GeexDataBaseUtil.deleteBeforeSize(DataUploadService.TABLE_NETWORK_REQUEST_DATA,result);
            }

        }



        return null;
    }
}
