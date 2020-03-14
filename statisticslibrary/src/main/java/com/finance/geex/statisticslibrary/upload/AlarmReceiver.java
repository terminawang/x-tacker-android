package com.finance.geex.statisticslibrary.upload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created on 2019/8/16 14:55.
 * 通知DataUploadService启动
 * @author Geex302
 */
public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Intent i = new Intent(context, DataUploadService.class);
            context.startService(i);
        }catch (Exception e){

        }



    }
}
