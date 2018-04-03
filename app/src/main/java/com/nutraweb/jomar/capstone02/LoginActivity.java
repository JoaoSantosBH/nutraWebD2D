package com.nutraweb.jomar.capstone02;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.StringJoiner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ERROR_MSG = "Please Digit Phone Number";
    private static final String CONFIRM_MSG = "The number is Correct?" ;
    private static final String CONFIRM = "Confirm?";
    private static final String POST_MSG = "Your Number was send!";

    @BindView(R.id.countryCode)
    EditText countryCodeProvided;

    @BindView(R.id.phoneNumber)
    EditText phoneNumberProvided;

    @BindView(R.id.getVerificationButton)
    Button mButton;

    @BindView(R.id.activity_main)
    LinearLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final String result;
                String country = countryCodeProvided.getText().toString();
                String phone = phoneNumberProvided.getText().toString();
                if(country != null && !country.isEmpty() && phone != null && !phone.isEmpty() ){
                     result =  country + phone;
                    if (result != null && !result.isEmpty() ){//tirar este if de validacao ja foi validado
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, CONFIRM_MSG + " " + result, Snackbar.LENGTH_LONG)
                                .setAction(CONFIRM, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view1){
                                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, POST_MSG, Snackbar.LENGTH_LONG);
                                        snackbar1.show();
                                        getVerificationSMSCode(result);
                                    }
                                });
                        snackbar.show();
                    }
                }else{

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout,ERROR_MSG,Snackbar.LENGTH_LONG);
                    snackbar.show();
                }




            }
        });


    }




    private void getVerificationSMSCode (String phoneNumber){
        Toast.makeText(this,"Chamando getVerification" + " : " + phoneNumber, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {

    }
}
