package com.nutraweb.jomar.capstone02.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.model.ProductEntity;
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


    private int mQuantity = 0;
    private int mTotalPrice = 0 ;
    private int value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_product_detail);
        setSupportActionBar(toolbar);
        ProductEntity product =null;
        this.setTitle(R.string.title_Add_Item_Stock);

        if (getIntent().hasExtra("myObj")) {
            product = (ProductEntity) getIntent().getParcelableExtra("myObj");
            value = product.getValor();
            title.setText(product.getTitulo());
            price.setText("R$ " + String.valueOf(product.getValor()) +" ,00" );
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
                Snackbar.make(view, title.getText(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

}
