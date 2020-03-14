package com.finance.geex.statisticslibrary.upload.http;

/**
 * Created on 2019/8/21 17:58.
 *
 * @author Geex302
 */
public class HttpRunnable implements Runnable {
    @Override
    public void run() {

        HttpUtil.upload(1);
        ThreadPoolUtil.threadPoolIsWork = false;

    }
}
