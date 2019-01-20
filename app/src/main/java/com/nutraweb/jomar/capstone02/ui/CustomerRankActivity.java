package com.nutraweb.jomar.capstone02.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.adapter.CustomerRankAdapter;
import com.nutraweb.jomar.capstone02.data.RankContract;
import com.nutraweb.jomar.capstone02.data.UserContract;
import com.nutraweb.jomar.capstone02.model.RankEntity;
import com.nutraweb.jomar.capstone02.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joaosantos on 19/01/19.
 */

public class CustomerRankActivity extends AppCompatActivity implements CustomerRankAdapter.MenuAdapterClickHandler {


    List<UserEntity> userEntities ;
    private CustomerRankAdapter rankAdapter;
    private LinearLayoutManager layoutManager;
    @BindView(R.id.rank_list_recycler_view)
    RecyclerView mRecyclerView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_rank);
        ButterKnife.bind(this);


        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        rankAdapter = new CustomerRankAdapter(this);
        mRecyclerView.setAdapter(rankAdapter);

        userEntities = getCustomers();

        rankAdapter.setList(userEntities);
    }

    private List<UserEntity> getCustomers() {
        List<UserEntity> userList = new ArrayList<>();
        UserEntity u = null;
        Cursor itemCursor = getContentResolver().query(
                UserContract.UserEntry.CONTENT_URI,
                null,
                null,
                null,
                UserContract.UserEntry.COLUMN_USER_RANK + " DESC");

        if (itemCursor != null && itemCursor.moveToFirst()) {
            do {
                u = new UserEntity();
                u.set_id(itemCursor.getInt(UserContract.UserEntry.COLUMN_INDEX_USER_ID));
                u.setName(itemCursor.getString(UserContract.UserEntry.COLUMN_INDEX_USER_NAME));
                u.setPhoneNumber(itemCursor.getInt(UserContract.UserEntry.COLUMN_INDEX_USER_PHONE));
                u.setEmail(itemCursor.getString(UserContract.UserEntry.COLUMN_INDEX_USER_EMAIL));
                userList.add(u);
            } while (itemCursor.moveToNext());

        }
        itemCursor.close();
        return userList;
    }

    @Override
    public void onClick(UserEntity u) {

    }
}
