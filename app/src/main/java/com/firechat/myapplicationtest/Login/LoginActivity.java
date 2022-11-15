package com.firechat.myapplicationtest.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firechat.myapplicationtest.MainActivity;
import com.firechat.myapplicationtest.R;
import com.firechat.myapplicationtest.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseMethods fireMethods;
    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail, mPassword;
    private String email, password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProgressBar = findViewById(R.id.progressBar);
        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mContext = LoginActivity.this;
        mProgressBar.setVisibility(View.GONE);
        fireMethods = new FirebaseMethods(mContext);
        mAuth = FirebaseAuth.getInstance();

        init();

    }

    private boolean isStringNull(String email, String password) {
        if (email.equals("") || password.equals("")) {
            Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*------------------------------------ Firebase ---------------------------------------------*/

    private void init() {
        /*     If the user is logged in then navigate to HomeActivity and call 'finish()'      */
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void logIn(View view) {
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();

        if (isStringNull(email, password)) {
            mProgressBar.setVisibility(View.VISIBLE);

            fireMethods.signinUser(email, password);
//            FirebaseMethods.signinUser(email,password);
        }
    }

    public void register(View view) {
        Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register);
    }
}