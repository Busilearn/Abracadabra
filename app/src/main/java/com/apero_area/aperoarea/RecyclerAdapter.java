package com.apero_area.aperoarea;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apero_area.aperoarea.Model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by micka on 10-Aug-17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements RecyclerView.OnItemTouchListener {
    private List<Product> Products;

    public RecyclerAdapter (List<Product> Products) {
        this.Products = Products;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(Products.get(position).getName());
        holder.description.setText(Products.get(position).getDescription());
        holder.price.setText(Products.get(position).getPrice());

        Picasso.with(holder.image.getContext())
                .load(Products.get(position).getImages().get(0).getSrc())
                .error(R.mipmap.ic_launcher)
                .into(holder.image);


       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return Products.size();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, description, price;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
            price = (TextView)itemView.findViewById(R.id.price);
            image = (ImageView)itemView.findViewById(R.id.imageProduct);


        }
    }

}
