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
import com.nutraweb.jomar.capstone02.adapter.StockDashBoardAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StockDashBoardActivity extends AppCompatActivity implements StockDashBoardAdapter.MenuAdapterClickHandler {


    private static final String ADD_ITEM = "Add Item on Stock";
    private static final String LIST_STOCK = "My Stock";

    private List<String> lista;
    private LinearLayoutManager layoutManager;
    private StockDashBoardAdapter mAdapter;

    @BindView(R.id.stock_list_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.error_textView)
    TextView textView;
    @BindView(R.id.spin_kit)
    SpinKitView kitView;

    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_dash);

        ButterKnife.bind(this);

        lista = new ArrayList<>();
        lista.add(ADD_ITEM);
        lista.add(LIST_STOCK);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new StockDashBoardAdapter(this);
        mAdapter.setList(lista);
        mRecyclerView.setAdapter(mAdapter);


    }
    @Override
    public void onClick(String string) {
        switch (string){

            case ADD_ITEM:
                Intent i = new Intent(this, StockAddItemActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(i,
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                } else {
                    // Swap without transitioni
                    startActivity(i);
                }


                break;
            case LIST_STOCK:
                Toast.makeText(this,LIST_STOCK, Toast.LENGTH_SHORT).show();
                break;

        }

    }

}
