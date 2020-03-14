package com.finance.geex.statisticslibrary.upload;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.finance.geex.statisticslibrary.data.CommonData;
import com.finance.geex.statisticslibrary.mananger.GeexDataApi;
import com.finance.geex.statisticslibrary.upload.http.HttpStatisticsCallable;
import com.finance.geex.statisticslibrary.upload.http.ThreadPoolUtil;
import com.finance.geex.statisticslibrary.util.AppUtil;

/**
 * Created on 2019/8/15 15:53.
 * 数据上传Service
 *
 * @author Geex302
 */
public class DataUploadService extends Service {

    /**
     * 1.每隔一段时间上传
     * 2.数据库记录大于某个数量上传
     * <p>
     * 1、2 结合上传
     */

    //时间间隔 time interval
    public static final int TIME_INTERVAL = 30 * 1000; //30秒
    public static final int UPLOAD_STRATEGY_ONE = 1; //每隔一段时间上传
    public static final int UPLOAD_STRATEGY_TWO = 2; //数据库记录大于某个数量上传  threshold value
    public static final int UPLOAD_STATISTICS_DATA_THRESHOLD_VALUE = 5; //埋点数据库上传阈值
    public static final int UPLOAD_NETWORK_DATA_THRESHOLD_VALUE = 3; //网络请求数据库上传阈值

    //区分表 1:埋点表 2:网络数据请求表 3:app崩溃
    public static final int TABLE_EVENTS_DATA = 1;
    public static final int TABLE_NETWORK_REQUEST_DATA = 2;
    public static final int TABLE_APP_CRASH_DATA = 3;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //上传数据
        //获取数据库数据

        //上传数据
        try {

            //获取经纬度(每隔一段时间获取一次经纬度)
            CommonData.getAndroidSdkLocation(GeexDataApi.mContext);

            if (AppUtil.isNetworkAvailable(GeexDataApi.mContext) && !ThreadPoolUtil.threadPoolIsWork) {
                ThreadPoolUtil.threadPoolIsWork = true;
                ThreadPoolUtil.submit(ThreadPoolUtil.GEEX_EVENTS_THREAD_POOL,new HttpStatisticsCallable(UPLOAD_STRATEGY_ONE));//0标识上传操作来自服务每隔1分钟上传
            }

        } catch (Exception e) {
            e.printStackTrace();
            ThreadPoolUtil.threadPoolIsWork = false;
        }

        //AlarmManager 定时任务
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long triggerTime = SystemClock.elapsedRealtime() + TIME_INTERVAL;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);


        return super.onStartCommand(intent, flags, startId);
    }
}
