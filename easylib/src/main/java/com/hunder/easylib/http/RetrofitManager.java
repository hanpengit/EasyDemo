package com.hunder.easylib.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hp on 2020/1/9.
 */
public class RetrofitManager {

    private String HOST = "http://app.exuefu.cn/";

    private static RetrofitManager instance;
    private Retrofit mRetrofit;
    private ApiService mApiService;

    private RetrofitManager() {
        init(HOST);
    }

    private void init(String baseUrl) {
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(build)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        mApiService = mRetrofit.create(ApiService.class);
    }

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    public void changeBaseUrl(String baseUrl) {
        init(baseUrl);
    }

    public static ApiService getApiService() {
        return getInstance()._getApiService();
    }

    public static <T> T create(Class<T> api) {
        T t = getInstance().getRetrofit().create(api);
        return t;
    }

    private Retrofit getRetrofit() {
        return mRetrofit;
    }

    private ApiService _getApiService() {
        return mApiService;
    }

    public Retrofit getRetrofit(String url) {
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .client(build)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }

    public Retrofit getRetrofitForString(String url) {
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .client(build)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }

}
