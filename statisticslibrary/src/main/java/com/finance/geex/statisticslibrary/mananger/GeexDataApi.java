package com.finance.geex.statisticslibrary.mananger;

import android.content.Context;
import android.content.Intent;

import com.finance.geex.statisticslibrary.data.CommonData;
import com.finance.geex.statisticslibrary.data.Constant;
import com.finance.geex.statisticslibrary.exception.CrashHandler;
import com.finance.geex.statisticslibrary.page.HookPage;
import com.finance.geex.statisticslibrary.upload.DataUploadService;
import com.finance.geex.statisticslibrary.upload.http.HttpAppCrashCallable;
import com.finance.geex.statisticslibrary.upload.http.HttpUploadLogCallable;
import com.finance.geex.statisticslibrary.upload.http.ThreadPoolUtil;

/**
 * Created on 2019/8/13 13:22.
 * 获取配置等信息
 * @author Geex302
 */
public class GeexDataApi {

    //appContext
    public static Context mContext;

    //启动时间
    public static long appLaunchTime;


    public static void init(Context context,boolean debug){

        appLaunchTime = System.currentTimeMillis();

        mContext = context;

        //环境
        Constant.isDebug = debug;

        //崩溃日志手机初始化
        CrashHandler.getInstance().init(mContext);


        //注册监听activity页面
        HookPage.init(mContext);

        //初始化网络请求库
//        RetrofitHelper.init(mContext);
        //初始化
//        RetrofitHelper.getInstance()
//                .setDebug(true)
//                .setConnectTimeout(30)
//                .setReadTimeout(30)
//                .setWriteTimeout(30)
//                .setBaseUrl("http://ip.taobao.com/service/")

                //添加token拦截器
//                .addTokenInterceptor(new TokenInterceptor())


        //通用数据中心初始化
        CommonData.init(mContext);





        try {

            //上传数据，若有数据
            //网络请求日志
            ThreadPoolUtil.submit(ThreadPoolUtil.GEEX_NORMAL_THREAD_POOL,new HttpUploadLogCallable(DataUploadService.UPLOAD_STRATEGY_TWO));
            //app crash日志
            ThreadPoolUtil.submit(ThreadPoolUtil.GEEX_NORMAL_THREAD_POOL,new HttpAppCrashCallable(DataUploadService.UPLOAD_STRATEGY_TWO));

            //启动定时服务上传数据
            Intent intent = new Intent(mContext, DataUploadService.class);
            mContext.startService(intent);

        }catch (Exception e){

        }





    }


    //获取app context实例
    public static Context getAppContext() {
        if (mContext == null)
            return null;
        return mContext.getApplicationContext();
    }


}
