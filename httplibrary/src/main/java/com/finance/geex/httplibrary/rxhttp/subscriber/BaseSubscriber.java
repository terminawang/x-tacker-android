package com.finance.geex.httplibrary.rxhttp.subscriber;

import android.content.Context;

import com.finance.geex.httplibrary.rxhttp.exception.ApiException;

import java.lang.ref.WeakReference;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

import static com.finance.geex.httplibrary.rxhttp.util.NetworkUtil.isNetworkAvailable;


/**
 * Created by GEEX302 on 2017/12/15.
 * 订阅者基类(防止内存泄漏)
 */

public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    public WeakReference<Context> contextWeakReference;


    public BaseSubscriber() {
    }

    @Override
    final protected void onStart() {
//        HttpLog.e("-->http is onStart");
        showProgress();
        start();
        if (contextWeakReference != null && contextWeakReference.get() != null && !isNetworkAvailable(contextWeakReference.get())) {
//            NoNetworkToast toast = new NoNetworkToast(contextWeakReference.get(), "网络竟然崩溃了");
//            toast.show();
            onComplete();
        }
    }


    public BaseSubscriber(Context context) {
        if (context != null) {
            contextWeakReference = new WeakReference<>(context);
        }
    }

    /**
     * 重写onnext 用于处理token失效后的结果
     * @param t
     */
    @Override
    final public void onNext(@NonNull T t) {
//        HttpLog.e("-->http is onNext");
        next(t);
    }


    abstract public void start();
    abstract public void next(@NonNull T t);
    abstract public void complete();
    abstract protected void showProgress();
    abstract protected void dismissProgress();

    @Override
    public final void onError(Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
//            if(contextWeakReference != null && contextWeakReference.get() != null && !isNetworkAvailable(contextWeakReference.get())){
//                //...
//            }else {
                onError(ApiException.handleException(e));
            }
//        }
        dismissProgress();
    }

    @Override
    final public void onComplete() {
//        HttpLog.e("-->http is onComplete");
        complete();
        dismissProgress();
    }


    public abstract void onError(ApiException e);

}
