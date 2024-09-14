package com.firechat.myapplicationtest.Login;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firechat.myapplicationtest.R;
import com.firechat.myapplicationtest.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

	private Context mContext;
	private FirebaseMethods fireMethods;
	private String email, username, password, name;
	private EditText mEmail, mPassword, mUsername, mName;
	private Button btnRegister;
	private ProgressBar mProgressBar;

	//firebase
	private FirebaseAuth mAuth;
	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference myRef;

	private String append = "";


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		mContext = RegisterActivity.this;
		mAuth = FirebaseAuth.getInstance();
		mFirebaseDatabase = FirebaseDatabase.getInstance();
		myRef = mFirebaseDatabase.getReference();
		fireMethods = new FirebaseMethods(mContext);

		initWidgets();
		init();
	}

	private void initWidgets() {

		mEmail = findViewById(R.id.input_email);
		mUsername = findViewById(R.id.input_username);
		btnRegister = findViewById(R.id.btn_register);
		mProgressBar = findViewById(R.id.progressBar);
		mPassword = findViewById(R.id.input_password);
		mName = findViewById(R.id.input_name);
		mContext = RegisterActivity.this;
		mProgressBar.setVisibility(View.GONE);

	}

	private void init() {
		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				email = mEmail.getText().toString().trim();
				username = mUsername.getText().toString().toLowerCase(Locale.getDefault()).trim();
				password = mPassword.getText().toString();
				name = mName.getText().toString().trim();

				if (checkInputs(email, username, password, name)) {
					mProgressBar.setVisibility(View.VISIBLE);
					checkIfUsernameExists(username, email, password, name);
				}
			}
		});
	}

	private boolean checkInputs(String email, String username, String password, String name) {
		if (email.equals("")) {
			mEmail.setError("Email");
		}
		if (username.equals("")) {
			mUsername.setError("Username");
		}
		if (password.equals("")) {
			mPassword.setError("Password");
		}
		if (name.equals("")) {
			mName.setError("Name");
		}

		if (email.equals("") || username.equals("") || password.equals("") || name.equals("")) {
			Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/*------------------------------------ Firebase ---------------------------------------------*/

	private void checkIfUsernameExists(final String username, final String email, final String password, final String name) {

		myRef.child(getString(R.string.dbname_users))
				.orderByChild(getString(R.string.dbname_username))
				.equalTo(username)
				.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						if (found(dataSnapshot)) {
							Toast.makeText(mContext, "Username Already Exist\nCreate New Username", Toast.LENGTH_SHORT).show();
						} else {
							fireMethods.registerNewEmail(email, password, username, name);
						}
					}

					private boolean found(DataSnapshot dataSnapshot) {
						return dataSnapshot.exists();
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {

					}
				});
	}
}