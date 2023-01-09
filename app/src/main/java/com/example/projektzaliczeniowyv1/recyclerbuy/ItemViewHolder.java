package com.example.projektzaliczeniowyv1.recyclerbuy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projektzaliczeniowyv1.R;
import com.example.projektzaliczeniowyv1.database.DbHelper;

import java.util.List;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    Context context;
    ItemAdapter adapter;
    TextView textViewTitle;
    TextView textViewPrice;
    ImageView imageViewHeadphone;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.titleRecyclerItem);
        textViewPrice = itemView.findViewById(R.id.priceRecyclerItem);
        imageViewHeadphone = itemView.findViewById(R.id.imageViewRecyclerItem);




    }

    public ItemViewHolder linkAdapter(ItemAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

}
