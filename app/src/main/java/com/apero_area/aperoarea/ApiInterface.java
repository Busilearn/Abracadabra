package com.apero_area.aperoarea;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


/**
 * Created by micka on 10-Aug-17.
 */

public interface ApiInterface {

    @GET("products")
    Call<List<product>> getProduct(@Header("Authorization") String authHeader);
}
