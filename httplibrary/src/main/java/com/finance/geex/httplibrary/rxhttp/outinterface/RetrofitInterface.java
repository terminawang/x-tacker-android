package com.finance.geex.httplibrary.rxhttp.outinterface;

import com.finance.geex.httplibrary.rxhttp.exception.ApiException;

import org.json.JSONObject;

/**
 * Created on 2019/8/20 09:49.
 * http库对外接口
 * @author Geex302
 */
public interface RetrofitInterface {

    void onError(ApiException e);

    void next(JSONObject jsonObject);

    void onComplete();

}
