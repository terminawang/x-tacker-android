package com.finance.geex.statisticslibrary.data;

/**
 * Created on 2019/8/30 09:56.
 *
 * @author Geex302
 */
public class Constant {

    /**
     * true:测试环境 false:正式环境
     */
    public static boolean isDebug = false;

    public static class DataUrl{


        private static String BASE_URL = isDebug ? "https://beta.geexfinance.com/"
                : "https://www.geexfinance.com/";



        /**
         * 埋点信息上传
         */
        public static String receiveData = BASE_URL + "api-gateway/api-tracker/receive/data";

        /**
         * https://beta.geexfinance.com/api-gateway/api-tracker/collector/crash
         * 崩溃日志上传
         */
        public static String collectorCrash = BASE_URL + "api-gateway/api-tracker/collect/crash";

        /**
         * https://beta.geexfinance.com/api-gateway/api-tracker/collector/request
         * 网络请求日志上传
         */
        public static String collectorRequest = BASE_URL + "api-gateway/api-tracker/collect/request";



    }



}
