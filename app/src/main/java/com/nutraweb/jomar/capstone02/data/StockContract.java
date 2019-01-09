package com.nutraweb.jomar.capstone02.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jomar on 01/06/18.
 */

public class StockContract {

    public static final class StockEntry implements BaseColumns {
        public static final String CONTENT_AUTHORITY = "com.nutraweb.jomar.capstone02";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_STOCK = "stock";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_STOCK  )
                .build();

        public static final String TABLE_NAME = "stock";
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STOCK;

    public static Uri buildStockUri(Long id){
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

        public static final String COLUMN_STOCK_PRODUCT_NAME = "product_name";
        public static final String COLUMN_STOCK_THUMB = "thumb";
        public static final String COLUMN_STOCK_QTY = "qty";


        public static final String[] MAIN_SALE_PROJECTION = {
                StockContract.StockEntry.COLUMN_STOCK_PRODUCT_NAME,
                StockContract.StockEntry.COLUMN_STOCK_THUMB,
                StockContract.StockEntry.COLUMN_STOCK_QTY
        };

        public static final int COLUMN_INDEX_STOCK_PRODUCT_NAME = 0;
        public static final int COLUMN_INDEX_STOCK_THUMB = 1 ;
        public static final int COLUMN_INDEX_STOCK_QTY = 2 ;

    }
}




