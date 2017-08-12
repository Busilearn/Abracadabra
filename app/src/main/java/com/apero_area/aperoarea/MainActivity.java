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

import java.util.List;

import io.realm.Realm;
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
    private Realm realm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager (this);
        //recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Product>> call = apiInterface.getProduct();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                products = response.body();
                if (products.size() != 0) {
                    adapter = new RecyclerAdapter(products, new RecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Product products) {
                            Log.d("test","click " + products);
                            realm.beginTransaction();
                            Product productsRealm = realm.copyToRealm(products);
                            realm.commitTransaction();

                            Intent intent = new Intent(MainActivity.this, ProductView.class);
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
