package com.apero_area.aperoarea.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.apero_area.aperoarea.R;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);
        Card cardToSave = mCardInputWidget.getCard();
        //cardToSave.setName("toto");
       //cardToSave.setAddressZip("94170");

        if (cardToSave == null) {
            Log.d("test","Invalid Card Data");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }

}
