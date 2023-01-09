package com.example.projektzaliczeniowyv1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.projektzaliczeniowyv1.database.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Orders extends AppCompatActivity {
    private DbHelper dbHelper;
    private List phones;
    private List airpods;
    private List prices;
    private List watches;
    private List images;
    SQLiteDatabase db;

    ListView listView;
    HashMap<String, Object> hashMap;
    ArrayList<HashMap<String, Object>> itemList;
    SQLiteDatabase db_write;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        listView = findViewById(R.id.ordersListView);

        dbHelper = new DbHelper(getApplicationContext());
        db_write = dbHelper.getWritableDatabase();

        phones = dbHelper.readOrder(DbHelper.ItemEntry.COLUMN_NAME_PHONE_ORDER) ;
        airpods = dbHelper.readOrder(DbHelper.ItemEntry.COLUMN_NAME_AIRPODS_ORDER);
        watches = dbHelper.readOrder(DbHelper.ItemEntry.COLUMN_NAME_WATCH_ORDER);
        prices = dbHelper.readOrder(DbHelper.ItemEntry.COLUMN_NAME_PRICE_ORDER);
//        images = dbHelper.readOrder(DbHelper.ItemEntry.COLUMN_NAME_PHONE_PHOTO);


        itemList = new ArrayList();

        for (int i=0;i<phones.size();i++){
            hashMap = new HashMap<>();
            hashMap.put("phones", phones.get(i));
            hashMap.put("airpods", airpods.get(i));
            hashMap.put("watches",watches.get(i));
            hashMap.put("price",prices.get(i));
//            hashMap.put("image",Integer.parseInt(String.valueOf(images.get(i))));
            itemList.add(hashMap);

        }



        String [] from = {"phones","airpods","watches","price"};
        int [] to ={R.id.phoneItem, R.id.airpodsItem,R.id.watchItem , R.id.priceItem};

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getApplicationContext(),
                itemList,
                R.layout.list_view_orders_items,
                from,
                to
        );

        listView.setAdapter(simpleAdapter);
    }

}