package com.nutraweb.jomar.capstone02.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nutraweb.jomar.capstone02.data.ProductContract;
import com.nutraweb.jomar.capstone02.model.ProductEntity;
import com.nutraweb.jomar.capstone02.network.NetworkUtils;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaosantos on 14/01/19.
 */

public class NutraWebSyncTask {
    synchronized public static void syncData(Context context) {
         List<ProductEntity> productEntityList;
         final Type productListType = new TypeToken<ArrayList<ProductEntity>>(){}.getType();

        try {

            String jsonResponse = NetworkUtils.getHttpResponse("http://audiolabpp.com.br/img/nutraWeb_info/nutrawebJSON.json");
            String mProductsJson;
            productEntityList = new Gson().fromJson(jsonResponse, productListType);
            ContentValues[]  values = new ContentValues[productEntityList.size()];
            int i=0;
            for (ProductEntity p :productEntityList){

                ContentValues cv = new ContentValues();
                cv.put(ProductContract.ProductEntry.COLUMN_PRODUCT_TITLE,p.getTitulo());
                cv.put(ProductContract.ProductEntry.COLUMN__PRODUCT_DESCRIPTION,p.getDescricao());
                cv.put(ProductContract.ProductEntry.COLUMN__PRODUCT_THUMB,p.getUrl());
                cv.put(ProductContract.ProductEntry.COLUMN__PRODUCT_PRICE,p.getValor());
                values[i] = cv;
                i++;

            }

            if (values != null && jsonResponse.length() != 0) {
                ContentResolver contentResolver = context.getContentResolver();
                contentResolver.delete(
                        ProductContract.ProductEntry.CONTENT_URI,
                        null,
                        null);

                contentResolver.bulkInsert(
                        ProductContract.ProductEntry.CONTENT_URI,
                        values);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
