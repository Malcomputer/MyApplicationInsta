package com.firechat.myapplicationinsta.Fragments.ProfileFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firechat.myapplicationinsta.Login.LoginActivity;
import com.firechat.myapplicationinsta.MainActivity;
import com.firechat.myapplicationinsta.R;
import com.firechat.myapplicationinsta.Utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

	private static final String TAG = "ProfileFragment";

	private FirebaseAuth mAuth;
	private FirebaseUser mUser;
	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference myRef;
	private Context mContext;
	private String userID;

	private TextView mName, mPhotos, mFollowers, mFollowing, mUsername, mDescription;
	private ImageView mProfilePhoto, mProfileBackground;
	private Button btnPost, btnPhotos, btnProfileEdit;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		((MainActivity) getActivity()).getSupportActionBar().show();
		View profile = inflater.inflate(R.layout.fragment_profile, container, false);
		Widgets(profile);
		init();
		ProfileContentContainer();
		return profile;
	}

	private void Widgets(View profile) {
		mName = profile.findViewById(R.id.profile_name);
		mProfilePhoto = profile.findViewById(R.id.profileimage);
		mProfileBackground = profile.findViewById(R.id.profilebackground);
		mFollowers = profile.findViewById(R.id.followers_value);
		mFollowing = profile.findViewById(R.id.following_value);
		mDescription = profile.findViewById(R.id.profile_bio);
		btnPhotos = profile.findViewById(R.id.btn_photos);
		btnPost = profile.findViewById(R.id.btn_post);
		btnProfileEdit = profile.findViewById(R.id.edit_profile);
		btnProfileEdit.setVisibility(View.VISIBLE);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mAuth = FirebaseAuth.getInstance();
		mUser = mAuth.getCurrentUser();
		mFirebaseDatabase = FirebaseDatabase.getInstance();
		myRef = mFirebaseDatabase.getReference();
		mContext = getActivity();
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().replace(R.id.profilecontent_container,
					new ProfileUserInfoFragment()).commit();
		}
	}

	private void init() {
		userID = mAuth.getUid();
		profileInfo();
		profileImage();
		profileBackground();
		profileBio();
		profileUsernameTitle();
		getFollowersCount();
	}

	private void ProfileContentContainer() {
		btnPhotos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().beginTransaction().replace(R.id.profilecontent_container,
						new ProfileUserPhotosFragment()).commit();
			}
		});
		btnPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().beginTransaction().replace(R.id.profilecontent_container,
						new ProfileUserPostFragment()).commit();
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.profile_menu, menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.logout:
				logout();
				break;
			case R.id.btn_1:
				Snackbar.make(getActivity().findViewById(R.id.profileimage), "hehe", Snackbar.LENGTH_LONG).show();
		}
		return false;
	}

	public void returningUser() {
		mAuth.signOut();
		Intent intent = new Intent(mContext, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		Toast.makeText(mContext, "Logged out", Toast.LENGTH_SHORT).show();
	}

	private void logout() {
		new AlertDialog
				.Builder(getContext())
				.setTitle("Log out")
				.setMessage("Are you sure you want to log out?")
				.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						returningUser();
					}
				})
				.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.show();
	}

	public void profileInfo() {
		myRef.child(mContext.getString(R.string.dbname_users))
				.child(userID)
				.child(mContext.getString(R.string.dbname_name))
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						String name = (String) dataSnapshot.getValue();
						String name2 = mUser.getDisplayName();
						String email = mUser.getEmail();
						String phone = mUser.getPhoneNumber();
						String providerId = mUser.getProviderId();
						String UID = mUser.getUid();
						Log.d(TAG, "onDataChange: 123   user ==> \n" + name + "\n" + name2 +
								"\n" + email + "\n" + phone + "\n" + providerId + "\n" + UID);
						mName.setText(name + " and " + name2);
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {

					}
				});
	}

	private void profileImage() {
		myRef.child(mContext.getString(R.string.dbname_users))
				.child(userID)
				.child(mContext.getString(R.string.dbname_profilephoto))
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						String profilePhoto = (String) dataSnapshot.getValue();
						UniversalImageLoader.setImage("", profilePhoto, mProfilePhoto, null);
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {
					}


				});
	}

	private void profileBackground() {
		myRef.child(mContext.getString(R.string.dbname_users))
				.child(userID)
				.child(mContext.getString(R.string.dbname_profilebackgroundphoto))
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						String backgroundImage = (String) dataSnapshot.getValue();
						UniversalImageLoader.setImage("", backgroundImage, mProfileBackground, null);

					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {

					}
				});
	}

	private void profileBio() {
		myRef.child(mContext.getString(R.string.dbname_users))
				.child(userID)
				.child(mContext.getString(R.string.dbname_bio))
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						String bio = (String) dataSnapshot.getValue();
						mDescription.setText(bio);
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {

					}
				});
	}

	private void profileUsernameTitle() {
		myRef.child(mContext.getString(R.string.dbname_users))
				.child(userID)
				.child(mContext.getString(R.string.dbname_username))
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						String usernametitle = (String) dataSnapshot.getValue();
						((MainActivity) mContext).getSupportActionBar().setTitle("          " + usernametitle);
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {

					}
				});
	}

	private void getFollowersCount() {
		myRef.child(getString(R.string.dbname_following))
				.child(userID)
				.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						String followingCount = String.valueOf(dataSnapshot.getChildrenCount());
						mFollowing.setText(followingCount);
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {

					}
				});
	}
}