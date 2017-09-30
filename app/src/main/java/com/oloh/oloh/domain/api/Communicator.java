package com.oloh.oloh.domain.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.oloh.oloh.domain.helper.NetworkConstants;
import com.oloh.oloh.util.AppConstants;
import com.oloh.oloh.view.activities.MainActivity;
import com.squareup.otto.Produce;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by micka on 15-Sep-17.
 */

public class Communicator {

    private static  final String TAG = "Communicator";
    private FrameLayout progressBarContainer;

    public void loginPost(String methode, String name, String firstName, String phoneNumber, String mail, String token, String amount, String currency, String notes, ArrayList<String> idProduct, ArrayList<String> quantityProduct, String posGps,
            final Context context, FrameLayout progressBarContainerPay){

        progressBarContainer = progressBarContainerPay;

        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, in order to get connection logs
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NetworkConstants.SERVER_URL)
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);

        Call<ServerResponse> call = service.post(methode, name, firstName, phoneNumber , mail, token, amount, currency, notes, idProduct, quantityProduct, posGps);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                //Permet l'affichage de la réponse du serveur BusProvider.getInstance().post(new ServerEvent(response.body()));
                progressBarContainer.setVisibility(View.GONE);
                ServerResponse serverResponse = response.body();

                if ((serverResponse != null ? serverResponse.getResponseCode() : 0) == 1){
                    Toast.makeText(context, "Merci pour votre achat, vous serez livré prochainement à votre position GPS", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(context,MainActivity.class);
                    context.startActivity(Intent);
                }else {
                    Toast.makeText(context, "Une erreur s'est produite veuillez réessayer ultérieurement", Toast.LENGTH_LONG).show();
                    Intent Intent = new Intent(context,MainActivity.class);
                    context.startActivity(Intent);
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                progressBarContainer.setVisibility(View.GONE);
                Toast.makeText(context, "Une erreur s'est produite veuillez réessayer ultérieurement", Toast.LENGTH_LONG).show();
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
            }
        });
    }

    @Produce
    public ServerEvent produceServerEvent(ServerResponse serverResponse) {
        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }

    }