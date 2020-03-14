package com.finance.geex.statisticslibrary.util;

/**
 * Created on 2019/8/22 15:15.
 * 防止连点
 * @author Geex302
 */
public class NoDoubleClickUtils {

    private final static int SPACE_TIME = 500;//2次点击的间隔时间，单位ms
    private static long lastClickTime;

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick;
        if (currentTime - lastClickTime > SPACE_TIME) {
            isClick = false;
        } else {
            isClick = true;
        }
        lastClickTime = currentTime;
        return isClick;
    }


}
