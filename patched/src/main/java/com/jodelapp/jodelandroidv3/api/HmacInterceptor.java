package com.jodelapp.jodelandroidv3.api;

import android.content.Context;

import java.io.IOException;

import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexReplace;
import okhttp3.Interceptor;
import okhttp3.Response;


@DexEdit
public class HmacInterceptor implements IHmacInterceptor {

    @DexReplace
    private static String calculateSaltHash(Context context) {
        return "a4a8d4d7b09736a0f65596a868cc6fd620920fb0";
    }

    @DexIgnore
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}

@DexIgnore
interface IHmacInterceptor extends Interceptor {
}