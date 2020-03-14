package com.finance.geex.statisticslibrary.http;

public interface DefinitionHttpRequest {
    String GET =  "GET";
    String POST =  "POST";

    //请求类型
    String getRequestType();
    //请求url
    String getRequestUrl();
    //请求开始时间
    long getStartTime();
    //服务器返回
    String getResponseBody();
    //响应时间
    String getResponseTime();
    //post请求时候的字段
    String getRequestParams();
}