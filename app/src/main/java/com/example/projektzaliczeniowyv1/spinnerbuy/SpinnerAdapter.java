package com.example.projektzaliczeniowyv1.spinnerbuy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projektzaliczeniowyv1.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    List titlesWatches;
    List pricesWatches;
    List imagesWatches;
    LayoutInflater inflater;

    public SpinnerAdapter(Context context, List titlesWatches, List pricesWatches, List imagesWatches) {
        this.context = context;
        this.titlesWatches = titlesWatches;
        this.pricesWatches = pricesWatches;
        this.imagesWatches = imagesWatches;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imagesWatches.size();

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.spinner_item,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewSpinnerItem);
        TextView title = (TextView) view.findViewById(R.id.titleSpinnerItem);
        TextView price = (TextView) view.findViewById(R.id.priceSpinnerItem);
        imageView.setImageResource(Integer.parseInt(String.valueOf(imagesWatches.get(i))));
        title.setText(String.valueOf(titlesWatches.get(i)));
        price.setText(String.valueOf(pricesWatches.get(i)));
        return view;
    }
}
