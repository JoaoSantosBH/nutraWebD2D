package com.nutraweb.jomar.capstone02.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jomar on 28/04/18.
 */

public class SaleContract {

        public static final class SaletEntry implements BaseColumns {

            public static final String TABLE_NAME = "sales";
            public static final String COLUMN_SALE_NUMBER = "sale_number";
            public static final String COLUMN__SALE_USER_ID = "user_id";
            public static final String COLUMN__SALE_DATE = "date";
            public static final String COLUMN__SALE_QTY = "qty";
            public static final String COLUMN__SALE_TOTAL = "total";


            public static final String[] MAIN_SALE_PROJECTION = {
                    SaleContract.SaletEntry.COLUMN_SALE_NUMBER,
                    SaleContract.SaletEntry.COLUMN__SALE_USER_ID,
                    SaleContract.SaletEntry.COLUMN__SALE_DATE,
                    SaleContract.SaletEntry.COLUMN__SALE_QTY,
                    SaleContract.SaletEntry.COLUMN__SALE_TOTAL
            };

            public static final int COLUMN_INDEX_SALE_NUMBER = 0;
            public static final int COLUMN_INDEX_SALE_USER_ID = 1;
            public static final int COLUMN_INDEX_SALE_DATE = 2 ;
            public static final int COLUMN_INDEX_SALE_QTY = 3 ;
            public static final int COLUMN_INDEX_SALE_TOTAL = 4 ;

        }
    }


