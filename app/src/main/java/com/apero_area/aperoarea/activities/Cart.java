package com.apero_area.aperoarea.activities;

/**
 * Created by dany on 20/08/2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apero_area.aperoarea.R;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
