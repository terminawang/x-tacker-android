package com.finance.geex.httplibrary.rxhttp.outinterface;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created on 2019/8/20 10:28.
 *
 * @author Geex302
 */
public interface Interceptor{

    Response intercept(Request request);

}
