package com.finance.geex.statisticslibrary.db;

/**
 * Created on 2019/8/14 14:26.
 * 数据库的一些常量
 * @author Geex302
 */
public class GeexDbParams {

    /* 数据库名称 */
    static final String DATABASE_NAME = "geexstatistics";
    /* 数据库版本号 */
    static final int DATABASE_VERSION = 2; //数据库若升级，则版本号+1
    /* 数据库中的表名 埋点 */
    public static final String TABLE_EVENTS= "geex_events";
    /* 数据库中的表名 网络请求 */
    public static final String TABLE_NETWORK_REQUEST= "geex_network_request"; //network request
    /* 数据库中的表名 app崩溃日志 */
    public static final String TABLE_APP_CRASH= "geex_app_crash";

    /* Event 表字段 */
    static final String KEY_DATA = "data";
    static final String KEY_CREATED_AT = "created_at";

}
