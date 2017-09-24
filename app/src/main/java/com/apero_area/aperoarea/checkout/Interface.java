package com.apero_area.aperoarea.checkout;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by micka on 15-Sep-17.
 */

public interface Interface {

    //This method is used for "POST"
    @FormUrlEncoded
    @POST("/wp-content/plugins/apero-area/payment_api/charge.php")
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
            //Send the id en quantity of each product in the cart
            //@FieldMap Map<String, String> product
            @Field("idProduct[]") ArrayList<String> idProduct,
            @Field("quantityProduct[]") ArrayList<String> quantityProduct,
            @Field("posGps") String posGps

    );
}