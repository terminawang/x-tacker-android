package com.finance.geex.statisticslibrary.page;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.finance.geex.statisticslibrary.mananger.GeexPackage;

/**
 * Created on 2019/8/13 14:51.
 * 获取activity,fragment信息
 * 1.从Application.ActivityLifecycleCallbacks的onActivityResumed／onActivityPaused这两个回调
 *   方法就可以分别得到Activity页面Show/Hide的时机，并在此时机上报相应页面事件
 * 2.交互归属的Activity页面可以通过view得到Context轻松获得
 * @author Geex302
 */
public class HookPage {

    //页面各种监听事件类
    private static ListenerInfo sListenerInfo;


    public static void init(Context context) {
        if (context == null) {
            return;
        }

        //监听所有Activity的生命周期
        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(getListenerInfo().createActivityLifecycleCallbacks());
    }

    private static ListenerInfo getListenerInfo() {
        if (sListenerInfo == null) {
            synchronized (ListenerInfo.class) {
                if (sListenerInfo == null) {
                    sListenerInfo = new ListenerInfo();
                }
            }
        }
        return sListenerInfo;
    }


    private static class ListenerInfo {

        Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks;


        private ListenerInfo() {
            mActivityLifecycleCallbacks = createActivityLifecycleCallbacks();

        }


        private Application.ActivityLifecycleCallbacks createActivityLifecycleCallbacks() {
            return new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

//                    String name = activity.getClass().getName();
//                    Log.d("wang", "onActivityCreated: " + name);

                }

                @Override
                public void onActivityStarted(Activity activity) {

                    String name = activity.getClass().getName();

                    //页面进入
                    GeexPackage.onEvent(name+"-onActivityStarted","页面进入");
                }

                @Override
                public void onActivityResumed(Activity activity) {
                }

                @Override
                public void onActivityPaused(Activity activity) {
                }

                @Override
                public void onActivityStopped(Activity activity) {
                    String name = activity.getClass().getName();
//                    Log.d("wang", "onActivityStopped: " + name);

                    //页面不在屏幕焦点。页面消失
                    GeexPackage.onEvent(name+"-onActivityStopped","页面消失");

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {



//                    Log.d("wang", "onActivityDestroyed: " + activity.getClass().getName());

                }
            };
        }



    }





}
