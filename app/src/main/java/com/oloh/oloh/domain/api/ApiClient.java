package com.oloh.oloh.domain.api;

import com.oloh.oloh.domain.helper.NetworkConstants;
import com.oloh.oloh.util.AppConstants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by micka on 01/09/2017.
 */

public class ApiClient {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor(AppConstants.username, AppConstants.password))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkConstants.SERVER_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }



}