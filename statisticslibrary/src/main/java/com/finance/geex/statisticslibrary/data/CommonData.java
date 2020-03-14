package com.finance.geex.statisticslibrary.data;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.WindowManager;

import com.finance.geex.statisticslibrary.mananger.GeexDataApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.LOCATION_SERVICE;
import static com.finance.geex.statisticslibrary.util.PermissionUtils.checkHasPermission;

/**
 * Created on 2019/8/15 13:36.
 * 类似经纬度、imei等通用数据获取
 *
 * @author Geex302
 */
public class CommonData {


    /**
     * sdk_version  //sdk版本
     * app_version  //APP版本
     * <p>
     * osVersion;  //系统版本
     * phone_brand  //手机品牌
     * deviceFingerprint; //设备指纹
     * screen_width  //屏幕宽
     * screen_hight  //屏幕高
     * uuid         //设备标识码
     * platform;  //ios  android
     * platName    //超即花  GeexPro
     * network      //wifi 4g
     * bundle_id    //包名
     * <p>
     * eventName;  //事件名称
     * eventType   //事件类型 点击  进入 消失
     * startTime;  //开始时间 yyyy-MM-dd HH:mm:ss
     * endTime;    //结束时间 yyyy-MM-dd HH:mm:ss
     * <p>
     * mobile;      //手机号
     * uid;         //uid
     * <p>
     * longitude; //经度
     * latitude;  //维度
     */

    private static String sLongitude = ""; // 经度
    private static String sLatitude = ""; // 纬度
    private static String imei = ""; //imei
    private static String deviceUuid = ""; //deviceUuid
    private static String ip = "";
    public static String mMobile = "";
    public static String mUid = "";



    private static LocationListener sLocationListener;

    /**
     * 初始化data数据
     *
     * @param context
     */
    public static void init(Context context) {

        //注册经纬度监听,获取经纬度
        try {

//            registerLocationListener(context);

            getAndroidSdkLocation(context);

            //获取ip地址
//            getIp();

        } catch (Exception e) {


        }

    }

    /**
     * 获取经纬度
     */
    @SuppressLint("MissingPermission")
    private static void registerLocationListener(Context context) {

        // 监听地理位置变化,并记录位置信息
        final LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 判断网络定位功能是否可用
        if (lm == null
                || !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                || !checkHasPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)) {
            return;
        }

        if (sLocationListener == null) {
            sLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        sLongitude = String.valueOf(location.getLongitude());
                        sLatitude = String.valueOf(location.getLatitude());

                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(String s) {
                }

                @Override
                public void onProviderDisabled(String s) {
                }
            };
        } else {
            lm.removeUpdates(sLocationListener);
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            try {
                if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, sLocationListener);
                }
