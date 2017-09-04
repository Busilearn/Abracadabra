package com.apero_area.aperoarea.domain.helper;

import com.apero_area.aperoarea.model.entities.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by stran on 01/09/2017.
 */

public interface ApiInterface {

    @GET("products")
    Observable<ArrayList<Product>> getProduct();
}
