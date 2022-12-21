package com.example.projektzaliczeniowyv1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    Spinner airpodsSpinner;
    int whatItem;
    CheckBox airpodsCheckbox;
    CheckBox watchCheckbox;
    RelativeLayout activityBuy;
    TextView watchSelected;
    TextView airpodsSelected;
    SendEmail sendEmail;
    SendMessage sendMessage = new SendMessage();
    String destinationAddress;
    String orderInfo;
    TextInputEditText inputEmailBuy;
    TextInputEditText inputNumberBuy;
    Button smsButton;
    Boolean watchPreferences;
    Boolean airpodsPreferences;
    int airpodsPrice = 0;
    int watchPrice = 0;
    int phonePrice;
    int price;
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

        airpodsCheckbox = findViewById(R.id.airpodsCheckbox);
        watchCheckbox = findViewById(R.id.watchCheckbox);

        watchSelected = findViewById(R.id.watchSelected);
        airpodsSelected = findViewById(R.id.airpodsSelected);

        inputNumberBuy  = findViewById(R.id.inputNumberBuy);

        productName.setText(getIntent().getStringExtra("productName"));
        productPrice.setText(getIntent().getStringExtra("productPrice"));
        productPhoto.setImageResource(getIntent().getIntExtra("productImage",1));

//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        titles = dbHelper.readData("headphones");
        prices = dbHelper.readData("headphones_price");
        images = dbHelper.readData("headphones_photo");
        titlesWatches = dbHelper.readData("watch");
        pricesWatches = dbHelper.readData("watch_price");
        imagesWatches = dbHelper.readData("watch_photo");

        Log.v("ViewHolder: ", String.valueOf(titles));
        Log.v("ViewHolder: ", String.valueOf(prices));
        Log.v("Watches:",String.valueOf(titlesWatches));

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        airpodsSpinner = findViewById(R.id.spinnerAirpods);
        airpodsSpinner.setOnItemSelectedListener(this);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(),titlesWatches,pricesWatches,imagesWatches);
        spinner.setAdapter(spinnerAdapter);
        SpinnerAdapter spinnerAdapter1 = new SpinnerAdapter(getApplicationContext(),titles, prices,images);
        airpodsSpinner.setAdapter(spinnerAdapter1);

        watches = String.valueOf(titlesWatches);



        TextView orderList = findViewById(R.id.orderList);
        orderList.setText("Your order: " + getIntent().getStringExtra("productName") + " " + Integer.parseInt(getIntent().getStringExtra("productPrice")));


        airpodsCheckbox.setChecked(true);
        //airpodsPreferences);
        airpodsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Log.v("AIRPODS",String.valueOf(isChecked));
                if(isChecked){
                    airpodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            airpodsSelected.setText(String.valueOf(titles.get(i)) + " " + Integer.parseInt(String.valueOf(prices.get(i))));
                            airpodsPrice = Integer.parseInt(String.valueOf(prices.get(i)));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }else{
                    airpodsSelected.setText("");
                }
            }
        });
        watchCheckbox.setChecked(true);
                //watchPreferences);
        watchCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.v("WATCH",String.valueOf(b));
                if(b){
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            watchSelected.setText(String.valueOf(titlesWatches.get(i)) + " " + Integer.parseInt(String.valueOf(pricesWatches.get(i))));
                            watchPrice = Integer.parseInt(String.valueOf(pricesWatches.get(i)));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }else{
                    watchSelected.setText("");
                }

            }
        });


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
        Button checkPrice = findViewById(R.id.checkPrice);
        checkPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonePrice = Integer.parseInt(getIntent().getStringExtra("productPrice"));
                if (watchPrice!=0 || airpodsPrice!=0){
                    price = phonePrice + watchPrice + airpodsPrice;
                }
                else if(watchPrice!=0 || airpodsPrice==0){
                    price = phonePrice + watchPrice;

                }else if(watchPrice==0 || airpodsPrice!=0){
                    price = phonePrice + airpodsPrice;
                }else if(watchPrice==0 || airpodsPrice==0){
                    price = phonePrice;
                }
                Log.v("PRICE", "Price: " + String.valueOf(price));
            }
        });







    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("airpodsCheckbox",airpodsCheckbox.isChecked());
        editor.putBoolean("watchCheckbox",watchCheckbox.isChecked());


        editor.apply();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        airpodsPreferences = sharedPref.getBoolean("airpodsCheckbox",airpodsCheckbox.isChecked());
        watchPreferences = sharedPref.getBoolean("watchCheckbox",watchCheckbox.isChecked());
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