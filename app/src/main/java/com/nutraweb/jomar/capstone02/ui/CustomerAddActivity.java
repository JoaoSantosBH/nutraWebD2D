package com.nutraweb.jomar.capstone02.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.data.UserContract;
import com.nutraweb.jomar.capstone02.model.UserEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerAddActivity extends AppCompatActivity {
    private UserEntity user = null;
    private boolean userExist ;

    @BindView(R.id.customer_add_toolbar)
    Toolbar toolbar;
    @BindView(R.id.addCustomerEditTextName)
    TextView name;
    @BindView(R.id.addCustomerEditTextPhone)
    TextView phone;
    @BindView(R.id.addCustomerEditTextEmail)
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);
        user = new UserEntity();
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.title_add_customer);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_customer_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String n = name.getText().toString();
                    String p = phone.getText().toString();
                    String e = email.getText().toString();

                if (!n.equals("") && !p.equals("") && !e.equals("")){
                    user.setName(name.getText().toString());
                    user.setPhoneNumber(Integer.valueOf(phone.getText().toString()));
                    user.setEmail(email.getText().toString());
                    userExist = userExist();
                    if(!userExist ) {
                        addUser();
                        name.setText("");phone.setText("");email.setText("");
                        Snackbar.make(view, R.string.addedUser, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(view, R.string.the_user_already_exist, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        }
                }else {
                    Snackbar.make(view, R.string.user_add_please_fill_all_fields, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
    private void addUser() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                ContentValues values = new ContentValues();

                values.put(UserContract.UserEntry.COLUMN_USER_NAME,
                        user.getName());
                values.put(UserContract.UserEntry.COLUMN_USER_PHONE,
                        user.getPhoneNumber());
                values.put(UserContract.UserEntry.COLUMN_USER_EMAIL,
                        user.getEmail());
                values.put(UserContract.UserEntry.COLUMN_USER_RANK,0);

                getContentResolver().insert(
                        UserContract.UserEntry.CONTENT_URI,
                        values
                );
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private boolean userExist() {

        Cursor itemCursor = getContentResolver().query(
                UserContract.UserEntry.CONTENT_URI,
                new String[]{UserContract.UserEntry.COLUMN_USER_EMAIL},
                UserContract.UserEntry.COLUMN_USER_EMAIL + " = '" + user.getEmail() + "' ;",
                null,
                null);

        if (itemCursor != null && itemCursor.moveToFirst()) {
            itemCursor.close();
            return true;
        } else {
            return false;
        }
    }

}
