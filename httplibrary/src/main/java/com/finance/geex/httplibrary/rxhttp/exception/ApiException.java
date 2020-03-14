package com.finance.geex.httplibrary.rxhttp.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Created by GEEX302 on 2017/12/15.
 * Api 异常错误类
 */

public class ApiException extends Exception {

    //对应HTTP的状态码
    private static final int BADREQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int METHOD_NOT_ALLOWED = 405;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


    private String displayMessage;

    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = 1001;
    private String message;
    private int code;

    public ApiException(String msg, int code){
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            if(httpException.code() == 401){
                ex = new ApiException(httpException, ERROR.TOKEN_ERROR);
                ex.message = "请重新登录";

            }else {
                ex = new ApiException(httpException, httpException.code());
                ex.message = httpException.getMessage();
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getErrCode());
            ex.message = resultException.getMessage();
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof JsonSyntaxException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException) {
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ClassCastException) {
            ex = new ApiException(e, ERROR.CAST_ERROR);
            ex.message = "类型转换错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ApiException(e, ERROR.UNKNOWNHOST_ERROR);
            ex.message = "无法解析该域名";
            return ex;
        } else if (e instanceof NullPointerException) {
            ex = new ApiException(e, ERROR.NULLPOINTER_EXCEPTION);
            ex.message = "NullPointerException";
            return ex;
        } else {
            ex = new ApiException(e, ERROR.UNKNOWN);
//            ex.message = Config.DEBUG?e.getMessage():"未知错误";
            ex.message = e.getMessage();
            return ex;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    /*public String getErrMessage() {
        return message;
    }*/

    /**
     * 约定异常
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
//                UNKNOWN + 1;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
//                PARSE_ERROR + 1;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;
//                NETWORD_ERROR + 1;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1004;
//                HTTP_ERROR + 1;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1005;
//                SSL_ERROR + 1;

        /**
         * 调用错误
         */
        public static final int INVOKE_ERROR = 1006;
//                TIMEOUT_ERROR + 1;
        /**
         * 类转换错误
         */
        public static final int CAST_ERROR =1007;
//                INVOKE_ERROR + 1;
        /**
         * 请求取消
         */
        public static final int REQUEST_CANCEL = 1008;
//                CAST_ERROR + 1;
        /**
         * 未知主机错误
         */
        public static final int UNKNOWNHOST_ERROR = 1009;
//                REQUEST_CANCEL + 1;

        /**
         * 空指针错误
         */
        public static final int NULLPOINTER_EXCEPTION = 1010;
//                UNKNOWNHOST_ERROR + 1;
        /**
         * token失效(http400)
         */
        public static final int TOKEN_ERROR = 1011;



    }
}
