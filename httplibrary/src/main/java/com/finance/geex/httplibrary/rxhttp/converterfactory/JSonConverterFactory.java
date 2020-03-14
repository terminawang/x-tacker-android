package com.finance.geex.httplibrary.rxhttp.converterfactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by GEEX302 on 2017/12/21.
 * Retrofit转换为String字符串
 */

public class JSonConverterFactory extends BaseConverterFactory {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public Converter<ResponseBody, JSONObject> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return new Converter<ResponseBody, JSONObject>() {
                @Override
                public JSONObject convert(ResponseBody value) throws IOException {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(value.string());
                        return jsonObject;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }
            };
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return new Converter<String, RequestBody>() {
                @Override
                public RequestBody convert(String value) throws IOException {
                    return RequestBody.create(MEDIA_TYPE, value);
                }
            };
        }
        return null;
    }

//    public static StringConverterFactory create() {
//        return new StringConverterFactory();
//    }
//
//    @Nullable
//    @Override
//    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//        return super.stringConverter(type, annotations, retrofit);
//    }



//    final class ConfigurationServiceConverter implements Converter<ResponseBody, String> {
//
//        @Override
//        public String convert(ResponseBody value) throws IOException {
//            BufferedReader r = new BufferedReader(new InputStreamReader(value.byteStream()));
//            StringBuilder total = new StringBuilder();
//            String line;
//            while ((line = r.readLine()) != null) {
//                total.append(line);
//            }
//            return total.toString();
//        }
//    }

}
