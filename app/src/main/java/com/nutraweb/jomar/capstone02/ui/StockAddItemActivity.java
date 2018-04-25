package com.nutraweb.jomar.capstone02.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.adapter.StockAddAdapter;
import com.nutraweb.jomar.capstone02.model.ProductEntity;
import com.nutraweb.jomar.capstone02.network.NetworkUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockAddItemActivity extends AppCompatActivity implements StockAddAdapter.MenuAdapterClickHandler, LoaderManager.LoaderCallbacks<String>  {

    private static final String URL = "http://audiolabpp.com.br/img/nutraWeb_info/nutrawebJSON.json";
    private static final int PRODUCT_LOADER = 2;
    private List<ProductEntity> productEntityList;
    private final Type productListType = new TypeToken<ArrayList<ProductEntity>>(){}.getType();
    private StockAddAdapter stockAddAdapter;
    private ProductListener productClickListener;

    public interface ProductListener {
        void onProductClicked(ProductEntity productEntity);
        void showErrorSnackBar();
    }


    private GridLayoutManager layoutManager;
    @BindView(R.id.add_stock_toolbar)
    Toolbar toolbar;

    @BindView(R.id.stock_add_spin_kit)
    SpinKitView spinKitView;


    @BindView(R.id.add_stcok_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        ButterKnife.bind(this);
        toolbar.setTitle(R.string.title_add_stock);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
        setSupportActionBar(toolbar);

        LoaderManager loaderManager = getSupportLoaderManager();
        if (savedInstanceState == null) {
            Bundle productBundle = new Bundle();
            productBundle.putString(URL, URL);
            Loader<String> stringLoader = loaderManager.getLoader(PRODUCT_LOADER);

            if (stringLoader == null)
                loaderManager.initLoader(PRODUCT_LOADER, productBundle, this);
            else
                loaderManager.restartLoader(PRODUCT_LOADER, productBundle, this);

        } else {
            loaderManager.initLoader(PRODUCT_LOADER, null, this);
        }

        layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        stockAddAdapter = new StockAddAdapter(this);
        mRecyclerView.setAdapter(stockAddAdapter);

    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(getBaseContext()) {
            String mProductsJson;

            @Override
            protected void onStartLoading() {
                spinKitView.setVisibility(View.VISIBLE);

                if (mProductsJson != null)
                    deliverResult(mProductsJson);
                else {
                    spinKitView.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {
                spinKitView.setVisibility(View.VISIBLE);
                String productsUrlString = args.getString(URL);
                return NetworkUtils.getHttpResponse(productsUrlString);
            }

            @Override
            public void deliverResult(String data) {
                mProductsJson = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        spinKitView.setVisibility(View.INVISIBLE);
        if (data != null) {
            productEntityList = new Gson().fromJson(data, productListType);
            stockAddAdapter.setList(productEntityList);
        } else {
            spinKitView.setVisibility(View.VISIBLE);
            Toast.makeText(this,R.string.error_loading_data,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onClick(ProductEntity product) {
        //productClickListener.onProductClicked(product);
        Toast.makeText(this,R.string.error_loading_data,Toast.LENGTH_SHORT).show();


    }
}
