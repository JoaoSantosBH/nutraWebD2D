package com.nutraweb.jomar.capstone02.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.data.StockContract;
import com.nutraweb.jomar.capstone02.model.ProductEntity;
import com.nutraweb.jomar.capstone02.model.StockEntity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity {

    @BindView(R.id.title_Prod_textView)
    TextView title;
    @BindView(R.id.product_price_textView)
    TextView price;
    @BindView(R.id.id_imageView)
    ImageView thumb;
    @BindView(R.id.total_products_value_textView)
    TextView total;
    @BindView(R.id.quantity_text_view)
    TextView qty;

    private StockEntity stockItem;
    private int mQuantity = 0;
    private int mTotalPrice = 0;
    private int value;
    private ProductEntity product = null;
    private boolean inStocck ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_product_detail);
        setSupportActionBar(toolbar);
        this.setTitle(R.string.title_Add_Item_Stock);

        if (getIntent().hasExtra("myObj")) {
            product = (ProductEntity) getIntent().getParcelableExtra("myObj");
            value = product.getValor();
            title.setText(product.getTitulo());
            price.setText("R$ " + String.valueOf(product.getValor()) + " ,00");
            total.setText(String.valueOf(mTotalPrice));
            qty.setText(String.valueOf(mQuantity));
            Context context = this;
            Picasso.with(context)
                    .load(Uri.parse(product.getUrl()))
                    .into(thumb);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_product_detail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inStocck = inStock();
                if(!inStocck){
                    if (mQuantity > 0) {
                        addItemToStock();
                        Snackbar.make(view, R.string.addedItem, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(view, R.string.addedItemNoQty, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } else {
                    Snackbar.make(view, R.string.the_item_is_on_stock, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }
    private boolean inStock() {

        Cursor itemCursor = getContentResolver().query(
                StockContract.StockEntry.CONTENT_URI,
                new String[]{StockContract.StockEntry.COLUMN_STOCK_PRODUCT_NAME},
                StockContract.StockEntry.COLUMN_STOCK_PRODUCT_NAME + " = '" + product.getTitulo() + "' ;",
                null,
                null);

        if (itemCursor != null && itemCursor.moveToFirst()) {
            itemCursor.close();
            return true;
        } else {
            return false;
        }
    }
    public void increment(View view) {
        mQuantity = mQuantity + 1;
        displayQuantity(mQuantity);
        mTotalPrice = calculatePrice();
        displayCost(mTotalPrice);
    }

    public void decrement(View view) {
        if (mQuantity > 0) {
            mQuantity = mQuantity - 1;
            displayQuantity(mQuantity);
            mTotalPrice = calculatePrice();
            displayCost(mTotalPrice);
        }
    }

    private void displayQuantity(int numberOfProducts) {
        qty.setText(String.valueOf(numberOfProducts));
    }

    private void displayCost(int totalPrice) {
        String convertPrice = NumberFormat.getCurrencyInstance().format(totalPrice);
        total.setText(convertPrice);
    }

    private int calculatePrice() {
        mTotalPrice = mQuantity * value;
        return mTotalPrice;
    }


    private void addItemToStock() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                ContentValues movieValues = new ContentValues();

                movieValues.put(StockContract.StockEntry.COLUMN_STOCK_PRODUCT_NAME,
                        product.getTitulo());
                movieValues.put(StockContract.StockEntry.COLUMN_STOCK_THUMB,
                        product.getUrl());
                movieValues.put(StockContract.StockEntry.COLUMN_STOCK_QTY,
                        mQuantity);

                getContentResolver().insert(
                        StockContract.StockEntry.CONTENT_URI,
                        movieValues
                );
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
