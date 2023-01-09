package com.example.projektzaliczeniowyv1.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ShopD.db";
    public DbHelper(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String TABLE_NAME1 = "user";
        public static final String TABLE_NAME2 = "orders";
        //        public static final String COLUMN_NAME_TITLE = "title";
//        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_PHONE_PHOTO = "phone_photo";
        public static final String COLUMN_NAME_PHONE_PRICE = "phone_price";

        public static final String COLUMN_NAME_AMOUNT = "amount";

        public static final String COLUMN_NAME_HEADPHONES = "headphones";
        public static final String COLUMN_NAME_HEADPHONES_PHOTO = "headphones_photo";
        public static final String COLUMN_NAME_HEADPHONES_PRICE = "headphones_price";

        public static final String COLUMN_NAME_WATCH = "watch";
        public static final String COLUMN_NAME_WATCH_PHOTO = "watch_photo";
        public static final String COLUMN_NAME_WATCH_PRICE = "watch_price";

        public static final String COLUMN_NAME_USERNAME = "user";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_EMAIL = "email";

        public static final String COLUMN_NAME_PHONE_ORDER = "phone_order";
        public static final String COLUMN_NAME_AIRPODS_ORDER = "airpods_order";
        public static final String COLUMN_NAME_AIRPODS_IMAGE_ORDER = "airpods_order_image";
        public static final String COLUMN_NAME_WATCH_ORDER = "watch_order";
        public static final String COLUMN_NAME_WATCH_IMAGE_ORDER = "watch_order_image";
        public static final String COLUMN_NAME_PRICE_ORDER = "price_order";



    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "
                    + ItemEntry.TABLE_NAME + " ("
                    + ItemEntry._ID + " INTEGER PRIMARY KEY,"
                    + ItemEntry.COLUMN_NAME_PHONE + " TEXT,"
                    + ItemEntry.COLUMN_NAME_PHONE_PHOTO + " INT, "
                    + ItemEntry.COLUMN_NAME_PHONE_PRICE + " INT, "
                    + ItemEntry.COLUMN_NAME_AMOUNT + " INT, "
                    + ItemEntry.COLUMN_NAME_HEADPHONES + " TEXT, "
                    + ItemEntry.COLUMN_NAME_HEADPHONES_PHOTO + " INT, "
                    + ItemEntry.COLUMN_NAME_HEADPHONES_PRICE + " INT, "
                    + ItemEntry.COLUMN_NAME_WATCH + " TEXT, "
                    + ItemEntry.COLUMN_NAME_WATCH_PHOTO + " INT, "
                    + ItemEntry.COLUMN_NAME_WATCH_PRICE + " INT )";
    //Entries for User Table
    /*private static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE "
                    + ItemEntry.TABLE_NAME1 + " ("
                    + ItemEntry._ID + " INTEGER PRIMARY KEY,"
                    + ItemEntry.COLUMN_NAME_EMAIL + " TEXT,"
                    + ItemEntry.COLUMN_NAME_USERNAME + " TEXT,"
                    + ItemEntry.COLUMN_NAME_PASSWORD + " PASSWORD )";*/
    //Entries for Order Table
    /*private static final String SQL_CREATE_ENTRIES_CART =
            "CREATE TABLE "
                    + ItemEntry.TABLE_NAME2 + " ("
                    + ItemEntry._ID + " INTEGER PRIMARY KEY,"
                    + ItemEntry.COLUMN_NAME_EMAIL + " TEXT,"
                    + ItemEntry.COLUMN_NAME_USERNAME + " TEXT,"
                    + ItemEntry.COLUMN_NAME_PASSWORD + " PASSWORD )";*/

    private static final String SQL_CREATE_ENTRIES_ORDERS =
            "CREATE TABLE "
                    + ItemEntry.TABLE_NAME2 + " ("
                    + ItemEntry._ID + " INTEGER PRIMARY KEY,"
                    + ItemEntry.COLUMN_NAME_PHONE_ORDER  + " TEXT,"
                    + ItemEntry.COLUMN_NAME_AIRPODS_ORDER + " TEXT,"
                    + ItemEntry.COLUMN_NAME_WATCH_ORDER + " TEXT,"
                    + ItemEntry.COLUMN_NAME_PRICE_ORDER + " INT)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS "
                    + ItemEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_ORDERS =
            "DROP TABLE IF EXISTS "
                    + ItemEntry.TABLE_NAME2;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        //sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_USER);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_ORDERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_ORDERS);
        onCreate(sqLiteDatabase);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
    @SuppressLint("Range")
    public List readOrder(String columnName){
        SQLiteDatabase db_read = getReadableDatabase();



        String[] projection = {
                BaseColumns._ID,
                ItemEntry.COLUMN_NAME_PHONE_ORDER,
                ItemEntry.COLUMN_NAME_AIRPODS_ORDER,
                ItemEntry.COLUMN_NAME_WATCH_ORDER,
                ItemEntry.COLUMN_NAME_PRICE_ORDER

        };

        String sortOrder =
                ItemEntry._ID + " DESC";

        Cursor cursor = db_read.query(
                ItemEntry.TABLE_NAME2,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        List result = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            String column = cursor.getString(cursor.getColumnIndex(columnName));
            Log.v("CURSOR",column);
            result.add(column);
        }
        /*do {
            for (int i = 0; i < cursor.getColumnCount(); i++) {

                    Log.e("CURSOR", "" + cursor.getString(i));
                }
            }while (cursor.moveToNext());*/


        /*while(cursor.moveToNext()){
            if(columnName == BaseColumns._ID){
                long item = cursor.getLong(
                        cursor.getColumnIndexOrThrow(ItemEntry._ID)
                );
                result.add(item);
            } else if(columnName == ItemEntry.COLUMN_NAME_PHONE_ORDER
                    || columnName == ItemEntry.COLUMN_NAME_AIRPODS_ORDER
                    || columnName == ItemEntry.COLUMN_NAME_WATCH_ORDER
                    || columnName == ItemEntry.COLUMN_NAME_PRICE_ORDER
                    ){
                int item = cursor.getInt(
                        cursor.getColumnIndexOrThrow(columnName)
                );
                result.add(item);
            }
            else{
                String item = cursor.getString(
                        cursor.getColumnIndexOrThrow(columnName)
                );
                result.add(item);
            }
        }
        */cursor.close();
        Log.v("TAG_ORDER","--------------------------------"+result);
        return result;
    }
    public List readData(String columnName){
        SQLiteDatabase db_read = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                ItemEntry.COLUMN_NAME_PHONE,
                ItemEntry.COLUMN_NAME_PHONE_PHOTO,
                ItemEntry.COLUMN_NAME_PHONE_PRICE,
                ItemEntry.COLUMN_NAME_AMOUNT,
                ItemEntry.COLUMN_NAME_HEADPHONES,
                ItemEntry.COLUMN_NAME_HEADPHONES_PHOTO,
                ItemEntry.COLUMN_NAME_HEADPHONES_PRICE,
                ItemEntry.COLUMN_NAME_WATCH,
                ItemEntry.COLUMN_NAME_WATCH_PHOTO,
                ItemEntry.COLUMN_NAME_WATCH_PRICE,
        };

        String sortOrder =
                ItemEntry.COLUMN_NAME_PHONE + " DESC";

        Cursor cursor = db_read.query(
                ItemEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List result = new ArrayList<>();
        while(cursor.moveToNext()){
            if(columnName == BaseColumns._ID){
                long item = cursor.getLong(
                        cursor.getColumnIndexOrThrow(ItemEntry._ID)
                );
                result.add(item);
            } else if(columnName == ItemEntry.COLUMN_NAME_PHONE_PHOTO
                    || columnName == ItemEntry.COLUMN_NAME_PHONE_PRICE
                    || columnName == ItemEntry.COLUMN_NAME_AMOUNT
                    || columnName == ItemEntry.COLUMN_NAME_HEADPHONES_PHOTO
                    || columnName == ItemEntry.COLUMN_NAME_HEADPHONES_PRICE
                    || columnName == ItemEntry.COLUMN_NAME_WATCH_PHOTO
                    || columnName == ItemEntry.COLUMN_NAME_WATCH_PRICE){
                int item = cursor.getInt(
                        cursor.getColumnIndexOrThrow(columnName)
                );
                result.add(item);
            }
            else{
                String item = cursor.getString(
                        cursor.getColumnIndexOrThrow(columnName)
                );
                result.add(item);
            }
        }
        cursor.close();
        Log.v("TAG","--------------------------------"+result);
        return result;
    }
    /*public void checkIfExists(String columnName,String selection,String like){
        SQLiteDatabase db_write = getWritableDatabase();
//        String selection = ItemEntry.COLUMN_NAME_PHONE + " LIKE ?";
//        String[] selectionArgs = {like};
//        db_write.execSQL("SELECT phone " + selection + " " + like);
        db_write.q("SELECT phone FROM products WHERE phone LIKE 'Apple iPhone 12 256GB Black';");
    }*/
    public boolean checkIfExists(String tableName, String dbField, String fieldValue){
        SQLiteDatabase db_write = getWritableDatabase();
        String query = "SELECT * FROM " + tableName + " WHERE " + dbField + " = " + fieldValue+";";
        Cursor cursor = db_write.rawQuery(query,null);
        if(cursor.getCount() <= 0){

            cursor.close();
            Log.v("CHECK","FALSE");
            return false;

        }
        cursor.close();
        Log.v("CHECK","TRUE");
        return true;


    }
    public int deleteData(String like){
        SQLiteDatabase db_write = getWritableDatabase();
        String selection = ItemEntry.COLUMN_NAME_PHONE + " LIKE ?";
        String[] selectionArgs = {like};
        return db_write.delete(ItemEntry.TABLE_NAME,selection,selectionArgs);
    }

    public void dropTable(String table){
        SQLiteDatabase db_write = getWritableDatabase();
        db_write.execSQL("DROP TABLE IF EXISTS "+table);
    }
}







