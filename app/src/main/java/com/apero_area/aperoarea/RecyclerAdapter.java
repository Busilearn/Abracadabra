package com.apero_area.aperoarea;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by micka on 10-Aug-17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
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
    }

    @Override
    public int getItemCount() {
        return Products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, description, price;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
            price = (TextView)itemView.findViewById(R.id.price);


        }
    }

}
