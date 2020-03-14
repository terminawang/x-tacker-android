package com.finance.geex.httplibrary.rxhttp.service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by GEEX302 on 2017/12/14.
 * 数据接口
 */

public interface ServiceApi {

//    /**
//     * 同步请求
//     * @param account
//     * @param code
//     * @param deviceToken
//     * @return
//     */
    @FormUrlEncoded
    @GET("user/autoLogin")
    Call<String> autoLogin(@Field("account") String account, @Field("code") String code,
                           @Field("deviceToken") String deviceToken);
//
//    /**
//     * repayment/initPreRepay(post object)
//     */
//    @POST()
//    Observable<String> initPreRepay(@Url String url, @Body RequestBody body);
//
//    /**
//     * user/checkUpdate
//     */
//    @GET("user/checkUpdate/{token}/{version}/{android}")
//    Observable<String> checkUpdate(@Path("token") String token, @Path("version") String version,
//
//                             @Path("android") String android);

    //同步请求
//    @FormUrlEncoded
    @GET()
    Call<String> getCall(@Url String url);

    @GET()
    Observable<String> get(@Url String url, @QueryMap Map<String, String> maps);

    @GET()
    Observable<String> get(@Url String url);

    @FormUrlEncoded
    @POST()
    Observable<String> post(@Url String url, @FieldMap Map<String, String> maps);

    @Multipart
    @POST
    Observable<String> post(@Url String url, @Part MultipartBody.Part filed);


    /**
     * 图片+ 参数上传
     * @param url       请求链接
     * @param params    参数
     * @return
     */
    @Multipart
    @POST
    Observable<String> postFile(@Url String url, @PartMap Map<String, RequestBody> params);


    /**
     * 同步接口
     * @param url
     * @param maps
     * @return
     */
    @FormUrlEncoded
    @POST()
    Call<String> refreshToken(@Url String url, @FieldMap Map<String, String> maps);


    @POST
    Observable<String> postJson(@Url String url, @Body RequestBody body);

    /**
     * RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonString);
     */

}
