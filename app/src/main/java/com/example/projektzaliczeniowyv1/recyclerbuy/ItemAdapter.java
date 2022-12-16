package com.example.projektzaliczeniowyv1.recyclerbuy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projektzaliczeniowyv1.R;
import com.example.projektzaliczeniowyv1.database.DbHelper;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    Context context;
    List titles;
    List prices;
    List images;
    DbHelper dbHelper;
    SQLiteDatabase db_read;



    public ItemAdapter(List titles,List prices, List images) {
        this.titles = titles;
        this.prices = prices;
        this.images = images;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        return new ItemViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.textViewTitle.setText(String.valueOf(titles.get(position)));
        holder.textViewPrice.setText(String.valueOf(prices.get(position)));
        holder.imageViewHeadphone.setImageResource(Integer.parseInt(String.valueOf(images.get(position))));
        //Log.v("ViewHolder:", String.valueOf(titles.size()));

    }

    @Override
    public int getItemCount() {
        return Integer.parseInt(String.valueOf(titles.size()));
    }
}

