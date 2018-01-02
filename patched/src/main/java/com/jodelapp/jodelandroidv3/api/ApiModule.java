package com.jodelapp.jodelandroidv3.api;

import android.util.Log;

import com.jodelapp.jodelandroidv3.jp.StethoInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * ? For debugging
 */
@DexEdit
public class ApiModule {

    @DexWrap
    private void setupCertificatePinning(OkHttpClient.Builder builder) {
        Method[] methods = builder.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0) {
                Log.i("JodelPatched", "Found method with 0 params");
                Type returnType = m.getGenericReturnType();
                if (returnType instanceof ParameterizedType) {
                    Log.i("JodelPatched", "Found method which returns an object with param types");
                    Type elementType = ((ParameterizedType) returnType).getActualTypeArguments()[0];
                    if (elementType.equals(Interceptor.class)) {
                        Log.i("JodelPatched", "Found correct type from method:" + m.getName());
                        try {
                            List<Interceptor> interceptors = (List<Interceptor>) m.invoke(builder);
                            interceptors.add(0, new StethoInterceptor());
                            Log.i("JodelPatched", "Added interceptor");
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        setupCertificatePinning(builder);
    }

//    @DexReplace
//    private OkHttpClient createOkHttpClient(RequestHeaderInterceptor requestHeaderInterceptor, IHmacInterceptor iHmacInterceptor, ResponseHeadersInterceptor responseHeadersInterceptor, AnalyticsInterceptor analyticsInterceptor) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        setupCertificatePinning(builder);
//        builder.f(60, TimeUnit.SECONDS);
//        builder.e(60, TimeUnit.SECONDS);
//        builder.Kw().add(0, requestHeaderInterceptor);
//        builder.Kw().add(1, iHmacInterceptor);
//        builder.Kw().add(2, responseHeadersInterceptor);
//        builder.Kw().add(3, analyticsInterceptor);
//        builder.Kw().add(4, new StethoInterceptor());
//
//        return builder.Kz();
//    }
}
