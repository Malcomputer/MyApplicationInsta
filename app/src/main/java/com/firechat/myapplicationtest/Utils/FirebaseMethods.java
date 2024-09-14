package com.firechat.myapplicationtest.Utils;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.firechat.myapplicationtest.Fragments.ProfileFragments.SecondaryProfileFragment;
import com.firechat.myapplicationtest.Login.LoginActivity;
import com.firechat.myapplicationtest.MainActivity;
import com.firechat.myapplicationtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseMethods {

	private static final Boolean CHECK_IF_VERIFIED = false;

	//firebase
	private FirebaseAuth mAuth;
	private FirebaseUser mUser;
	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference myRef;
	private String userID;

	//vars
	private Context mContext;

	public FirebaseMethods(Context context) {
		mAuth = FirebaseAuth.getInstance();
		mUser = mAuth.getCurrentUser();
		mFirebaseDatabase = FirebaseDatabase.getInstance();
		myRef = mFirebaseDatabase.getReference();
		mContext = context;

		if (mUser != null) {
			userID = mUser.getUid();
		}
	}

	public void signinUser(String email, String password) {
		mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				FirebaseUser user = mUser;
				// If sign in fails, display a message to the user. If sign in succeeds
				// the auth state listener will be notified and logic to handle the
				// signed in user can be handled in the listener.
				if (!task.isSuccessful()) {
					Toast.makeText(mContext, R.string.auth_wrongpass, Toast.LENGTH_SHORT).show();
				} else {
					if (CHECK_IF_VERIFIED) {
						if (user.isEmailVerified()) {
							authDone();
						} else {
							Toast.makeText(mContext, "Email is not verified\ncheck your email inbox.", Toast.LENGTH_SHORT).show();
//                            sendVerificationEmail();
							mAuth.signOut();
						}
					} else {
						authDone();
					}
				}


			}
		});
	}

	private void authDone() {
		Intent intent = new Intent(mContext, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}

	public void registerNewEmail(final String email, final String password, final String username, final String name) {
		mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				// If sign in fails, display a message to the user. If sign in succeeds
				// the auth state listener will be notified and logic to handle the
				// signed in user can be handled in the listener.
				if (!task.isSuccessful()) {
					Toast.makeText(mContext, R.string.auth_registrationFailed, Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(mContext, R.string.auth_registrationSuccess, Toast.LENGTH_SHORT).show();
					//send verification email
					if (mUser != null) {
						mUser.sendEmailVerification()
								.addOnCompleteListener(new OnCompleteListener<Void>() {
									@Override
									public void onComplete(@NonNull Task<Void> task) {
										if (task.isSuccessful()) {
											Toast.makeText(mContext, "Verification email sent successfully.", Toast.LENGTH_SHORT).show();

										} else {
											Toast.makeText(mContext, "couldn't send verification email.", Toast.LENGTH_SHORT).show();
										}
									}
								});
					}
					registerToDatabase(email, username, name);
					mAuth.signOut();
					Intent signIn = new Intent(mContext, LoginActivity.class);
					signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					mContext.startActivity(signIn);
					Toast.makeText(mContext, R.string.auth_login, Toast.LENGTH_SHORT).show();
				}

			}

			private void registerToDatabase(String email, String username, String name) {
				mUser.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(name).build());
				userID = mAuth.getUid();
				// Update user info to the database
				myRef.child(mContext.getString(R.string.dbname_users))
						.child(userID)
						.child(mContext.getString(R.string.dbname_name))
						.setValue(name);
				myRef.child(mContext.getString(R.string.dbname_users))
						.child(userID)
						.child(mContext.getString(R.string.dbname_username))
						.setValue(username);
				myRef.child(mContext.getString(R.string.dbname_users))
						.child(userID)
						.child(mContext.getString(R.string.dbname_email))
						.setValue(email);
				myRef.child(mContext.getString(R.string.dbname_users))
						.child(userID)
						.child(mContext.getString(R.string.dbname_profilephoto))
						.setValue("");
				myRef.child(mContext.getString(R.string.dbname_users))
						.child(userID)
						.child(mContext.getString(R.string.dbname_profilebackgroundphoto))
						.setValue("");
				myRef.child(mContext.getString(R.string.dbname_users))
						.child(userID)
						.child(mContext.getString(R.string.dbname_bio))
						.setValue("");
				myRef.child(mContext.getString(R.string.dbname_users))
						.child(userID)
						.child(mContext.getString(R.string.dbname_user_id))
						.setValue(userID);
			}
		});
	}

	public void followUser(String singleUser, String uUserId) {
		myRef.child(mContext.getString(R.string.dbname_following))
				.child(userID)
				.child(singleUser)
				.child(mContext.getString(R.string.dbname_datefollowed))
				.setValue(System.currentTimeMillis());
		myRef.child(mContext.getString(R.string.dbname_following))
				.child(userID)
				.child(singleUser)
				.child(mContext.getString(R.string.dbname_user_id))
				.setValue(uUserId);
//		myRef.child(mContext.getString(R.string.dbname_following))
//				.child(userID)
//				.child()
	}
}
