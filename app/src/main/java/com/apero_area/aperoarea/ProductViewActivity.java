package com.apero_area.aperoarea;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;

import com.apero_area.aperoarea.Model.Product;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


public class ProductViewActivity extends AppCompatActivity {

    private TextView name, shortDescription, description, price;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);


        Gson gson = new Gson();
        Product product = gson.fromJson(getIntent().getStringExtra("productJson"), Product.class);

        name = (TextView)findViewById(R.id.SimpleProductName);
        shortDescription = (TextView)findViewById(R.id.SimpleProductShortDecription);
        description = (TextView)findViewById(R.id.SimpleProductDescription);
        price = (TextView)findViewById(R.id.SimpleProductPrice);
        image = (ImageView)findViewById(R.id.SimpleProductImage);


        name.setText(product.getName());
        shortDescription.setText(product.getShort_description());
        description.setText(product.getDescription());
        price.setText(product.getPrice());

        Picasso.with(getBaseContext())
                .load(product.getImages().get(0).getSrc())
                .error(R.mipmap.ic_launcher)
                .into(image);
    }

}
