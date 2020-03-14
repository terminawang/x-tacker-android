package com.finance.geex.statisticslibrary.exception;

import android.content.Context;
import android.util.Log;

import com.finance.geex.statisticslibrary.mananger.GeexPackage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created on 2019/9/6 15:24.
 * 未捕获崩溃异常处理
 * @author Geex302
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    //单例
    public static CrashHandler crashHandler = null;
    //系统默认异常处理器
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private Context mContext;


    private CrashHandler(){}

    public static CrashHandler getInstance() {

        if (crashHandler == null)
            crashHandler = new CrashHandler();

        return crashHandler;

    }

    public void init(Context context){

        //初始化操作
        //得到系统的应用异常处理器
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();

    }




    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        //发生未捕获的异常
        //获取手机设备相关信息

        //写入手机本地文件
        String errMessage = getErrMessage(ex);
        //写入数据库并上传
        GeexPackage.onAppCrash(errMessage);


        //交由系统默认处理
        //如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就由自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        }

    }

    private String getErrMessage(Throwable ex) {

        PrintWriter pw = null;
        Writer writer = new StringWriter();
        try {
            pw = new PrintWriter(writer);
            ex.printStackTrace(pw);
        } catch (Exception e) {
            return "";
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return writer.toString();

    }


}
