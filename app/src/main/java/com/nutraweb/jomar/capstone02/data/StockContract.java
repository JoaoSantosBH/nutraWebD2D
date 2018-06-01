package com.nutraweb.jomar.capstone02.data;

import android.provider.BaseColumns;

/**
 * Created by jomar on 01/06/18.
 */

public class StockContract {
    public static final class StockEntry implements BaseColumns {

        public static final String TABLE_NAME = "stock";
        public static final String COLUMN_STOCK_PRODUCT_ID = "product_id";
        public static final String COLUMN_STOCK_PRODUCT_NAME = "product_name";
        public static final String COLUMN_STOCK_THUMB = "thumb";
        public static final String COLUMN_STOCK_QTY = "qty";


        public static final String[] MAIN_SALE_PROJECTION = {
                StockContract.StockEntry.COLUMN_STOCK_PRODUCT_ID,
                StockContract.StockEntry.COLUMN_STOCK_PRODUCT_NAME,
                StockContract.StockEntry.COLUMN_STOCK_THUMB,
                StockContract.StockEntry.COLUMN_STOCK_QTY
        };

        public static final int COLUMN_INDEX_STOCK_PRODUCT_ID = 0;
        public static final int COLUMN_INDEX_STOCK_PRODUCT_NAME = 1;
        public static final int COLUMN_INDEX_STOCK_THUMB = 2 ;
        public static final int COLUMN_INDEX_STOCK_QTY = 3 ;

    }
}




