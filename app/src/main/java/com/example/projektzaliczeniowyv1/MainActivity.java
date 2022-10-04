package com.example.projektzaliczeniowyv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.projektzaliczeniowyv1.database.DbHelper;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private SQLiteDatabase db_read;
    private SQLiteDatabase db_write;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(getApplicationContext());
        db_read = dbHelper.getReadableDatabase();
        db_write = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.ItemEntry.COLUMN_NAME_TITLE, "title");
        values.put(DbHelper.ItemEntry.COLUMN_NAME_SUBTITLE,"subtitle");

        long newRowId = db_write.insert(DbHelper.ItemEntry.TABLE_NAME,null,values);


        String[] projection = {
                BaseColumns._ID,
                DbHelper.ItemEntry.COLUMN_NAME_TITLE,
                DbHelper.ItemEntry.COLUMN_NAME_SUBTITLE
        };

        String selection = DbHelper.ItemEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "title"};

        String sortOrder =
                DbHelper.ItemEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db_read.query(
                DbHelper.ItemEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()){
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(DbHelper.ItemEntry._ID)
            );
            itemIds.add(itemId);
        }
        cursor.close();
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
              break;
          case R.id.send_message:
            break;
          case R.id.share:
              break;
          case R.id.set_settings:
              break;
          case R.id.about:
              showAlertDialog(findViewById(R.id.about));
              break;
              default:
            return super.onOptionsItemSelected(item);
    }
        return true;
    }

   public void showAlertDialog(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.dialog_name);
        alert.setTitle(R.string.dialog_message);
        alert.create().show();
   }
}

