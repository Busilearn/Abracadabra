package com.apero_area.aperoarea.activities;

/**
 * Created by dany on 20/08/2017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.apero_area.aperoarea.R;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
