package com.finance.geex.httplibrary.rxhttp.interceptor;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created on 2019/4/28 16:03.
 *
 * @author Geex302
 */
public class CustomAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        return null;
    }
}
