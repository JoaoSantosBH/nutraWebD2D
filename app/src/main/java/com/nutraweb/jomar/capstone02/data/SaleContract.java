package com.nutraweb.jomar.capstone02.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.nutraweb.jomar.capstone02.model.ProductEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jomar on 28/04/18.
 */

public class SaleContract {

        public static final class SaleEntry implements BaseColumns {

            public static final String CONTENT_AUTHORITY = "com.nutraweb.jomar.capstone02s";
            public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
            public static final String PATH = "sales";
            public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH  )
                    .build();
            public static final String TABLE_NAME = "sales";
            public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
            public static final String CONTENT_TYPE2 = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

            public static final String COLUMN_SALE_NUMBER = "sale_number";
            public static final String COLUMN_SALE_USER_ID = "user_id";
            public static final String COLUMN_SALE_DATE = "date";
            public static final String COLUMN_SALE_QTY = "qty";
            public static final String COLUMN_SALE_TOTAL = "total";
            //public static final String[] COLUMN_SALE_ITENS = String[]{};

            public static Uri buildSaleUri(Long id){
                return ContentUris.withAppendedId(CONTENT_URI, id);
            }

            public static final String[] MAIN_SALE_PROJECTION = {
                    SaleContract.SaleEntry.COLUMN_SALE_NUMBER,
                    SaleContract.SaleEntry.COLUMN_SALE_USER_ID,
                    SaleContract.SaleEntry.COLUMN_SALE_DATE,
                    SaleContract.SaleEntry.COLUMN_SALE_QTY,
                    SaleContract.SaleEntry.COLUMN_SALE_TOTAL,
                    //SaleEntry.COLUMN_SALE_ITENS[]{}
            };

            public static final int COLUMN_INDEX_SALE_NUMBER = 0;
            public static final int COLUMN_INDEX_SALE_USER_ID = 1;
            public static final int COLUMN_INDEX_SALE_DATE = 2 ;
            public static final int COLUMN_INDEX_SALE_QTY = 3 ;
            public static final int COLUMN_INDEX_SALE_TOTAL = 4 ;

        }
    }


