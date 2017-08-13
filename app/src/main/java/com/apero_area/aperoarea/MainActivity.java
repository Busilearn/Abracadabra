package com.apero_area.aperoarea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.apero_area.aperoarea.Model.Product;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private List<Product> products;
    private ApiInterface apiInterface;
    private ImageView imageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setHasFixedSize(true);

        // Build of the retrofit object
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        // Make the request
        Call<List<Product>> call = apiInterface.getProduct();
        //
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                products = response.body();
                if (products.size() != 0) {
                    adapter = new RecyclerAdapter(products, new RecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Product products) {
                            Log.d("test","click " + products);

                            Intent intent = new Intent(MainActivity.this, ProductViewActivity.class);
                            Gson gson = new Gson();
                            String productJson = gson.toJson(products);
                            intent.putExtra("productJson", productJson);

                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("test", "echec" + t);
            }
        });
    }

}
