package com.nutraweb.jomar.capstone02.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.adapter.StockAddAdapter;
import com.nutraweb.jomar.capstone02.adapter.StockListAdapter;
import com.nutraweb.jomar.capstone02.data.StockContract;
import com.nutraweb.jomar.capstone02.model.StockEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joaosantos on 10/01/19.
 */

public class StockListItensActivity extends AppCompatActivity implements StockListAdapter.MenuAdapterClickHandler{
    private StockListAdapter stockListAdapter;
    private List<StockEntity> stockEntities;

    private GridLayoutManager layoutManager;
    @BindView(R.id.list_stock_toolbar)
    Toolbar toolbar;

    @BindView(R.id.list_stcok_recycler_view)
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_list_stock);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.title_list_stock);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));

        layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        stockListAdapter = new StockListAdapter(this);
        mRecyclerView.setAdapter(stockListAdapter);
        setSupportActionBar(toolbar);

        stockEntities =  getItensInStock();
        stockListAdapter.setList(stockEntities);
    }

    private List<StockEntity> getItensInStock() {
        List<StockEntity> listItens = new ArrayList<StockEntity>();
        StockEntity s =null;
        Cursor itemCursor = getContentResolver().query(
                StockContract.StockEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (itemCursor != null && itemCursor.moveToFirst()) {
    do {
            s  = new StockEntity();
            s.set_id(itemCursor.getInt(StockContract.StockEntry.COLUMN_INDEX_STOCK_PRODUCT_ID));
            s.setProductName(itemCursor.getString(StockContract.StockEntry.COLUMN_INDEX_STOCK_PRODUCT_NAME));
            s.setThumb(itemCursor.getString(StockContract.StockEntry.COLUMN_INDEX_STOCK_THUMB));
            s.setQty(itemCursor.getInt(StockContract.StockEntry.COLUMN_INDEX_STOCK_QTY));
            listItens.add(s);
    } while (itemCursor.moveToNext());

    itemCursor.close();
            return listItens;
        } else {
            return listItens;
        }
    }

    @Override
    public void onClick(StockEntity product) {

    }
}
