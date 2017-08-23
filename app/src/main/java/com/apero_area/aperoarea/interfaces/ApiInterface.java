package com.apero_area.aperoarea.interfaces;


import com.apero_area.aperoarea.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by micka on 10-Aug-17.
 */

public interface ApiInterface {

    @GET("products")
    Call<List<Product>> getProduct();
}