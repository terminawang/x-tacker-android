package com.finance.geex.statistics;

import android.app.Application;

import com.finance.geex.statisticslibrary.mananger.GeexDataApi;

/**
 * Created on 2019/8/14 10:18.
 *
 * @author Geex302
 */
public class CustomApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {

        GeexDataApi.init(this,true);

    }


}
