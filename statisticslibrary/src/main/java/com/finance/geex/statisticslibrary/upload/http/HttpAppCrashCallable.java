package com.finance.geex.statisticslibrary.upload.http;

import android.util.Log;

import com.finance.geex.statisticslibrary.db.GeexDataBaseUtil;
import com.finance.geex.statisticslibrary.upload.DataUploadService;

import java.util.concurrent.Callable;

/**
 * Created on 2019/9/17 14:10.
 * 上传APP CRASH信息
 * @author Geex302
 */
public class HttpAppCrashCallable implements Callable<String> {


    private int message;

    public HttpAppCrashCallable(){


    }

    public HttpAppCrashCallable(int message){
        this.message = message;
    }

    @Override
    public String call() throws Exception {

        synchronized (Object.class){
            int result = HttpUtil.uploadAppCrashLog();
            if(result != 0){
                GeexDataBaseUtil.deleteBeforeSize(DataUploadService.TABLE_APP_CRASH_DATA,result);
            }
        }



        return null;
    }
}
