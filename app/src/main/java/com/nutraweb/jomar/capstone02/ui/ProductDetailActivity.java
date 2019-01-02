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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity {

    @BindView(R.id.title_Prod_textView)
    TextView title;

    @BindView(R.id.id_imageView)
    ImageView thumb;
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
            title.setText(product.getTitulo());
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

}
