package com.nutraweb.jomar.capstone02.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jomar on 28/04/18.
 */

public class ProductContract {




    public static final class ProductEntry implements BaseColumns {

        public static final String CONTENT_AUTHORITY = "com.nutraweb.jomar.capstone02b";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_PRODUCTS = "products";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PRODUCTS)
                .build();

        public static final String TABLE_NAME = "products";
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
        public static final String COLUMN_PRODUCT_PRODUCTID = "productid";
        public static final String COLUMN_PRODUCT_TITLE = "titulo";
        public static final String COLUMN__PRODUCT_DESCRIPTION = "descricao";
        public static final String COLUMN__PRODUCT_THUMB = "url";
        public static final String COLUMN__PRODUCT_PRICE = "valor";



        public static Uri buildUsersUri(Long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] MAIN_PRODUCT_PROJECTION = {
                ProductEntry.COLUMN_PRODUCT_PRODUCTID,
                ProductEntry.COLUMN_PRODUCT_TITLE,
                ProductEntry.COLUMN__PRODUCT_DESCRIPTION,
                ProductEntry.COLUMN__PRODUCT_THUMB,
                ProductEntry.COLUMN__PRODUCT_PRICE
        };

        public static final int COLUMN_INDEX_PRODUCT_PRODUCTID =0 ;
        public static final int COLUMN_INDEX_PRODUCT_TITLE = 1;
        public static final int COLUMN_INDEX_PRODUCT_DESCRIPTION = 2;
        public static final int COLUMN_INDEX_PRODUCT_THUMB = 3 ;
        public static final int COLUMN_INDEX_PRODUCT_PRICE = 4 ;

    }
}

