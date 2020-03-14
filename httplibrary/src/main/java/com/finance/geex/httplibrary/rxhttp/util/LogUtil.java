package com.finance.geex.httplibrary.rxhttp.util;

import android.content.Context;
import android.util.Log;

import com.finance.geex.httplibrary.rxhttp.Config;

import java.util.Map;

/**
 * Created on 2019/8/19 11:10.
 *
 * @author Geex302
 */
public class LogUtil {

    public static final String LOG_TAG_REQUEST ="request info";

    public static void v(String tag, String msg) {
        if (StringUtils.isEmpty(msg)) return;
        if (Config.DEBUG) {
            Log.v(tag, msg);
        }
    }
    public static void v(String tag, String msg, Throwable e) {
        if (StringUtils.isEmpty(msg)) return;
        if (Config.DEBUG) {
            Log.v(tag, msg,e);
        }
    }

    public static void d(String tag, String msg) {
        if (StringUtils.isEmpty(msg)) return;
        if (Config.DEBUG) {
            Log.d(tag, msg);
        }
    }
    public static void d(String tag, String msg, Throwable e) {
        if (StringUtils.isEmpty(msg)) return;
        if (Config.DEBUG) {
            Log.d(tag, msg,e);
        }
    }

    public static void i(String tag, String msg) {
        if (StringUtils.isEmpty(msg)) return;
        if (Config.DEBUG) {
            Log.i(tag, msg);
        }
    }
    public static void i(String tag, String msg, Throwable e) {
        if (StringUtils.isEmpty(msg)) return;
        if (Config.DEBUG) {
            Log.i(tag, msg,e);
        }
    }

//    private static boolean check(String str) {
//        return null == str || str.trim().length() == 0;
//    }

    public static void w(String tag, String msg) {
        if (StringUtils.isEmpty(msg)) return;
        if (Config.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable e) {
        if (StringUtils.isEmpty(msg)) return;
        if (Config.DEBUG) {
            Log.w(tag, msg,e);
        }
    }

    public static void e(String tag, String msg) {
        if (StringUtils.isEmpty(msg)) return;
        if (Config.DEBUG) {
            Log.e(tag, msg);
        }
    }


    public static void e(String tag, String msg, Throwable e) {
        if (StringUtils.isEmpty(msg)) return;
        if (Config.DEBUG) {
            Log.e(tag, msg,e);
        }
    }
    private static final String NET_URL = "NET_URL";



    public static void logUrl(String url, Map<String, String> params) {
        if (StringUtils.isEmpty(url)) return;
        if (!Config.DEBUG) {
            return;
        }
        if (null != params) {
            for (Map.Entry entry : params.entrySet()) {
                url += "&" + entry.getKey() + "=" + entry.getValue();
            }
            url = url.replaceFirst("&", "?");
        }
//        if (null != MyApplication.getInstance()) {
//            String token = SharedPreferencesUtils.getInstance().getString(SharedPreferencesUtils.TOKEN, "");
//            url += TextUtils.empty(token) ? "" : "&token=" + token;
//        }
        Log.e(NET_URL, url);
    }

    /**
     * 用于后期 友盟错误日志信息收集
     * @param context
     * @param error
     */
    public static void reportError(Context context, String error){

    }

    /**
     * 用于后期 友盟错误日志信息收集
     * @param context
     * @param e
     */
    public static void reportError(Context context, Throwable e){

    }

}
