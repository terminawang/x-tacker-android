package com.finance.geex.httplibrary.rxhttp.interceptor;




import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



/**
 * Created by GEEX302 on 2017/12/21.
 * token拦截器(处理token错误)
 */

public class TokenInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private String status;




    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();


        Response originalResponse = null;


        //需要重新构建request请

        /**
         * 登录获取token时，基础认证
         */
        Request.Builder requestBuild = request.newBuilder()
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/51.0.2704.7 Safari/537.36");


        request = requestBuild.build();//重新构建 请求
        originalResponse = chain.proceed(request);


        return originalResponse;
    }

}
