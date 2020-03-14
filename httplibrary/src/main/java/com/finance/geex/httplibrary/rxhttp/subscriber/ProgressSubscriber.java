package com.finance.geex.httplibrary.rxhttp.subscriber;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;


import com.finance.geex.httplibrary.rxhttp.exception.ApiException;

import io.reactivex.annotations.NonNull;

/**
 * Created by GEEX302 on 2017/12/15.
 * 进度条 订阅
 */

public class ProgressSubscriber<T> extends BaseSubscriber<T> implements ProgressCancelListener {
    private IProgressDialog progressDialog;
    private Dialog mDialog;
    private boolean isShowProgress = true;

    /**
     * 默认不显示弹出框，不可以取消
     *
     * @param context  上下文
     */
    public ProgressSubscriber(Context context) {
        super(context);
        init(false);
    }

    /**
     * 自定义加载进度框
     *
     * @param context 上下文
     * @param progressDialog 自定义对话框
     */
    public ProgressSubscriber(Context context, IProgressDialog progressDialog) {
        super(context);
        this.progressDialog = progressDialog;
        init(false);
    }

    /**
     *
     * @param context 上下文
     * @param progressDialog 自定义对话框
     * @param isShowProgress 是否显示
     */
    public ProgressSubscriber(Context context, IProgressDialog progressDialog,boolean isShowProgress) {
        super(context);
        if(isShowProgress){
            this.progressDialog = progressDialog;
        }
        init(false);
    }

    /**
     * 初始化
     *
     * @param isCancel 对话框是否可以取消
     */
    private void init(boolean isCancel) {
        if (progressDialog == null) return;
        if(!isShowProgress)return;
        mDialog = progressDialog.getDialog();
        if (mDialog == null) return;
        mDialog.setCancelable(isCancel);
        if (isCancel) {
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    ProgressSubscriber.this.onCancelProgress();
                }
            });
        }
    }

    /**
     * 展示进度框
     */
    @Override
    final protected void showProgress() {
        if (!isShowProgress) {
            return;
        }
        if (mDialog != null) {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

    /**
     * 取消进度框
     */
    @Override
    final protected void dismissProgress() {
        if (!isShowProgress) {
            return;
        }
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void onError(ApiException e) {
//        dismissProgress();
    }

    @Override
    public void onCancelProgress() {
        if (!isDisposed()) {
            dispose();
        }
    }

    public void next(@NonNull T t){

    };
}
