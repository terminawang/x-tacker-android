package com.finance.geex.httplibrary.rxhttp.rfhelp;


import android.app.Application;
import android.content.Context;

import com.finance.geex.httplibrary.rxhttp.Config;
import com.finance.geex.httplibrary.rxhttp.converterfactory.StringConverterFactory;
import com.finance.geex.httplibrary.rxhttp.interceptor.LogInterceptor;
import com.finance.geex.httplibrary.rxhttp.interceptor.TokenInterceptor;
import com.finance.geex.httplibrary.rxhttp.service.ServiceApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by GEEX302 on 2017/12/14.
 * 封装Retrofit
 */
public class RetrofitHelper {
    private volatile static RetrofitHelper retrofitHelper = null;

    private static final int TIME_OUT_DETAULT = 30; //okhttp默认超时 时间设置



    public static Context sContext;
    private static RetrofitHelper singleton;

    private String mBaseUrl;//全局BaseUrl
    private boolean mDebug;//是否为debug环境

    private OkHttpClient.Builder okHttpBuild;//okhttp请求的客户端
    private Retrofit.Builder retrofitBuilder = null;//Retrofit请求Builder
    private Retrofit retrofit = null;//Retrofit对象
    private OkHttpClient okHttpClient;



    public static void init(Context application){
        sContext = application;
    }

    public static RetrofitHelper getInstance() {
        if(sContext == null){
            throw new ExceptionInInitializerError("请先在全局Application中调用 RetrofitHelper.init() 初始化！");
        }
        if (singleton == null) {
            synchronized (RetrofitHelper.class) {
                if (singleton == null) {
                    singleton = new RetrofitHelper();
                }
            }
        }
        return singleton;
    }


    private RetrofitHelper(){

        //okhttp请求的客户端
        okHttpBuild = new OkHttpClient.Builder();
        //设置默认连接超时时间
        okHttpBuild.connectTimeout(TIME_OUT_DETAULT, TimeUnit.SECONDS);
        okHttpBuild.readTimeout(TIME_OUT_DETAULT, TimeUnit.SECONDS);
        okHttpBuild.writeTimeout(TIME_OUT_DETAULT, TimeUnit.SECONDS);
        okHttpBuild.addInterceptor(new TokenInterceptor());
        //Retrofit请求Builder
        retrofitBuilder = new Retrofit.Builder();
        //okHttpClient
        okHttpClient = okHttpBuild.build();

    }





    /**
     * 单例
     * @return
     */
    public static RetrofitHelper getInstance(String baseUrl,boolean config,int timeout){
        synchronized (RetrofitHelper.class) {
            if (retrofitHelper == null) {
                retrofitHelper = new RetrofitHelper();
            }
        }
        return retrofitHelper;
    }



    /**
     * 获取Retrofit实例
     * @return
     */
//    private static Retrofit getRetrofit(){
//
//    }

    private static Retrofit build(){
        synchronized (Retrofit.class){
            if(getInstance().retrofit == null){
                getInstance().retrofit = new Retrofit.Builder()
                            .baseUrl(getInstance().mBaseUrl) //BASE_URL要以\结尾
                            .client(getInstance().okHttpClient) //OkHttpClient
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //RxJava处理数据
    //                            .addConverterFactory(GsonConverterFactory.create()) //Gson解析数据
//                            .addConverterFactory(new JSonConverterFactory())//添加 json 转换功能
                            .addConverterFactory(new StringConverterFactory()) //添加 转成字符串进行处理，不使用Gson
                            .build();

                }
        }
        return getInstance().retrofit;
    }

    public static Retrofit reBuild(){
        return null;
    }

    /**
     * 切换线程操作
     * @return Observable转换器
     */
    public static <T> ObservableTransformer<T, T> observeOnMainThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())                       // 在io线程中请求
                        .observeOn(AndroidSchedulers.mainThread());         // 请求完成后返回主线程处理
            }
        };
    }

    public static ObservableTransformer<String,JSONObject> jsonObjectObservableTransformer(){
        return new ObservableTransformer<String, JSONObject>() {
            @Override
            public ObservableSource<JSONObject> apply(@NonNull Observable<String> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Function<String, JSONObject>() {
                    @Override
                    public JSONObject apply(@NonNull String s) throws Exception,JSONException {
                        return new JSONObject(s);
                    }
                });
            }
        };
    }

    /**
     * 全局设置baseUrl
     */
    public RetrofitHelper setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
        return this;
    }

    /**
     * 是否为debug环境
     * @param debug true:debug环境  false:生产环境
     * @return
     */
    public RetrofitHelper setDebug(boolean debug) {
        mDebug = debug;
        //debug环境下默认加入日志
        if(mDebug){
            okHttpBuild.addInterceptor(new LogInterceptor());
        }

        return this;
    }


    /**
     * 连接时间设置
     * @param timeout
     * @return
     */
    public RetrofitHelper setConnectTimeout(int timeout){
        okHttpBuild.connectTimeout(timeout, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 读取时间设置
     * @param timeout
     * @return
     */
    public RetrofitHelper setReadTimeout(int timeout){
        okHttpBuild.readTimeout(timeout, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 写入时间
     * @param timeout
     * @return
     */
    public RetrofitHelper setWriteTimeout(int timeout){
        okHttpBuild.writeTimeout(timeout, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 是否加入token拦截器
     * @return
     */
    public RetrofitHelper addTokenInterceptor(Interceptor interceptor){
        okHttpBuild.addInterceptor(interceptor);
        return this;
    }


    public static <T> T createApi(Class<T> clazz){
        return getInstance().build().create(clazz);
    }




}
