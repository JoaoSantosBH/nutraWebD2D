package com.nutraweb.jomar.capstone02.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jomar on 28/04/18.
 */

public class NutraWebDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "nutra.db";
    private static final int DATABASE_VERSION = 1;


    final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " (" +
            UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UserContract.UserEntry.COLUMN_USER_NAME + " TEXT NOT NULL, " +
            UserContract.UserEntry.COLUMN_USER_EMAIL + " TEXT NOT NULL, " +
            UserContract.UserEntry.COLUMN_USER_PHONE + " INTEGER NOT NULL " +
            ");";

    final String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + ProductContract.ProductEntry.TABLE_NAME + " (" +
            ProductContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ProductContract.ProductEntry.COLUMN_PRODUCT_TITLE + " TEXT NOT NULL, " +
            ProductContract.ProductEntry.COLUMN__PRODUCT_DESCRIPTION + " TEXT NOT NULL, " +
            ProductContract.ProductEntry.COLUMN__PRODUCT_THUMB + " TEXT NOT NULL, " +
            ProductContract.ProductEntry.COLUMN__PRODUCT_PRICE + " INTEGER NOT NULL " +
            ");";

    final String SQL_CREATE_SALE_TABLE = "CREATE TABLE " + SaleContract.SaletEntry.TABLE_NAME + " (" +
            SaleContract.SaletEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SaleContract.SaletEntry.COLUMN_SALE_NUMBER + " INTEGER NOT NULL, " +
            SaleContract.SaletEntry.COLUMN_SALE_USER_ID + " INTEGER NOT NULL, " +
            SaleContract.SaletEntry.COLUMN_SALE_DATE + " TEXT NOT NULL, " +
            SaleContract.SaletEntry.COLUMN_SALE_QTY + " INTEGER NOT NULL, " +
            SaleContract.SaletEntry.COLUMN_SALE_TOTAL + " INTEGER NOT NULL " +
            ");";

    final String SQL_CREATE_STOCK_TABLE = "CREATE TABLE " + StockContract.StockEntry.TABLE_NAME + " (" +
            StockContract.StockEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            StockContract.StockEntry.COLUMN_STOCK_PRODUCT_NAME + " TEXT NOT NULL, " +
            StockContract.StockEntry.COLUMN_STOCK_THUMB + " TEXT NOT NULL, " +
            StockContract.StockEntry.COLUMN_STOCK_QTY + " INTEGER NOT NULL " +

            ");";

    private int total;


    public NutraWebDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
        db.execSQL(SQL_CREATE_SALE_TABLE);
        db.execSQL(SQL_CREATE_STOCK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ProductContract.ProductEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SaleContract.SaletEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + StockContract.StockEntry.TABLE_NAME);
        onCreate(db);
    }
}
