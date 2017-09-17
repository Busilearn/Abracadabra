package com.apero_area.aperoarea.checkout;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by micka on 15-Sep-17.
 */

public interface Interface {

    //This method is used for "POST"
    @FormUrlEncoded
    @POST("/charge.php")
    Call<ServerResponse> post(
            @Field("method") String method,
            @Field("mail") String mail,
            @Field("token") String token,
            @Field("amount") String amount,
            @Field("currency") String currency

    );

}