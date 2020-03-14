package com.finance.geex.statisticslibrary.upload.http;


import android.util.Log;

import com.finance.geex.statisticslibrary.db.GeexDataBaseUtil;
import com.finance.geex.statisticslibrary.db.GeexDataBean;
import com.finance.geex.statisticslibrary.upload.DataUploadService;
import com.finance.geex.statisticslibrary.util.TimeUtil;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created on 2019/8/21 13:45.
 * net请求后 返回数据
 * @author Geex302
 */
public class HttpStatisticsCallable implements Callable<String> {


    private int message;

    public HttpStatisticsCallable(){


    }

    public HttpStatisticsCallable(int message){
        this.message = message;
    }



    @Override
    public String call() throws Exception {

        //执行的耗时任务
        int result = HttpUtil.upload(message); //json串
        //若数据上传成功，则删除数据库
        if(result != 0){ //数据上传成功
            //上传数据成功则删除数据
            GeexDataBaseUtil.deleteBeforeSize(DataUploadService.TABLE_EVENTS_DATA, result);
        }

        ThreadPoolUtil.threadPoolIsWork = false;

        return null;
    }
}
