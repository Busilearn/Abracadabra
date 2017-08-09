package com.apero_area.aperoarea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private static final String NETWORK_NAME = "Woocommerce";
    private static final String RESOURCE_URL = "https://apero-area/wc-api/v1/orders/count";
    private static final String SCOPE = "*"; //all permissions


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();





    }
}
