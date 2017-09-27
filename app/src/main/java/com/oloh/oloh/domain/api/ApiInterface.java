package com.oloh.oloh.domain.api;

import com.oloh.oloh.model.entities.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by stran on 01/09/2017.
 */

public interface ApiInterface {

    @GET("products")
    Call<ArrayList<Product>> getProduct();
}
