package com.finance.geex.statisticslibrary.mananger;

import com.finance.geex.statisticslibrary.data.CommonData;
import com.finance.geex.statisticslibrary.db.GeexDataBaseUtil;
import com.finance.geex.statisticslibrary.db.GeexDataBean;
import com.finance.geex.statisticslibrary.db.GeexErrDataBean;
import com.finance.geex.statisticslibrary.db.GeexNetworkRequestBean;
import com.finance.geex.statisticslibrary.upload.DataUploadService;
import com.finance.geex.statisticslibrary.upload.http.HttpAppCrashCallable;
import com.finance.geex.statisticslibrary.upload.http.HttpStatisticsCallable;
import com.finance.geex.statisticslibrary.upload.http.HttpUploadLogCallable;
import com.finance.geex.statisticslibrary.upload.http.ThreadPoolUtil;
import com.finance.geex.statisticslibrary.util.AppUtil;
import com.finance.geex.statisticslibrary.util.TimeUtil;

/**
 * Created on 2019/8/20 15:25.
 * 数据组装
 * @author Geex302
 */
public class GeexPackage {

    /**
     * sdk_version  //sdk版本
     app_version  //APP版本

     osVersion;  //系统版本
     phone_brand  //手机品牌
     deviceFingerprint; //设备指纹
     screen_width  //屏幕宽
     screen_hight  //屏幕高
     uuid         //设备标识码
     platform;  //ios  android
     platName    //超即花  GeexPro
     network      //wifi 4g
     bundle_id    //包名

     eventName;  //事件名称
     eventType   //事件类型 点击  进入 消失
     startTime;  //开始时间 yyyy-MM-dd HH:mm:ss
     endTime;    //结束时间 yyyy-MM-dd HH:mm:ss

     mobile;      //手机号
     uid;         //uid

     longitude; //经度
     latitude;  //维度
     */


    public static void onEvent(String eventName,String eventType){

        GeexDataBean geexDataBean = new GeexDataBean();
        geexDataBean.setSdk_version(CommonData.getSdkVersion());
        geexDataBean.setApp_version(CommonData.getAppVersion(GeexDataApi.getAppContext()));
        geexDataBean.setOsVersion(CommonData.getOsVersion(GeexDataApi.getAppContext()));
        geexDataBean.setPhone_brand(CommonData.getPhoneBrand(GeexDataApi.getAppContext()));
        geexDataBean.setDeviceFingerprint(CommonData.getIMEI(GeexDataApi.getAppContext()));
        geexDataBean.setScreen_width(CommonData.getScreenWidth(GeexDataApi.getAppContext()));
        geexDataBean.setScreen_hight(CommonData.getScreenHeight(GeexDataApi.getAppContext()));
        geexDataBean.setUuid(CommonData.getDeviceUuid(GeexDataApi.getAppContext()));
        geexDataBean.setPlatform(CommonData.getPlatform());
        geexDataBean.setPlatName(CommonData.getAppName(GeexDataApi.getAppContext()));
        geexDataBean.setNetwork(CommonData.getNetworkType(GeexDataApi.getAppContext()));
        geexDataBean.setBundle_id(CommonData.getPackageName(GeexDataApi.getAppContext()));
        geexDataBean.setEventName(eventName);
        geexDataBean.setEventType(eventType);
        geexDataBean.setStartTime(TimeUtil.longToString(System.currentTimeMillis(), "yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        geexDataBean.setEndTime(TimeUtil.longToString(System.currentTimeMillis(), "yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        geexDataBean.setMobile(CommonData.mMobile);
        geexDataBean.setUid(CommonData.mUid);
        geexDataBean.setLatitude(CommonData.getLatitude());
        geexDataBean.setLongitude(CommonData.getLongitude());

        //存入数据库
        GeexDataBaseUtil.insertSingleData(DataUploadService.TABLE_EVENTS_DATA,geexDataBean);

        //上传
        if(AppUtil.isNetworkAvailable(GeexDataApi.mContext) && !ThreadPoolUtil.threadPoolIsWork){
            try {
                ThreadPoolUtil.threadPoolIsWork = true;
                ThreadPoolUtil.submit(ThreadPoolUtil.GEEX_EVENTS_THREAD_POOL,new HttpStatisticsCallable(DataUploadService.UPLOAD_STRATEGY_TWO));//1标识请求上传来自当数据库大于10条

            } catch (Exception e) {
                e.printStackTrace();
                ThreadPoolUtil.threadPoolIsWork = false;
            }

        }

    }

    public static void onNetworkRequest(GeexNetworkRequestBean geexNetworkRequestBean){

        geexNetworkRequestBean.setSdk_version(CommonData.getSdkVersion());
        geexNetworkRequestBean.setApp_version(CommonData.getAppVersion(GeexDataApi.getAppContext()));
        geexNetworkRequestBean.setPlatform(CommonData.getPlatform());
        geexNetworkRequestBean.setPlatName(CommonData.getAppName(GeexDataApi.getAppContext()));
        geexNetworkRequestBean.setMobile(CommonData.mMobile);
        geexNetworkRequestBean.setNetwork(CommonData.getNetworkType(GeexDataApi.getAppContext()));
        geexNetworkRequestBean.setUuid(CommonData.getDeviceUuid(GeexDataApi.getAppContext()));

        //存入数据库
        GeexDataBaseUtil.insertSingleData(DataUploadService.TABLE_NETWORK_REQUEST_DATA,geexNetworkRequestBean);


        //上传
        ThreadPoolUtil.submit(ThreadPoolUtil.GEEX_NORMAL_THREAD_POOL,new HttpUploadLogCallable(DataUploadService.UPLOAD_STRATEGY_TWO));

    }

    public static void onAppCrash(String errMessage){

        GeexErrDataBean geexErrDataBean = new GeexErrDataBean();
        geexErrDataBean.setCrash_reason(errMessage);
        geexErrDataBean.setCrash_time(TimeUtil.longToString(System.currentTimeMillis(), "yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        geexErrDataBean.setApp_version(CommonData.getAppVersion(GeexDataApi.getAppContext()));
        geexErrDataBean.setCpu_abi(CommonData.getCpuAbi());
        geexErrDataBean.setMobile(CommonData.mMobile);
        geexErrDataBean.setPhone_brand(CommonData.getPhoneBrand(GeexDataApi.getAppContext()));
        geexErrDataBean.setPlatform(CommonData.getPlatform());
        geexErrDataBean.setPlatName(CommonData.getAppName(GeexDataApi.getAppContext()));
        geexErrDataBean.setSdk_version(CommonData.getSdkVersion());
        geexErrDataBean.setApp_launch_time(TimeUtil.longToString(CommonData.getAppLaunchTime(), "yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        geexErrDataBean.setUuid(CommonData.getDeviceUuid(GeexDataApi.getAppContext()));

        //存入数据库
        GeexDataBaseUtil.insertSingleData(DataUploadService.TABLE_APP_CRASH_DATA,geexErrDataBean);

        //上传
        ThreadPoolUtil.submit(ThreadPoolUtil.GEEX_NORMAL_THREAD_POOL,new HttpAppCrashCallable(DataUploadService.UPLOAD_STRATEGY_TWO));


    }


}
