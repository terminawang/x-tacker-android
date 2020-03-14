package com.finance.geex.statisticslibrary.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2019/8/15 13:47.
 * 权限判断类
 * @author Geex302
 */
public class PermissionUtils {

    private PermissionUtils() {
    }

    /**
     * 判断权限
     * @param context 上下文
     * @param permissions 权限组
     * @return true:已允许该权限; false:没有允许该权限
     */
    public static boolean checkHasPermission(@NonNull Context context, @NonNull String... permissions) {
        boolean isPermission = true;
        try {
            List<String> permissionList = Arrays.asList(permissions);
            Class<?> contextCompat = null;
            try {
                contextCompat = Class.forName("android.support.v4.content.ContextCompat");
            } catch (Exception e) {
                //ignored
            }

            if (contextCompat == null) {
                try {
                    contextCompat = Class.forName("androidx.core.content.ContextCompat");
                } catch (Exception e) {
                    //ignored
                }
            }

            if (contextCompat == null) {
                return true;
            }

            for (int i = 0; i < permissionList.size(); i++) {
                String permission = permissionList.get(i);
                Method checkSelfPermissionMethod = contextCompat.getMethod("checkSelfPermission", new Class[]{Context.class, String.class});
                int result = (int) checkSelfPermissionMethod.invoke(null, new Object[]{context, permission});
                if (result != PackageManager.PERMISSION_GRANTED) {
                    isPermission = false;
                    break;
                }
            }


            return isPermission;
        } catch (Exception e) {

            return true;
        }
    }


}
