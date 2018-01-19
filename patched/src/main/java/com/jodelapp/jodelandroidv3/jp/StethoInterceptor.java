/*
 * Copyright (c) 2014-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant 
 * of patent rights can be found in the PATENTS file in the same directory.
*/

package com.jodelapp.jodelandroidv3.jp;

import com.facebook.stetho.inspector.network.DefaultResponseHandler;
import com.facebook.stetho.inspector.network.NetworkEventReporter;
import com.facebook.stetho.inspector.network.NetworkEventReporterImpl;
import com.facebook.stetho.inspector.network.RequestBodyHelper;

import lanchon.dexpatcher.annotation.DexAdd;
import okhttp3.*;
import okhttp3.internal.http.RealInterceptorChain;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@DexAdd
class Get {
    static Request getRequest(Interceptor.Chain chain) {
        Method[] methods = chain.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 && m.getGenericReturnType().equals(Request.class)) {
                try {
                    return (Request) m.invoke(chain);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static Response getResponse(Interceptor.Chain chain, Request request) throws IOException {
        Method[] methods = chain.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 1 && m.getGenericReturnType().equals(Response.class)) {
                try {
                    return (Response) m.invoke(chain, request);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static Response getResponse(Response response) {
        Method[] methods = response.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 && m.getGenericReturnType().equals(Response.class)) {
                try {
                    return (Response) m.invoke(response);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static ResponseBody getResponseBody(Response response) {
        Method[] methods = response.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 && m.getGenericReturnType().equals(ResponseBody.class)) {
                try {
                    return (ResponseBody) m.invoke(response);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static MediaType getMediaType(ResponseBody body) {
        Method[] methods = body.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 && m.getGenericReturnType().equals(MediaType.class)) {
                try {
                    return (MediaType) m.invoke(body);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static BufferedSource getBufferedSource(ResponseBody body) {
        Method[] methods = body.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 && m.getGenericReturnType().equals(BufferedSource.class)) {
                try {
                    return (BufferedSource) m.invoke(body);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static InputStream getInputStream(BufferedSource source) {
        Method[] methods = source.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 && m.getGenericReturnType().equals(InputStream.class)) {
                try {
                    return (InputStream) m.invoke(source);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    static String getHeader(Response response, String header) {
        Method[] methods = response.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 1 && m.getGenericReturnType().equals(String.class)) {
                try {
                    return (String) m.invoke(response, header);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static String getHeader(Request request, String header) {
        Method[] methods = request.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (!m.getName().equals("toString") && m.getParameterTypes().length == 1 && m.getGenericReturnType().equals(String.class)) {
                try {
                    return (String) m.invoke(request, header);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static Response.Builder getBuilder(Response response) {
        Method[] methods = response.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 && m.getGenericReturnType().equals(Response.Builder.class)) {
                try {
                    return (Response.Builder) m.invoke(response);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static Response.Builder setForwardingResponseBody(Response.Builder builder, ResponseBody body) {
        Method[] methods = builder.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getGenericParameterTypes().length == 1 &&
                    m.getGenericReturnType().equals(Response.Builder.class) &&
                    m.getGenericParameterTypes()[0].equals(ResponseBody.class)) {
                try {
                    return (Response.Builder) m.invoke(builder, body);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static Response buildResponse(Response.Builder builder) {
        Method[] methods = builder.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 && m.getGenericReturnType().equals(Response.class)) {
                try {
                    return (Response) m.invoke(builder);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static HttpUrl getHttpUrl(Request request) {
        Method[] methods = request.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 && m.getGenericReturnType().equals(HttpUrl.class)) {
                try {
                    return (HttpUrl) m.invoke(request);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static String getMethod(Request request) {
        Method[] methods = request.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (!m.getName().equals("toString") && m.getParameterTypes().length == 0 && m.getGenericReturnType().equals(String.class)) {
                try {
                    return (String) m.invoke(request);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static BufferedSink getBuffer(Sink sink) {
        Method[] methods = Okio.class.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 1 &&
                    m.getGenericReturnType().equals(BufferedSink.class)
                    && m.getGenericParameterTypes()[0].equals(Sink.class)) {
                try {
                    return (BufferedSink) m.invoke(null, sink);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static RequestBody getRequestBody(Request request) {
        Method[] methods = request.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 && m.getGenericReturnType().equals(RequestBody.class)) {
                try {
                    return (RequestBody) m.invoke(request);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static void setBufferedSink(RequestBody body, BufferedSink sink) {
        Method[] methods = body.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 1 && m.getGenericParameterTypes()[0].equals(BufferedSink.class)) {
                try {
                    m.invoke(body, sink);
                    return;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static int getCode(Response response) {
        Method[] methods = response.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 && m.getGenericReturnType().equals(int.class)) {
                try {
                    return (int) m.invoke(response);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    static BufferedSource getBuffer(Source source) {
        Method[] methods = Okio.class.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 1 &&
                    m.getGenericReturnType().equals(BufferedSource.class)
                    && m.getGenericParameterTypes()[0].equals(Source.class)) {
                try {
                    return (BufferedSource) m.invoke(null, source);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static Headers getHeaders(Request request) {
        Method[] methods = request.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 &&
                    m.getGenericReturnType().equals(Headers.class)) {
                try {
                    return (Headers) m.invoke(request);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static Headers getHeaders(Response response) {
        Method[] methods = response.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 &&
                    m.getGenericReturnType().equals(Headers.class)) {
                try {
                    return (Headers) m.invoke(response);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static Connection getConnection(RealInterceptorChain chain) {
        Method[] methods = chain.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0 &&
                    m.getGenericReturnType().equals(Connection.class)) {
                try {
                    return (Connection) m.invoke(chain);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

/**
 * Provides easy integration with <a href="http://square.github.io/okhttp/">OkHttp</a> 3.x by way of
 * the new <a href="https://github.com/square/okhttp/wiki/Interceptors">Interceptor</a> system. To
 * use:
 * <pre>
 *   OkHttpClient client = new OkHttpClient.Builder()
 *       .addNetworkInterceptor(new StethoInterceptor())
 *       .build();
 * </pre>
 */
@DexAdd
public class StethoInterceptor implements Interceptor {
    private final NetworkEventReporter mEventReporter = NetworkEventReporterImpl.get();


    @Override
    public Response intercept(Chain chain) throws IOException {
        String requestId = mEventReporter.nextRequestId();

//        Request request = chain.Ki();
        Request request = Get.getRequest(chain);

        RequestBodyHelper requestBodyHelper = null;
        if (mEventReporter.isEnabled()) {
            requestBodyHelper = new RequestBodyHelper(mEventReporter, requestId);
            OkHttpInspectorRequest inspectorRequest =
                    new OkHttpInspectorRequest(requestId, request, requestBodyHelper);
            mEventReporter.requestWillBeSent(inspectorRequest);
        }

        Response response;
        try {
//            response = chain.b(request);
            response = Get.getResponse(chain, request);
        } catch (IOException e) {
            if (mEventReporter.isEnabled()) {
                mEventReporter.httpExchangeFailed(requestId, e.toString());
            }
            throw e;
        }

        if (mEventReporter.isEnabled()) {
            if (requestBodyHelper != null && requestBodyHelper.hasBody()) {
                requestBodyHelper.reportDataSent();
            }

            Connection connection = Get.getConnection((RealInterceptorChain) chain);
            mEventReporter.responseHeadersReceived(
                    new OkHttpInspectorResponse(
                            requestId,
                            request,
                            response,
                            connection));

//            ResponseBody body = response.KM();
            ResponseBody body = Get.getResponseBody(response);
            MediaType contentType = null;
            InputStream responseStream = null;
            if (body != null) {
//                contentType = body.JI();
                contentType = Get.getMediaType(body);
//                responseStream = body.KS().MK();
                responseStream = Get.getInputStream(Get.getBufferedSource(body));
            }

            responseStream = mEventReporter.interpretResponseStream(
                    requestId,
                    contentType != null ? contentType.toString() : null,
//                    response.header("Content-Encoding"),
                    Get.getHeader(response, "Content-Encoding"),
                    responseStream,
                    new DefaultResponseHandler(mEventReporter, requestId));
            if (responseStream != null) {
                response = Get.buildResponse(
                        Get.setForwardingResponseBody(
                                Get.getBuilder(response),
                                new ForwardingResponseBody(body, responseStream)
                        )
                );
//                        .newBuilder()
//                        .body(new ForwardingResponseBody(body, responseStream))
//                        .build();
            }
        }

        return response;
    }

    private static class OkHttpInspectorRequest implements NetworkEventReporter.InspectorRequest {
        private final String mRequestId;
        private final Request mRequest;
        private RequestBodyHelper mRequestBodyHelper;

        OkHttpInspectorRequest(
                String requestId,
                Request request,
                RequestBodyHelper requestBodyHelper) {
            mRequestId = requestId;
            mRequest = request;
            mRequestBodyHelper = requestBodyHelper;
        }

        @Override
        public String id() {
            return mRequestId;
        }

        @Override
        public String friendlyName() {
            // Hmm, can we do better?  tag() perhaps?
            return null;
        }

        @Nullable
        @Override
        public Integer friendlyNameExtra() {
            return null;
        }

        @Override
        public String url() {
            return Get.getHttpUrl(mRequest).toString();
        }

        @Override
        public String method() {
            return Get.getMethod(mRequest);
        }

        @Nullable
        @Override
        public byte[] body() throws IOException {
            RequestBody body = Get.getRequestBody(mRequest);
            if (body == null) {
                return null;
            }

            OutputStream out = mRequestBodyHelper.createBodySink(firstHeaderValue("Content-Encoding"));
            BufferedSink bufferedSink = Get.getBuffer(Okio.sink(out));
//             BufferedSink bufferedSink = Okio.buffer(Okio.sink(out));
            try {
//                body.a(bufferedSink);
                Get.setBufferedSink(body, bufferedSink);
            } finally {
                bufferedSink.close();
            }
            return mRequestBodyHelper.getDisplayBody();
        }

        @Override
        public int headerCount() {
            return Get.getHeaders(mRequest).size();
        }

        @Override
        public String headerName(int index) {
            return Get.getHeaders(mRequest).name(index);
        }

        @Override
        public String headerValue(int index) {
            return Get.getHeaders(mRequest).value(index);
        }

        @Nullable
        @Override
        public String firstHeaderValue(String name) {
            return Get.getHeader(mRequest, name);
        }
    }

    private static class OkHttpInspectorResponse implements NetworkEventReporter.InspectorResponse {
        private final String mRequestId;
        private final Request mRequest;
        private final Response mResponse;
        private @Nullable
        final Connection mConnection;

        OkHttpInspectorResponse(
                String requestId,
                Request request,
                Response response,
                @Nullable Connection connection) {
            mRequestId = requestId;
            mRequest = request;
            mResponse = response;
            mConnection = connection;
        }

        @Override
        public String requestId() {
            return mRequestId;
        }

        @Override
        public String url() {
            return Get.getHttpUrl(mRequest).toString();
        }

        @Override
        public int statusCode() {
            return Get.getCode(mResponse);
        }

        @Override
        public String reasonPhrase() {
            return mResponse.message();
        }

        @Override
        public boolean connectionReused() {
            // Not sure...
            return false;
        }

        @Override
        public int connectionId() {
            return mConnection == null ? 0 : mConnection.hashCode();
        }

        @Override
        public boolean fromDiskCache() {
            return Get.getResponse(mResponse) != null;
        }

        @Override
        public int headerCount() {
            return Get.getHeaders(mResponse).size();
        }

        @Override
        public String headerName(int index) {
            return Get.getHeaders(mResponse).name(index);
        }

        @Override
        public String headerValue(int index) {
            return Get.getHeaders(mResponse).value(index);
        }

        @Nullable
        @Override
        public String firstHeaderValue(String name) {
//            return mResponse.header(name);
            return Get.getHeader(mResponse, name);
        }
    }

    private static class ForwardingResponseBody extends ResponseBody {
        private final ResponseBody mBody;
        private final BufferedSource mInterceptedSource;

        public ForwardingResponseBody(ResponseBody body, InputStream interceptedStream) {
            mBody = body;
            mInterceptedSource = Get.getBuffer(Okio.source(interceptedStream));
        }

        @Nullable
        @Override
        public MediaType SI() {
            return mBody.SI();
        }

        @Override
        public long SJ() {
            return mBody.SJ();
        }

        @Override
        public BufferedSource SK() {
            return mInterceptedSource;
        }
    }
}
