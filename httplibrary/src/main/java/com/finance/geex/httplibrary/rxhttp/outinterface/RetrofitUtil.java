package com.finance.geex.httplibrary.rxhttp.outinterface;

import android.content.Context;

import com.finance.geex.httplibrary.rxhttp.exception.ApiException;
import com.finance.geex.httplibrary.rxhttp.outinterface.RetrofitInterface;
import com.finance.geex.httplibrary.rxhttp.rfhelp.RetrofitHelper;
import com.finance.geex.httplibrary.rxhttp.service.ServiceApi;
import com.finance.geex.httplibrary.rxhttp.subscriber.ProgressSubscriber;

import org.json.JSONObject;

import io.reactivex.annotations.NonNull;

/**
 * Created on 2019/8/20 09:55.
 * 封装的Retrofit对外接口
 * @author Geex302
 */
public class RetrofitUtil {

    public static void get(Context context, String url, final RetrofitInterface retrofitInterface){

        RetrofitHelper.createApi(ServiceApi.class)
                .get(url)
                .compose(RetrofitHelper.jsonObjectObservableTransformer())
                .subscribe(new ProgressSubscriber<JSONObject>(context, null) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);

                        retrofitInterface.onError(e);

                    }

                    @Override
                    public void next(@NonNull JSONObject jsonObject) {

                        retrofitInterface.next(jsonObject);

                    }


                    @Override
                    public void complete() {
                        super.complete();

                        retrofitInterface.onComplete();

                    }
                });


    }


}
