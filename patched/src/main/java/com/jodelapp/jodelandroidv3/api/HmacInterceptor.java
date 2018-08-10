package com.jodelapp.jodelandroidv3.api;

import android.content.Context;
import android.text.TextUtils;

import com.tellm.android.app.mod.BuildConfig;

import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.fabric.sdk.android.services.network.HttpRequest;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexReplace;
import okhttp3.Request;


/**
 * ? For valid signing of requests
 */
@SuppressWarnings("JniMissingFunction")
@DexEdit(contentOnly = true)
public class HmacInterceptor {


    @DexIgnore
    private static final String SEPARATOR = "%";
    @DexIgnore
    private String saltHash;
    @DexIgnore
    private int port;

    @DexReplace
    private String calculateHMac(Request request, String str) {
        try {
            URI TH = request.url().uri();
            StringBuilder append2 = new StringBuilder(request.method()).append(SEPARATOR).append(TH.getHost()).append(SEPARATOR).append(String.valueOf(this.port)).append(SEPARATOR).append(TH.getPath());
            append2.append(SEPARATOR);
            String header = request.header(HttpRequest.HEADER_AUTHORIZATION);
            if (!TextUtils.isEmpty(header)) {
                append2.append(header.split(" ")[1]);
            }
            append2.append(SEPARATOR).append(str);
            appendQuery(append2, TH.getQuery());
            appendBody(append2, request);
            return toHexString(calculateRFC2104HMAC(append2.toString().getBytes("UTF-8"), BuildConfig.HMAC_KEY));
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    @DexReplace
    private static String getClientType() {
        return "android_" + BuildConfig.VERSION_NAME;
    }

    @DexAdd
    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    @DexAdd
    private static byte[] calculateRFC2104HMAC(byte[] data, String key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        return mac.doFinal(data);
    }


    @DexIgnore
    private static void appendQuery(StringBuilder stringBuilder, String str) {}

    @DexIgnore
    private static void appendBody(StringBuilder stringBuilder, Request request) {}

    @DexReplace
    private static String calculateSaltHash(Context context) {
        return "a4a8d4d7b09736a0f65596a868cc6fd620920fb0";
    }
}