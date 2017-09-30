package com.oloh.oloh.domain.api;

import com.oloh.oloh.domain.helper.NetworkConstants;
import com.oloh.oloh.model.entities.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by stran on 01/09/2017.
 */

public interface ApiInterface {
    //This method is used for "GET" data
    @GET(NetworkConstants.URL_GET_PRODUCTS_MAP)
    Call<ArrayList<Product>> getProduct();


    //This method is used for "POST" data
    @FormUrlEncoded
    @POST(NetworkConstants.URL_POST_OLOH_CHARGE)
    Call<ServerResponse> post(
            @Field("method") String method,
            @Field("name") String name,
            @Field("firstName") String firstName,
            @Field("phoneNumber") String phoneNumber,
            @Field("mail") String mail,
            @Field("token") String token,
            @Field("amount") String amount,
            @Field("currency") String currency,
            @Field("notes") String notes,
            @Field("idProduct[]") ArrayList<String> idProduct,
            @Field("quantityProduct[]") ArrayList<String> quantityProduct,
            @Field("posGps") String posGps
    );
}
