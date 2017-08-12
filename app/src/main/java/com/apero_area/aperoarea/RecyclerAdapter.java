package com.apero_area.aperoarea;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apero_area.aperoarea.Model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by micka on 10-Aug-17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<Product> products;


    public interface OnItemClickListener {
        void onItemClick(Product item);
    }

    private final OnItemClickListener listener;

    public RecyclerAdapter(List<Product> products, OnItemClickListener listener) {
        this.products = products;
        this.listener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.name.setText(products.get(position).getName());
        holder.short_description.setText(products.get(position).getShort_description());
        holder.price.setText(products.get(position).getPrice());
final int test = position;
        holder.bind(products.get(position), listener);


        Picasso.with(holder.image.getContext())
                .load(products.get(position).getImages().get(0).getSrc())
                .error(R.mipmap.ic_launcher)
                .into(holder.image);

        holder.addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // Log.d("test", products.get(test).getName());
                Log.d("test", "click");
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, short_description, price;
        ImageView image;
        ImageButton addproduct, minusproduct;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            short_description = (TextView)itemView.findViewById(R.id.short_description);
            price = (TextView)itemView.findViewById(R.id.price);
            image = (ImageView)itemView.findViewById(R.id.imageProduct);
            addproduct = (ImageButton) itemView.findViewById(R.id.addproduct);
            minusproduct = (ImageButton) itemView.findViewById(R.id.minusproduct);

        }

        public void bind(final Product products, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(products);
                }
            });
        }




    }

}