//                    if (sLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                        sLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10, sLocationListener);
//                    }
            } catch (Exception e) {
//                LogUtils.e(e);
            }
        });

    }

    /**
     * 获取sdk版本
     * @return
     */
    public static String getSdkVersion(){

        return "1.0.2";
    }


    /**
     * 获取app版本名称
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.0.0";

    }

    /**
     * 获取android版本
     *
     * @param context
     * @return
     */
    public static String getOsVersion(Context context) {
        return "android " + android.os.Build.VERSION.RELEASE;
    }


    /**
     * 获取手机品牌+型号
     *
     * @param context
     * @return
     */
    public static String getPhoneBrand(Context context) {
        return android.os.Build.BRAND + " " + Build.MODEL;
    }

    /**
     * 手机cpu型号+android版本号
     * @return
     */
    public static String getCpuAbi(){

        return Build.CPU_ABI + "-android" + android.os.Build.VERSION.RELEASE;

    }

    /**
     * 获取app启动时间
     * @return
     */
    public static long getAppLaunchTime(){

        return GeexDataApi.appLaunchTime;

    }



    /**
     * 获取手机imei码
     *
     * @param mContext
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context mContext) {

        if (!TextUtils.isEmpty(imei)) {
            return imei;
        }

        try {
            if (!checkHasPermission(mContext, Manifest.permission.READ_PHONE_STATE)) {
                return imei;
            }

            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    imei = tm.getImei();
                } else {
                    imei = tm.getDeviceId();
                }
            }
        } catch (Exception e) {

        }
        return imei;
    }

    /**
     * 屏幕宽度
     */
    public static String getScreenWidth(Context context) {
        try {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            return String.valueOf(wm.getDefaultDisplay().getWidth());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    /**
     * 屏幕高度
     */
    public static String getScreenHeight(Context context) {
        try {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            return String.valueOf(wm.getDefaultDisplay().getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    /**
     * uuid 设备唯一码
     * @param context
     * @return
     */
    private static UUID uuid;
    public static String getDeviceUuid(Context context) {
        if (!TextUtils.isEmpty(deviceUuid)) {
            return deviceUuid;
        }

        if (uuid == null) {
            synchronized (CommonData.class) {
                if (uuid == null) {
                    final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    try {
                        if (!"9774d56d682e549c".equals(androidId)) {
                            uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                        } else {
                            final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                            uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            deviceUuid = uuid.toString();
        }

        return deviceUuid;

    }


    /**
     * 平台android
     *
     * @return
     */
    public static String getPlatform() {
        return "android";
    }

    /**
     * 获取应用名称 platName
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取应用包名 bundle_id
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        if (context != null) {
            return context.getPackageName();
        } else {
            return "";
        }
    }

    /**
     * network  网络环境(4g/wifi)
     * @param context
     * @return
     */
    public static String getNetworkType(Context context) {
        try {
            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectMgr.getActiveNetworkInfo();
            if (info == null) {
                return "unknown";
            }
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return "wifi";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                // 联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EDGE，电信的2G为CDMA，电信的3G为EVDO
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_IDEN:   // 25 kbps
                    case TelephonyManager.NETWORK_TYPE_CDMA:   // 电信2G, 14-64 kbps
                    case TelephonyManager.NETWORK_TYPE_GPRS:   // 移动/联通2G, 100 kbps
                    case TelephonyManager.NETWORK_TYPE_EDGE:   // 移动/联通2.5G, 50-100 kbps
                    case TelephonyManager.NETWORK_TYPE_1xRTT:  // CDMA2000-1X, 50-100 kbps(号称3G,然而速度并没有达到3G标准)
                        return "2G";
                    case TelephonyManager.NETWORK_TYPE_EVDO_0: // 电信3G, 400-1000 kbps
                    case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3G, 600-1400 kbps
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // 电信3G, 5 Mbps
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  // 电信3G,EVDO的升级, 1-2 Mbps
                    case TelephonyManager.NETWORK_TYPE_UMTS:   // 联通3G, 400-7000 kbps
                    case TelephonyManager.NETWORK_TYPE_HSPA:   // 联通3G,700-1700 kbps
                    case TelephonyManager.NETWORK_TYPE_HSUPA:  // 联通3G, 1-23 Mbps
                    case TelephonyManager.NETWORK_TYPE_HSDPA:  // 联通3.5G, 2-14 Mbps
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  // 联通3.5G, 10-20 Mbps
                        return "3G";
                    case TelephonyManager.NETWORK_TYPE_LTE:    // 4G, 10+ Mbps
                        return "4G";
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    default:
                        return "unknown";
                }
            } else {
                return info.getTypeName();
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取手机号
     * @return
     */
    public static void setMobile(String mobile){


        if(!TextUtils.isEmpty(mobile)){
            mMobile = mobile;
        }


    }


    /**
     * 获取uid
     * @return
     */
    public static void setUid(String uid){


        if(!TextUtils.isEmpty(uid)){
            mUid = uid;
        }


    }

    /**
     * 获取ip地址
     */
    public static void getIp(){


        new Thread(new Runnable() {
            @Override
            public void run() {

                URL infoUrl = null;
                InputStream inStream = null;
                String ipLine = "";
                HttpURLConnection httpConnection = null;
                try {
                    infoUrl = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=myip");
                    URLConnection connection = infoUrl.openConnection();
                    httpConnection = (HttpURLConnection) connection;
                    connection.setUseCaches(false);
                    ((HttpURLConnection) connection).setRequestMethod("GET");
                    connection.setRequestProperty("user-agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.7 Safari/537.36");
//                    connection.setRequestProperty("user-agent",
//                            "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.7 Safari/537.36");
                    int responseCode = httpConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inStream = httpConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(inStream, "utf-8"));
                        StringBuilder strber = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null){
                            strber.append(line + "\n");
                        }
                        Pattern pattern = Pattern
                                .compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
                        Matcher matcher = pattern.matcher(strber.toString());
                        if (matcher.find()) {
                            ipLine = matcher.group();
                            if(!TextUtils.isEmpty(ipLine)){
                                ip = ipLine;
                            }
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(inStream != null){
                            inStream.close();
                        }
                        if(httpConnection != null){
                            httpConnection.disconnect();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }).start();


    }

    /**
     * 获取纬度
     * @return
     */
    public static String getLongitude(){

        return sLongitude;

    }

    /**
     * 获取经度
     * @return
     */
    public static String getLatitude(){

        return sLatitude;

    }

    @SuppressLint("MissingPermission")
    public static void getAndroidSdkLocation(Context context){

        Location location = null;

        try {
            LocationManager locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation("network");
            if (location == null) {
                location = locationManager.getLastKnownLocation("gps");
                if (location == null) {
                    location = locationManager.getLastKnownLocation("passive");
                }
            }
            if(location != null){
                showLocation(location);
            }


        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }



    private static void showLocation(Location location){

        sLongitude = String.valueOf(location.getLongitude());
        sLatitude = String.valueOf(location.getLatitude());



    }




}
