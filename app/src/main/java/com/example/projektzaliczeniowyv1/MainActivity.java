package com.example.projektzaliczeniowyv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.projektzaliczeniowyv1.database.DbHelper;
import com.example.projektzaliczeniowyv1.messageSystem.SendEmail;
import com.example.projektzaliczeniowyv1.messageSystem.SendMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private List titles;
    private List images;
    private List prices;
    SQLiteDatabase db;

    private static final String TAG="1111";
    ListView listView;
    HashMap<String, Object> hashMap;
    ArrayList<HashMap<String, Object>> itemList;
    SQLiteDatabase db_write;
    ShortcutInfo shortcut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(getApplicationContext());
        db_write = dbHelper.getWritableDatabase();
        try{

        } catch (SQLiteException e){
            if (e.getMessage().contains("no such table")){
                Log.e(TAG, "Creating table " + DbHelper.ItemEntry.TABLE_NAME + "because it doesn't exist!" );
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
                + DbHelper.ItemEntry.COLUMN_NAME_WATCH_PRICE + " INT )");




            }
        }
       /* ContentValues values1 = new ContentValues();
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_PHONE, "Apple iPhone 14 Pro 512GB Space Black");
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_PHONE_PHOTO, R.drawable.iphone1);
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_PHONE_PRICE, 8499);
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES, "Apple AirPods Pro (2nd gen)");
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES_PHOTO, R.drawable.airpods1);
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES_PRICE, 1449);
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_WATCH, "Apple Watch Series 8 Black 41mm");
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_WATCH_PHOTO, R.drawable.watch1);
        values1.put(DbHelper.ItemEntry.COLUMN_NAME_WATCH_PRICE, 2399);
        long newRowId = db_write.insert(DbHelper.ItemEntry.TABLE_NAME, null, values1);

        ContentValues values2 = new ContentValues();
        values2.put(DbHelper.ItemEntry.COLUMN_NAME_PHONE, "Apple iPhone 13 256 GB – Blue");
        values2.put(DbHelper.ItemEntry.COLUMN_NAME_PHONE_PHOTO, R.drawable.iphone2);
        values2.put(DbHelper.ItemEntry.COLUMN_NAME_PHONE_PRICE, 5199);
        values2.put(DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES, "AirPods (3rd generation) with MagSafe Charging Case");
        values2.put(DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES_PHOTO, R.drawable.airpods2);
        values2.put(DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES_PRICE, 1099);
        values2.put(DbHelper.ItemEntry.COLUMN_NAME_WATCH, "Apple Watch SE Midnight Aluminium Case with Sport Loop 40mm");
        values2.put(DbHelper.ItemEntry.COLUMN_NAME_WATCH_PHOTO, R.drawable.watch2);
        values2.put(DbHelper.ItemEntry.COLUMN_NAME_WATCH_PRICE, 1499);
        long newRowId2 = db_write.insert(DbHelper.ItemEntry.TABLE_NAME, null, values2);

        ContentValues values3 = new ContentValues();
        values3.put(DbHelper.ItemEntry.COLUMN_NAME_PHONE, "Apple iPhone 12 256GB Black");
        values3.put(DbHelper.ItemEntry.COLUMN_NAME_PHONE_PHOTO, R.drawable.iphone3);
        values3.put(DbHelper.ItemEntry.COLUMN_NAME_PHONE_PRICE, 8499);
        values3.put(DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES, "Apple AirPods Max Space Gray");
        values3.put(DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES_PHOTO, R.drawable.airpods3);
        values3.put(DbHelper.ItemEntry.COLUMN_NAME_HEADPHONES_PRICE, 3099);
        values3.put(DbHelper.ItemEntry.COLUMN_NAME_WATCH, "Apple Watch Ultra Titanium Case with Starlight Alpine Loop 49mm");
        values3.put(DbHelper.ItemEntry.COLUMN_NAME_WATCH_PHOTO, R.drawable.watch3);
        values3.put(DbHelper.ItemEntry.COLUMN_NAME_WATCH_PRICE, 4799);
        long newRowId3 = db_write.insert(DbHelper.ItemEntry.TABLE_NAME, null, values3);
*/






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
                Intent intent = new Intent(getApplicationContext(),BuyPage.class);
                startActivity(intent);
                
                Toast.makeText(getApplicationContext(), (CharSequence) itemList.get(i),Toast.LENGTH_SHORT).show();
            }
        });


    addDynamicShortcuts(this);


    }

    private void addDynamicShortcuts(Context context) {
        ShortcutManager shortcutManager = (ShortcutManager) getSystemService(Context.SHORTCUT_SERVICE);
        List<ShortcutInfo> shortcutInfoList = new ArrayList<>();
        shortcut = new ShortcutInfo.Builder(context,"open")
                .setShortLabel("Sprawdź tekst")
                .setLongLabel("Sprawdź wiadomości tekstowe")
                .setIcon(Icon.createWithResource(context,R.drawable.ic_baseline_format_list_numbered_24))
                .setIntent(new Intent(Intent.ACTION_VIEW,null,context, SendMessage.class).setAction("sms"))
                .build();
    }

    private void addProducts(){

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
             Intent intentSMS = new Intent(this, SendMessage.class);
             startActivity(intentSMS);

            break;
          case R.id.send_email:
              Intent intentEmail = new Intent(this, SendEmail.class);
              startActivity(intentEmail);

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
    private void orderList() {
    }

    public void showAlertDialog(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.dialog_name);
        alert.setTitle(R.string.dialog_message);
        Dialog d  = alert.setView(new View(this)).create();
        int width=(int) (getResources().getDisplayMetrics().widthPixels*0.70);
        int height=(int) (getResources().getDisplayMetrics().heightPixels*0.20);
        d.show();
        d.getWindow().setLayout(width,height);
   }
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
   
   


}

