package com.nutraweb.jomar.capstone02;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar    ;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.nutraweb.jomar.capstone02.adapter.DashboardAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DashBoardActivity extends AppCompatActivity implements DashboardAdapter.MenuAdapterClickHandler {

    private static final String CUSTOMER = "Customer";
    private static final String SALES = "Sales";
    private static final String STOCK ="Stock";
    private List<String> lista;
    private LinearLayoutManager layoutManager;
    private DashboardAdapter mAdapter;
    @BindView(R.id.dash_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.error_textView)
    TextView textView;
    @BindView(R.id.spin_kit)
    SpinKitView kitView;

    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        lista = new ArrayList<>();
        lista.add(CUSTOMER);
        lista.add(SALES);
        lista.add(STOCK);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DashboardAdapter(this);
        mAdapter.setList(lista);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onClick(String string) {
        Toast.makeText(this,"Clickando", Toast.LENGTH_SHORT).show();
    }
}
