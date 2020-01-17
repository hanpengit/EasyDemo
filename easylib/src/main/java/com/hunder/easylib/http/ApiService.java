package com.hunder.easylib.http;

import com.hunder.easylib.entity.Home;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by hp on 2020/1/8.
 */

public interface ApiService {

    @GET("home/v5")
    Observable<Home> getHome();

}
