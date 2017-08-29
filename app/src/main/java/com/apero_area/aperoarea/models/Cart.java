package com.apero_area.aperoarea.models;


import android.text.Html;
import android.text.Spanned;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by micka on 10-Aug-17.
 */

public class Cart {
    private ArrayList<Product> cartItems = new ArrayList<Product>();
    public Product getProducts(int position){
        return cartItems.get(position);
    }
    public void setProducts(Product Products){
        cartItems.add(Products);
    }
    public int getCartsize(){
        return cartItems.size();
    }
    public boolean CheckProductInCart(Product aproduct){
        return cartItems.contains(aproduct);
    }

}

