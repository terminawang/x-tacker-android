package com.finance.geex.httplibrary.rxhttp.exception;

/**
 * Created by GEEX302 on 2017/12/15.
 * 处理服务器异常
 */

public class ServerException extends RuntimeException {

    private int errCode;
    private String message;

    public ServerException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
