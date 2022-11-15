package com.firechat.myapplicationtest.Models;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firechat.myapplicationtest.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetUser {
	private Context mContext;
	public static String name;
	public static String userId;
	public static String username;
	public static String photo;
	public static String backPhoto;
	public static String bio;


	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference myRef;

	public GetUser(Context context, String user) {
		mContext = context;
		mFirebaseDatabase = FirebaseDatabase.getInstance();
		myRef = mFirebaseDatabase.getReference();
		getUsers(user);
	}

	private void getUsers(String singleUser) {
		myRef.child(mContext.getString(R.string.dbname_users))
				.child(singleUser)
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						User ds = dataSnapshot.getValue(User.class);
//						mUserList.add(ds);
						name = ds.getName();
						userId = ds.getUser_id();
						username = String.valueOf(ds.getUsername());
						photo = ds.getPhotoURL();
						backPhoto = ds.getProfilebackimage();
						bio = ds.getBio();
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {
					}
				});

	}


//	public static String name = "malcolm";
}
