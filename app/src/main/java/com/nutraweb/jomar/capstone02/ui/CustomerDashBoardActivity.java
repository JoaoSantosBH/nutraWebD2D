package com.nutraweb.jomar.capstone02.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.adapter.CustomerDashBoardAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerDashBoardActivity extends AppCompatActivity implements CustomerDashBoardAdapter.MenuAdapterClickHandler{


    private static final String ADD_CUSTOMER = "ADD CUSTOMER";
    private static final String LIST_RANK = "RANK LIST";
    private List<String> lista;
    private LinearLayoutManager layoutManager;
    private CustomerDashBoardAdapter mAdapter;


    @BindView(R.id.customer_list_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.error_textView)
    TextView textView;
    @BindView(R.id.spin_kit)
    SpinKitView kitView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dash_board);
        ButterKnife.bind(this);


        lista = new ArrayList<>();
        lista.add(ADD_CUSTOMER);
        lista.add(LIST_RANK);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CustomerDashBoardAdapter(this);
        mAdapter.setList(lista);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onClick(String string) {
        switch (string){

            case ADD_CUSTOMER:
                Intent i = new Intent(this, CustomerAddActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(i,
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                } else {
                    // Swap without transitioni
                    startActivity(i);
                }
                break;
            case LIST_RANK:
                Intent j = new Intent(this, CustomerRankActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(j,
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                } else {
                    // Swap without transitioni
                    startActivity(j);
                }

                break;


        }
    }
}
