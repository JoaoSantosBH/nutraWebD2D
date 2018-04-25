package com.nutraweb.jomar.capstone02.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.adapter.SalesDashBoardAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jomar on 22/04/18.
 */

public class SalesDashBoardActivity extends AppCompatActivity implements SalesDashBoardAdapter.MenuAdapterClickHandler  {
    private static final String NEW_SALE = "New Sale";
    private static final String HISTORY = "Sales History";

    private List<String> lista;
    private LinearLayoutManager layoutManager;
    private SalesDashBoardAdapter mAdapter;

    @BindView(R.id.sales_list_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.error_textView)
    TextView textView;
    @BindView(R.id.spin_kit)
    SpinKitView kitView;

    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_dash);

        ButterKnife.bind(this);

        lista = new ArrayList<>();
        lista.add(NEW_SALE);
        lista.add(HISTORY);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new SalesDashBoardAdapter(this);
        mAdapter.setList(lista);
        mRecyclerView.setAdapter(mAdapter);


    }
    @Override
    public void onClick(String string) {
        switch (string){

            case NEW_SALE:
                Intent salesSell = new Intent(this, SalesSellActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(salesSell,
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                } else {
                    // Swap without transitioni
                    startActivity(salesSell);
                }


                break;
            case HISTORY:
                Toast.makeText(this,HISTORY, Toast.LENGTH_SHORT).show();
                break;

        }

    }

}
