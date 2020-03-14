package com.finance.geex.httplibrary.rxhttp.interceptor;


import com.finance.geex.httplibrary.rxhttp.util.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 打印请求日志 开发期间 使用
 * Created by GEEX88 on 2018/1/19.
 */
public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();//请求发起的时间
        LogUtil.i(LogUtil.LOG_TAG_REQUEST, String.format("send request info %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间
        ResponseBody responseBody = response.peekBody(1024 * 1024);

        LogUtil.i(LogUtil.LOG_TAG_REQUEST, String.format("response info: [%s] %n response body json:【%s】 %.1fms%n%s",
                response.request().url(),
                responseBody.string(),
                (t2 - t1) / 1e6d,
                response.headers()));
        return response;
    }
}
