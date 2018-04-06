package com.nutraweb.jomar.capstone02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG                 = LoginActivity.class.getName().toString();

    private static final int INVALID_NUMBER_MESSAGE = R.string.invalid_phone_number;
    private static final int ERROR_MSG              = R.string.error_message;
    private static final int CONFIRM_MSG            = R.string.snack_login_confirm_message ;
    private static final int CONFIRM                = R.string.snack_login_confirm;
    private static final int POST_MSG               = R.string.snack_confirm_send;
    private static final int KEY_VERIFY_IN_PROGRESS = R.string.key_verify_msg;
    private boolean mVerificationInProgress = false;
    private static final int STATE_INITIALIZED      = 1;
    private static final int STATE_CODE_SENT        = 2;
    private static final int STATE_VERIFY_FAILED    = 3;
    private static final int STATE_VERIFY_SUCCESS   = 4;
    private static final int STATE_SIGNIN_FAILED    = 5;
    private static final int STATE_SIGNIN_SUCCESS   = 6;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private String resultPhoneNumber;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

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
        //setting lclick listener
        mButton.setOnClickListener(this);

        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    phoneNumberProvided.setError(String.valueOf(R.string.invalid_phone_number));
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), R.string.firebase_quota_exced,
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
                updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
        // [END phone_auth_callbac
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        // [START_EXCLUDE]
        if (mVerificationInProgress && validatePhoneNumber()) {

                        startPhoneNumberVerification(resultPhoneNumber);
        }
        // [END_EXCLUDE]
    }
    // [END on_start_check_user]

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(String.valueOf(KEY_VERIFY_IN_PROGRESS), mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(String.valueOf(KEY_VERIFY_IN_PROGRESS));
    }

    //first step - verification
    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS, user);


                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]]
                                Toast.makeText(LoginActivity.this,INVALID_NUMBER_MESSAGE,Toast.LENGTH_SHORT).show();

                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    private void signOut() {
        mAuth.signOut();
        updateUI(STATE_INITIALIZED);
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    @SuppressLint("StringFormatInvalid")
    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button

                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the
                Log.v(TAG,"Verificacao sucesso");
                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                Log.v(TAG,"Verificacao sucesso");
                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in
                Log.v(TAG,"Verificacao sucesso");

                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                Log.v(TAG,"Verificacao sucesso");
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                Log.v(TAG,"Verificacao sucesso");
                Intent i = new Intent(LoginActivity.this,DashBoardActivity.class);
                startActivity(i);
                break;
        }

        if (user == null) {
            // Signed out


//            mStatusText.setText(R.string.signed_out);
        } else {
            // Signed in


            phoneNumberProvided.setText(null);
            countryCodeProvided.setText(null);

        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = resultPhoneNumber;
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberProvided.setError(String.valueOf(R.string.invalid_phone_number));
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.getVerificationButton:
                InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        phoneNumberProvided.getWindowToken(), 0);

                String country = "+" + countryCodeProvided.getText().toString();
                String phone = phoneNumberProvided.getText().toString();
                if(country != null && !country.isEmpty() && phone != null && !phone.isEmpty() ){
                    resultPhoneNumber =  country + phone;
                    if (resultPhoneNumber != null && !resultPhoneNumber.isEmpty() ){//tirar este if de validacao ja foi validado
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, CONFIRM_MSG + " " + resultPhoneNumber, Snackbar.LENGTH_LONG)
                                .setAction(CONFIRM, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view1){
                                        /*Snackbar snackbar1 = Snackbar.make(coordinatorLayout, POST_MSG, Snackbar.LENGTH_LONG);
                                        snackbar1.show();*/
                                        startPhoneNumberVerification(resultPhoneNumber);
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
    }





}
