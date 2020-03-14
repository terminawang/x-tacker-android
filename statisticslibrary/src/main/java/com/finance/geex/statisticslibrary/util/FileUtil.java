package com.finance.geex.statisticslibrary.util;


import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created on 2019/8/28 10:27.
 * 文件处理工具类
 * @author Geex302
 */
public class FileUtil {

    public static String getStringByAssetsFile(Context context){

        String result = "";
        BufferedReader br = null;
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(context.getAssets().open("public_key.pem"));
            br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(br != null){
                    br.close();
                }
                if(isr != null){
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }


        return result;
    }


}
