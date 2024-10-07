package com.firechat.myapplicationinsta.Fragments.SearchFragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.firechat.myapplicationinsta.Adapters.UserListAdapter;
import com.firechat.myapplicationinsta.Fragments.ProfileFragments.SecondaryProfileFragment;
import com.firechat.myapplicationinsta.MainActivity;
import com.firechat.myapplicationinsta.Models.User;
import com.firechat.myapplicationinsta.R;
import com.firechat.myapplicationinsta.Utils.WrappingListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SearchFragment extends Fragment {
	private static final String TAG = "SearchFragment";

	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference myRef;
	private Context mContext;
	private WrappingListView mListView;
	private List<User> mUserList;
	private UserListAdapter mAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		((MainActivity) mContext).getSupportActionBar().show();
		((MainActivity) mContext).getSupportActionBar().setTitle("Search");
		View search = inflater.inflate(R.layout.fragment_search, container, false);
		mListView = search.findViewById(R.id.user_search);
		return search;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mFirebaseDatabase = FirebaseDatabase.getInstance();
		myRef = mFirebaseDatabase.getReference();
		mContext = getActivity();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.search_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		mUserList = new ArrayList<>();
//		getDatabaseInfo();
		searchText(searchItem);
	}

	private void searchText(MenuItem searchItem) {
		SearchView searchView = new SearchView(((MainActivity) mContext).getSupportActionBar().getThemedContext());
		MenuItemCompat.setActionView(searchItem, searchView);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String s) {
				String text = s.toLowerCase(Locale.getDefault());
				searchForMatchId(text);
				return false;
			}
		});
	}

	private void searchForMatchId(String text) {
		//update the users list view
		myRef.child(getString(R.string.dbname_users))
				.orderByChild(getString(R.string.dbname_username))
				.startAt(text)
				.endAt(text + "\uf8ff")
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                            Log.d(TAG, "onDataChange: searched          test ==>  " + ds.getValue(User.class).toString());
							Log.d(TAG, "onDataChange: searched   user i want ==>  " + ds.child("username").getValue());
//                            Log.d(TAG, "onDataChange: searched result i want ==>  " + ds.child("user_id").getValue());


							mUserList.add(ds.getValue(User.class));
							updateUsersList();
						}
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {
					}
				});
	}

	private void getDatabaseInfo() {
		myRef.child(getString(R.string.dbname_users))
				.orderByChild(getString(R.string.dbname_username))
				.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						for (DataSnapshot ds : dataSnapshot.getChildren()) {
							mUserList.add(ds.getValue(User.class));
							updateUsersList();
						}
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {

					}
				});
	}

	private void updateUsersList() {

		mAdapter = new UserListAdapter(Objects.requireNonNull(getContext()), R.layout.layout_user_listitem, mUserList);

		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				//navigate to Secondary User Profile Fragment

				String getscndUID = mUserList.get(position).getUser_id();
				Bundle bund = new Bundle();
				bund.putString("UID", getscndUID);

				Fragment secusefrag = new SecondaryProfileFragment();
				secusefrag.setArguments(bund);

				getFragmentManager()
						.beginTransaction()
						.replace(R.id.fragment_container, secusefrag)
						.addToBackStack("secusefrag")
						.commit();
			}
		});
	}

}