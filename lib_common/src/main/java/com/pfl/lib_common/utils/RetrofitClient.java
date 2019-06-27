package com.pfl.lib_common.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * RetrofitClient
 */
public class RetrofitClient {

    private static final int DEFAULT_TIMEOUT = 5;
    private static Retrofit mRetrofit;

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(App.getInstance());
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static RetrofitClient getInstance(String url) {
        return new RetrofitClient(App.getInstance(), url);
    }

    private RetrofitClient(Context context) {
        this(context, null);
    }

    private RetrofitClient(Context context, String url) {

        if (TextUtils.isEmpty(url)) {
            url = BaseUrlManager.getBaseUrl();
        }

        OkHttpClient okHttpClient = getOkHttpClient();

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
    }

    public OkHttpClient getOkHttpClient() {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .retryOnConnectionFailure(true)
                .cookieJar(new CookieManger(App.getInstance()))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

    }

    public <T> T create(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

}