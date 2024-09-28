package com.firechat.myapplicationinsta.Fragments.ProfileFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firechat.myapplicationinsta.MainActivity;
import com.firechat.myapplicationinsta.Models.User;
import com.firechat.myapplicationinsta.R;
import com.firechat.myapplicationinsta.Utils.FirebaseMethods;
import com.firechat.myapplicationinsta.Utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SecondaryProfileFragment extends Fragment {

	private FirebaseAuth mAuth;
	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference myRef;
	private FirebaseMethods firebaseMethods;

	private List<User> mUserList;
	private String userID;

	private String uName, uUsername, uUserId, uPhoto, uBackPhoto, uBio;
	private TextView mName, mPhotos, mFollowers, mFollowing, mUsername, mDescription;
	private ImageView mProfilePhoto, mProfileBackground;
	private Button btnPost, btnGridPhotos, btnFollow;


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAuth = FirebaseAuth.getInstance();
		mFirebaseDatabase = FirebaseDatabase.getInstance();
		myRef = mFirebaseDatabase.getReference();
		userID = mAuth.getUid();
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().replace(R.id.profilecontent_container,
					new ProfileUserInfoFragment()).commit();
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View secondaryUser = inflater.inflate(R.layout.fragment_profile, container, false);
		Widgets(secondaryUser);
		String hehe = getArguments().getString("UID");
		getUser(hehe);
		ProfileContentContainer();
		return secondaryUser;
	}

	private void Widgets(View secondaryUser) {
		firebaseMethods = new FirebaseMethods(getContext());
		btnGridPhotos = secondaryUser.findViewById(R.id.btn_photos);
		btnPost = secondaryUser.findViewById(R.id.btn_post);
		btnFollow = secondaryUser.findViewById(R.id.follow_button);
		mFollowers = secondaryUser.findViewById(R.id.followers_value);
		mFollowing = secondaryUser.findViewById(R.id.following_value);
		mName = secondaryUser.findViewById(R.id.profile_name);
		mProfilePhoto = secondaryUser.findViewById(R.id.profileimage);
		mProfileBackground = secondaryUser.findViewById(R.id.profilebackground);
		mDescription = secondaryUser.findViewById(R.id.profile_bio);
		btnFollow.setVisibility(View.VISIBLE);
	}

	private void getUser(final String singleUser) {
		mUserList = new ArrayList<>();
		myRef.child(getString(R.string.dbname_users))
				.child(singleUser)
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						User ds = dataSnapshot.getValue(User.class);
						mUserList.add(ds);
						uName = ds.getName();
						uUserId = ds.getUser_id();
						uUsername = String.valueOf(ds.getUsername());
						uPhoto = ds.getPhotoURL();
						uBackPhoto = ds.getProfilebackimage();
						uBio = ds.getBio();
						UserDetail(singleUser, uName, uUsername, uPhoto, uBackPhoto, uBio);
						if (singleUser.equals(userID)) {
							getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();
						}
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {
					}
				});
	}

	private void ProfileContentContainer() {
		btnGridPhotos.setOnClickListener(new View.OnClickListener() {
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

	private void UserDetail(String singleUser, String uName, String uUsername, String uPhoto, String uBackPhoto, String uBio) {
		userInfo(uUsername, uName, uPhoto, uBackPhoto, uBio);
		UserSettings(singleUser);
	}

	private void userInfo(String uUsername, String uName, String uPhoto, String uBackPhoto, String uBio) {
		((MainActivity) getActivity()).getSupportActionBar().setTitle("          " + uUsername);
		mName.setText(uName);
		mDescription.setText(uBio);
		UniversalImageLoader.setImage("", uPhoto, mProfilePhoto, null);
		UniversalImageLoader.setImage("", uBackPhoto, mProfileBackground, null);


	}

	private void UserSettings(final String singleUser) {
		userFollow(singleUser);
		userUnfollow(singleUser);
		buttonLooks(singleUser);
		userFollowingCount(singleUser);
	}

	private void buttonLooks(String singleUser) {
		myRef.child(getString(R.string.dbname_following))
				.child(userID)
				.child(singleUser)
				.child(getString(R.string.dbname_iffollowing))
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
						String tru = (String) dataSnapshot.getValue();
						try {
							if (tru.equals(getString(R.string.follow_key))) {
								btnFollow.setBackgroundColor(getResources().getColor(R.color.dark_grey));
								btnFollow.setText(getString(R.string.following));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {

					}
				});
	}

	private void userUnfollow(final String singleUser) {
		myRef.child(getString(R.string.dbname_following))
				.child(userID)
				.child(singleUser)
				.child(getString(R.string.dbname_iffollowing))
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
						String tru = (String) dataSnapshot.getValue();
						try {
							if (tru.equals(getString(R.string.follow_key))) {
								btnFollow.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										myRef.child(getString(R.string.dbname_following))
												.child(userID)
												.child(singleUser)
												.removeValue();
										getFragmentManager().beginTransaction()
												.detach(SecondaryProfileFragment.this)
												.attach(SecondaryProfileFragment.this)
												.commit();
									}
								});
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {

					}
				});

	}

	private void userFollow(final String singleUser) {
		btnFollow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				firebaseMethods.followUser(singleUser, uUserId);
			}
		});
//		setupDialog(singleUser);
	}

	private void setupDialog(String singleUser) {
		myRef.child(getString(R.string.dbname_dialog))
				.child(userID)
				.child(singleUser)
				.child(getString(R.string.followed_userID))
				.setValue(singleUser);
//		Then Invert
		myRef.child(getString(R.string.dbname_dialog))
				.child(singleUser)
				.child(userID)
				.child(getString(R.string.followed_userID))
				.setValue(userID);
	}

	private void userFollowingCount(String singleUser) {
		myRef.child(getString(R.string.dbname_following))
				.child(singleUser)
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