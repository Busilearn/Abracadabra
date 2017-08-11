package com.apero_area.aperoarea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private List<product> products;
    private ApiInterface apiInterface;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        String username = "ck_fb2a39bf26ad4535b96a3d4e3a518e9378825148";
        String password = "cs_3709ea5013a44e175a5079bd67204f092a4a2d5e";

        String base = username + ":" + password;
        String authHeader ="Basic" + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<product>> call = apiInterface.getProduct(authHeader);


        call.enqueue(new Callback<List<product>>() {
            @Override
            public void onResponse(Call<List<product>> call, Response<List<product>> response) {

                products = response.body();
                Log.d("test", products.toString());
               /* adapter = new RecyclerAdapter(products);
                recyclerView.setAdapter(adapter);*/
            }

            @Override
            public void onFailure(Call<List<product>> call, Throwable t) {

            }
        });


    }

}
