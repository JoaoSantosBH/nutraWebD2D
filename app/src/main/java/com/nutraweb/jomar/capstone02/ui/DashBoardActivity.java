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
import com.google.android.gms.ads.MobileAds;

import com.github.ybq.android.spinkit.SpinKitView;
import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.adapter.DashboardAdapter;
import com.nutraweb.jomar.capstone02.sync.NutraWebSyncUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;



public class DashBoardActivity extends AppCompatActivity implements DashboardAdapter.MenuAdapterClickHandler {
    private AdView mAdView;

    private static final String CUSTOMER = "CUSTOMER";
    private static final String SALES = "SALES";
    private static final String STOCK ="STOCK";
    private List<String> lista;
    private LinearLayoutManager layoutManager;
    private DashboardAdapter mAdapter;
    @BindView(R.id.dash_recyclerview)
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
        NutraWebSyncUtils.initialize(this);
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713

// TODO: Add adView to your view hierarchy.
        MobileAds.initialize(this, "ca-app-pub-5253945965989036~1399832495");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    @Override
    public void onClick(String string) {
        switch (string){

            case CUSTOMER:
                Intent customerIntent = new Intent(this, CustomerDashBoardActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(customerIntent,
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                } else {
                    // Swap without transitioni
                    startActivity(customerIntent);
                }
                break;
            case SALES:
                Intent salesDashIntent = new Intent(this, SalesDashBoardActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(salesDashIntent,
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                } else {
                    // Swap without transitioni
                    startActivity(salesDashIntent);
                }                break;
            case STOCK:
                Intent stockIntent = new Intent(this, StockDashBoardActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(stockIntent,
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                } else {
                    // Swap without transitioni
                    startActivity(stockIntent);
                }
                break;
        }

    }
}
