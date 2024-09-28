package com.firechat.myapplicationinsta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.firechat.myapplicationinsta.Fragments.ChatFragment.ChatFragment;
import com.firechat.myapplicationinsta.Fragments.HomeFragments.HomeFragment;
import com.firechat.myapplicationinsta.Fragments.ProfileFragments.ProfileFragment;
import com.firechat.myapplicationinsta.Fragments.SearchFragment.SearchFragment;
import com.firechat.myapplicationinsta.Fragments.ShareFragment.ShareFragment;
import com.firechat.myapplicationinsta.Login.LoginActivity;
import com.firechat.myapplicationinsta.Utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends AppCompatActivity {

	private FirebaseAuth mAuth;
	private FirebaseUser currentUser;
	private String userID;
	private DatabaseReference db;

	private Boolean chatCondition = true;
	private Context mContext = MainActivity.this;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		db = FirebaseDatabase.getInstance().getReference();
		mAuth = FirebaseAuth.getInstance();
		userID = mAuth.getUid();
		currentUser = mAuth.getCurrentUser();

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setTitle(R.string.frag_title);
//        toolbar.setTitle("hello");

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		ImageLoader.getInstance().init(new UniversalImageLoader(mContext).getConfig());

		BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
		bottomNav.setOnNavigationItemSelectedListener(navListener);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
					new HomeFragment()).commit();
		}
	}


	@Override
	public void onStart() {
		super.onStart();
		// Check if user is signed in (non-null) and update UI accordingly.
		if (currentUser == null) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		} else {
			status(true);
		}
	}

	private void status(boolean isOnline) {
		if (currentUser != null) {
			db.child(getString(R.string.dbname_users))
					.child(userID)
					.child(getString(R.string.availability))
					.setValue(isOnline);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		status(false);
	}

	private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
			new BottomNavigationView.OnNavigationItemSelectedListener() {
				@Override
				public boolean onNavigationItemSelected(@NonNull MenuItem item) {
					Fragment selectedFragment = null;

					switch (item.getItemId()) {
						case R.id.nav_home:
							selectedFragment = new HomeFragment();
							break;
						case R.id.nav_search:
							selectedFragment = new SearchFragment();
							break;
						case R.id.nav_add:
							selectedFragment = new ShareFragment();
							break;
						case R.id.nav_chat:
							if (chatCondition) {
								selectedFragment = new ChatFragment();
							} else {
								Intent intent = new Intent(MainActivity.this, ChatActivity.class);
								startActivity(intent);
							}
							break;
						case R.id.nav_profile:
							selectedFragment = new ProfileFragment();
							break;
					}

					getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

					return true;
				}
			};


	@Override
	public void onBackPressed() {
		new AlertDialog
				.Builder(this)
				.setTitle("Exit App")
				.setMessage("Are you sure you want to leave the app?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}

				})
				.setNegativeButton("No", null)
				.show();
	}
}