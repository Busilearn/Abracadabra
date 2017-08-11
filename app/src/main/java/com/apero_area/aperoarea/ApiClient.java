package com.apero_area.aperoarea;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by micka on 10-Aug-17.
 */

public class ApiClient {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static String username = "ck_fb2a39bf26ad4535b96a3d4e3a518e9378825148";
    private static String password = "cs_3709ea5013a44e175a5079bd67204f092a4a2d5e";


    public static final String BASE_URL = "https://apero-area.com/wp-json/wc/v2/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor(username, password))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }



}
