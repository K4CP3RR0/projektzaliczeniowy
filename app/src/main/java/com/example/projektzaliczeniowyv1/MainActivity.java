package com.example.projektzaliczeniowyv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.projektzaliczeniowyv1.database.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private List titles;
    private List images;
    private List prices;


    private static final String TAGsms = "SMS1111";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 2;
    SmsManager smsManager;
    String destinationAddress = "";
    String scAddress = null;
    String text="";
    PendingIntent sentIntent = null;
    PendingIntent deliveryIntent;
    long messageId = 0;
    EditText phoneNumber;
    EditText smsMessage;
    Button sendSms;


    private static final String TAG="1111";
    ListView listView;
    Button addItem;
    HashMap<String, Object> hashMap;
    ArrayList<HashMap<String, Object>> itemList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(getApplicationContext());
        SQLiteDatabase db_write = dbHelper.getWritableDatabase();

/*
        db_write.execSQL("CREATE TABLE "
                + DbHelper.ItemEntry.TABLE_NAME + " ("
                + DbHelper.ItemEntry._ID + " INTEGER PRIMARY KEY,"
                + DbHelper.ItemEntry.COLUMN_NAME_PHONE + " TEXT,"
                + DbHelper.ItemEntry.COLUMN_NAME_PHONE_PHOTO + " INT, "
                + DbHelper.ItemEntry.COLUMN_NAME_PHONE_PRICE + " INT, "
                + DbHelper.ItemEntry.COLUMN_NAME_AMOUNT + " INT, "
                + DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES + " TEXT, "
                + DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES_PHOTO + " INT, "
                + DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES_PRICE + " INT, "
                + DbHelper.ItemEntry.COLUMN_NAME_WATCH + " TEXT, "
                + DbHelper.ItemEntry.COLUMN_NAME_WATCH_PHOTO + " INT, "
                + DbHelper.ItemEntry.COLUMN_NAME_WATCH_PRICE + " INT )");*/

        /*ContentValues values1 = new ContentValues();

        values1.put(DbHelper.ItemEntry.COLUMN_NAME_PHONE,"Apple iPhone 14 Pro 512GB Space Black");
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_PHONE_PHOTO, R.drawable.iphone1);
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_PHONE_PRICE,8499);
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES,"Apple AirPods Pro (2nd gen)");
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES_PHOTO,R.drawable.airpods1);
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES_PRICE,1449);
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_WATCH,"Apple Watch Series 8 Black 41mm");
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_WATCH_PHOTO,R.drawable.watch1);
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_WATCH_PRICE,2399);

        long newRowId = db_write.insert(DbHelper.ItemEntry.TABLE_NAME,null,values1);*/
        titles = dbHelper.readData(DbHelper.ItemEntry.COLUMN_NAME_PHONE) ;
        images = dbHelper.readData(DbHelper.ItemEntry.COLUMN_NAME_PHONE_PHOTO);
        prices = dbHelper.readData(DbHelper.ItemEntry.COLUMN_NAME_PHONE_PRICE);

        listView = findViewById(R.id.simpleListView);

       itemList = new ArrayList();

       for (int i=0;i<titles.size();i++){
           hashMap = new HashMap<>();
           hashMap.put("title", titles.get(i));
           hashMap.put("image", images.get(i));
           hashMap.put("price",prices.get(i));
           itemList.add(hashMap);

       }



       String [] from = {"title","image","price"};
       int [] to ={R.id.titleItem, R.id.imageItem,  R.id.priceItem};

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getApplicationContext(),
                itemList,
                R.layout.list_view_items,
                from,
                to
        );

        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), (CharSequence) itemList.get(i),Toast.LENGTH_SHORT).show();
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()){
          case R.id.order_list:
              orderList();
              break;
          case R.id.send_message:
              sendMessage();
            break;
          case R.id.share:
              shareList();
              break;
          case R.id.set_settings:
              saveSettings();
              break;
          case R.id.about:
              showAlertDialog(findViewById(R.id.about));
              break;
              default:
            return super.onOptionsItemSelected(item);
    }
        return true;
    }

    private void saveSettings() {
    }

    private void shareList() {
    }

    private void sendMessage() {
        if (checkPermission(Manifest.permission.SEND_SMS)){
            destinationAddress = phoneNumber.getText().toString();
            text = smsMessage.getText().toString();
            if(!destinationAddress.equals("")&& !text.equals("")){
                smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(
                        destinationAddress,
                        null,
                        text,
                        null,
                        null
                );
                Toast.makeText(MainActivity.this,"SMS send", Toast.LENGTH_SHORT).show();
                Log.v(TAGsms,"Sms send");

            }
            else{
                Toast.makeText(MainActivity.this,"Permission denied", Toast.LENGTH_SHORT).show();
                Log.v(TAGsms,"Permission denied");
            }
        }
    }

    private boolean checkPermission(String sendSms) {return true;}


    private void orderList() {
    }

    public void showAlertDialog(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.dialog_name);
        alert.setTitle(R.string.dialog_message);
        alert.create().show();
   }
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
   
   


}

