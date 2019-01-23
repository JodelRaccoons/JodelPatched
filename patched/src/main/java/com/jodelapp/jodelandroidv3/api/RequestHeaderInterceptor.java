package com.jodelapp.jodelandroidv3.api;

import com.tellm.android.app.mod.BuildConfig;

import java.io.IOException;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Admin on 06.04.2018.
 */
@DexEdit(contentOnly = true)
public class RequestHeaderInterceptor implements Interceptor {

    @DexEdit
    private String userAgentValue;

    @DexWrap
    @Override
    public Response intercept(Chain interceptor$Chain) throws IOException {
        if (userAgentValue != null && !userAgentValue.contains("JodelPatched")) {
            if (BuildConfig.DEBUG) {
                userAgentValue = userAgentValue.replaceFirst("Jodel", "JodelPatched/just ignore this one (debug)");
            } else {
                userAgentValue = userAgentValue.replaceFirst("Jodel", "JodelPatched");
            }
        }

        return intercept(interceptor$Chain);
    }

    @DexAdd
    public static class JPInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder newBuilder = chain.request().newBuilder();
            newBuilder.addHeader("X-JodelPatched-Version", BuildConfig.VERSION_NAME + "/" + BuildConfig.VERSION_CODE);
            newBuilder.addHeader("X-JodelPatched-BuildType", BuildConfig.BUILD_TYPE);
            newBuilder.addHeader("X-JodelPatched-GlutenFree", "definitely");
            newBuilder.addHeader("X-Pect", "The spanish inquisition");
            return chain.proceed(newBuilder.build());
        }
    }
}
