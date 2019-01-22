package com.nutraweb.jomar.capstone02.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.adapter.CustomerRankAdapter;
import com.nutraweb.jomar.capstone02.adapter.SaleHistoryAdapter;
import com.nutraweb.jomar.capstone02.data.SaleContract;
import com.nutraweb.jomar.capstone02.model.SaleEntity;
import com.nutraweb.jomar.capstone02.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalesHistoryActivity extends AppCompatActivity {

    private List<SaleEntity> sales;
    List<UserEntity> userEntities ;
    private SaleHistoryAdapter adapter;
    private LinearLayoutManager layoutManager;
    @BindView(R.id.)
    RecyclerView mRecyclerView;
    @BindView(R.id.)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_history);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.title_sales_hist);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
        setSupportActionBar(toolbar);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        adapter = new CustomerRankAdapter(this);
        mRecyclerView.setAdapter(adapter);

        sales = getSales();
        adapter.setList(sales);


    }


    private List<SaleEntity> getSales() {
        SaleEntity s ;
        List<SaleEntity> list = new ArrayList<>();
        Cursor itemCursor = getContentResolver().query(
                SaleContract.SaleEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (itemCursor != null && itemCursor.moveToFirst()) {
            do {
                s = new SaleEntity();
                s.setNumberSale(itemCursor.getInt(SaleContract.SaleEntry.COLUMN_INDEX_SALE_NUMBER));
                s.setQty(itemCursor.getInt(SaleContract.SaleEntry.COLUMN_INDEX_SALE_QTY));
                s.setTotal(itemCursor.getInt(SaleContract.SaleEntry.COLUMN_INDEX_SALE_TOTAL));
                s.setUserId(itemCursor.getInt(SaleContract.SaleEntry.COLUMN_INDEX_SALE_USER_ID));
                s.setDate(itemCursor.getString(SaleContract.SaleEntry.COLUMN_INDEX_SALE_DATE));
                list.add(s);

            } while (itemCursor.moveToNext());

        }
        itemCursor.close();
        return list;

    }


}
