package com.finance.geex.statistics.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created on 2019/9/6 10:42.
 *
 * @author Geex302
 */
public class TokenInterceptor implements Interceptor{


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        return chain.proceed(request);
    }
}
