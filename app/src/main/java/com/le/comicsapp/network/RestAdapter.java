package com.le.comicsapp.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.le.comicsapp.R;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAdapter {

    private static final String TAG = "RestAdapter";
    public static final int READ_TIMEOUT_SECS = 60;
    public static final int CONNECTION_TIMEOUT_SECS = 60;

    public static Retrofit createRetrofit(@NonNull Context ctx) {
        GsonConverterFactory gsonFactory = GsonConverterFactory.create();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT_SECS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECS, TimeUnit.SECONDS)
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {

                                Request.Builder builder = chain.request().newBuilder();
                                builder.addHeader("Content-Type", "application/json");
                                Request request = builder.build();

                                String requestSummary = "--> " + request.toString();

                                Charset UTF8 = Charset.forName("UTF-8");
                                RequestBody requestBody = request.body();
                                if (requestBody != null) {
                                    Buffer buffer = new Buffer();
                                    requestBody.writeTo(buffer);
                                    requestSummary = requestSummary + "\n" +
                                            "--> Content-Type: application/json\n" +
                                            "--> " + buffer.readString(UTF8);
                                }

                                Log.i(TAG, requestSummary);

                                Response response = chain.proceed(request);
                                String responseSummary = String.format(Locale.ENGLISH, "<-- %s", response.toString());
                                ResponseBody responseBody = response.body();
                                if (responseBody != null) {
                                    BufferedSource source = responseBody.source();
                                    source.request(Long.MAX_VALUE);
                                    Buffer buffer = source.buffer();
                                    responseSummary = responseSummary + "\n" +
                                            "<-- " + buffer.clone().readString(UTF8);
                                }

                                Log.i(TAG, responseSummary);
                                return response;
                            }
                        });


        OkHttpClient httpClient = httpClientBuilder.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ctx.getString(R.string.marvel_api_url))
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient).build();

        return retrofit;
    }
}
