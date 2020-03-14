package com.finance.geex.statisticslibrary.db;

import java.io.Serializable;

/**
 * Created on 2019/9/10 10:47.
 * 错误日志实体类
 * @author Geex302
 */
public class GeexErrDataBean implements Serializable{

    private String platform; //平台 如ios  android
    private String platName; //app名字 如超即花  GeexPro
    private String sdk_version; //sdk版本
    private String app_version; //app版本
    private String mobile; //手机号
    private String phone_brand; //手机品牌
    private String cpu_abi; //cpu类型
    private String crash_time; //崩溃时间
    private String crash_reason; //崩溃原因
    private String app_launch_time; //app启动时间
    private String uuid; //设备唯一识别码

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone_brand() {
        return phone_brand;
    }

    public void setPhone_brand(String phone_brand) {
        this.phone_brand = phone_brand;
    }

    public String getCpu_abi() {
        return cpu_abi;
    }

    public void setCpu_abi(String cpu_abi) {
        this.cpu_abi = cpu_abi;
    }

    public String getCrash_time() {
        return crash_time;
    }

    public void setCrash_time(String crash_time) {
        this.crash_time = crash_time;
    }

    public String getCrash_reason() {
        return crash_reason;
    }

    public void setCrash_reason(String crash_reason) {
        this.crash_reason = crash_reason;
    }

    public String getApp_launch_time() {
        return app_launch_time;
    }

    public void setApp_launch_time(String app_launch_time) {
        this.app_launch_time = app_launch_time;
    }
}
