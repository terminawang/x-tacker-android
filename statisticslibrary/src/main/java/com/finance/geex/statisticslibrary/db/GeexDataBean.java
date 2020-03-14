package com.finance.geex.statisticslibrary.db;

import java.io.Serializable;

/**
 * Created on 2019/8/14 15:49.
 * 埋点model类
 * @author Geex302
 */
public class GeexDataBean implements Serializable{

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

    private String sdk_version; //sdk版本
    private String app_version; //app版本
    private String osVersion; //手机系统版本
    private String phone_brand; //手机品牌
    private String deviceFingerprint; //设备指纹 imei
    private String screen_width; //屏幕宽
    private String screen_hight; //屏幕高
    private String uuid; //设备唯一识别码
    private String platform; //平台 ios/android
    private String platName; //应用名称
    private String network; //网络情况 wifi/4g/unknown
    private String bundle_id; //包名
    private String eventName; //事件名称
    private String eventType; //事件类型 点击、页面进入、页面消失
    private String startTime; //开始时间 yyyy-MM-dd HH:mm:ss
    private String endTime; //结束时间 yyyy-MM-dd HH:mm:ss
    private String mobile; //手机号
    private String uid; //uid
    private String longitude; //经度
    private String latitude; //纬度


    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getPhone_brand() {
        return phone_brand;
    }

    public void setPhone_brand(String phone_brand) {
        this.phone_brand = phone_brand;
    }

    public String getDeviceFingerprint() {
        return deviceFingerprint;
    }

    public void setDeviceFingerprint(String deviceFingerprint) {
        this.deviceFingerprint = deviceFingerprint;
    }

    public String getScreen_width() {
        return screen_width;
    }

    public void setScreen_width(String screen_width) {
        this.screen_width = screen_width;
    }

    public String getScreen_hight() {
        return screen_hight;
    }

    public void setScreen_hight(String screen_hight) {
        this.screen_hight = screen_hight;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getBundle_id() {
        return bundle_id;
    }

    public void setBundle_id(String bundle_id) {
        this.bundle_id = bundle_id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
