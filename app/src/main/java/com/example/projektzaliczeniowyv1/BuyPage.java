package com.example.projektzaliczeniowyv1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.projektzaliczeniowyv1.database.DbHelper;
import com.example.projektzaliczeniowyv1.messageSystem.SendEmail;
import com.example.projektzaliczeniowyv1.messageSystem.SendMessage;
import com.example.projektzaliczeniowyv1.recyclerbuy.ItemAdapter;
import com.example.projektzaliczeniowyv1.spinnerbuy.SpinnerAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Collections;
import java.util.List;

public class BuyPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TextView productPrice;
    TextView productName;
    ImageView productPhoto;
    DbHelper dbHelper;
    SQLiteDatabase db_read;
    List titles;
    List prices;
    List images;
    List titlesWatches;
    List pricesWatches;
    List imagesWatches;
    String watches;
    Spinner spinner;
    int whatItem;
    RelativeLayout activityBuy;

    SendEmail sendEmail;
    SendMessage sendMessage = new SendMessage();
    String destinationAddress;
    String orderInfo;
    TextInputEditText inputEmailBuy;
    TextInputEditText inputNumberBuy;
    Button smsButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_page);
        activityBuy = findViewById(R.id.activityBuy);
        //activityBuy.setBackgroundColor(Color.rgb(244,244,244));
        dbHelper = new DbHelper(getApplicationContext());
        db_read = dbHelper.getReadableDatabase();


        productName = findViewById(R.id.titleItem);
        productPrice = findViewById(R.id.priceItem);
        productPhoto = findViewById(R.id.imageItem);
        smsButton = findViewById(R.id.smsButton);

        productName.setText(getIntent().getStringExtra("productName"));
        productPrice.setText(getIntent().getStringExtra("productPrice"));
        productPhoto.setImageResource(getIntent().getIntExtra("productImage",1));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        titles = dbHelper.readData("headphones");
        prices = dbHelper.readData("headphones_price");
        images = dbHelper.readData("headphones_photo");
        titlesWatches = dbHelper.readData("watch");
        pricesWatches = dbHelper.readData("watch_price");
        imagesWatches = dbHelper.readData("watch_photo");
        Log.v("ViewHolder: ", String.valueOf(titles));
        Log.v("ViewHolder: ", String.valueOf(prices));
        Log.v("Watches:",String.valueOf(titlesWatches));

        ItemAdapter itemAdapter = new ItemAdapter(titles, prices,images);
        recyclerView.setAdapter(itemAdapter);


        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        watches = String.valueOf(titlesWatches);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(),titlesWatches,pricesWatches,imagesWatches);
        spinner.setAdapter(spinnerAdapter);

        //inputEmailBuy  = findViewById(R.id.inputEmailBuy);
        inputNumberBuy  = findViewById(R.id.inputNumberBuy);



        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SendMessage.class);
                destinationAddress = inputNumberBuy.getText().toString();
                Log.v("SMSS",destinationAddress);
                orderInfo = "Zamówienie: \n" + productName.getText().toString() + "- " + productPrice.getText().toString();
                intent.putExtra("phoneNumber", destinationAddress);
                intent.putExtra("orderInfo", orderInfo);
                startActivity(intent);


            }
        });






    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            whatItem = i;
            Log.v("ITEM", String.valueOf(whatItem));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}