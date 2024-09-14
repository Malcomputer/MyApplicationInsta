package com.firechat.myapplicationtest.Fragments.ChatFragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.firechat.myapplicationtest.Adapters.ChatProfileImageAdapter;
import com.firechat.myapplicationtest.MainActivity;
import com.firechat.myapplicationtest.Models.User;
import com.firechat.myapplicationtest.R;
import com.firechat.myapplicationtest.Utils.WrappingListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

	private FirebaseAuth mAuth;
	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference myRef;
	private String userID, useID, photoID;

	private WrappingListView mListing;

	private List<User> mChatList;
	private ChatProfileImageAdapter mChatAdapter;


	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		((MainActivity) getActivity()).getSupportActionBar().show();
		((MainActivity) getActivity()).getSupportActionBar().setTitle("Chat");
		View chat = inflater.inflate(R.layout.fragment_chat, container, false);
		bindViews(chat);
		userList();
		return chat;
	}

	private void bindViews(View chat) {
		mListing = chat.findViewById(R.id.recycler_view_all_user_listing);
		mAuth = FirebaseAuth.getInstance();
		mFirebaseDatabase = FirebaseDatabase.getInstance();
		myRef = mFirebaseDatabase.getReference();
		userID = mAuth.getUid();
	}

	private void userList() {
		mChatList = new ArrayList<>();

		myRef.child(getString(R.string.dbname_following))
				.child(userID)
//				.orderByChild(getString(R.string.dbname_iffollowing))
//				.orderByChild("datefollowed")
//				.equalTo(getString(R.string.follow_key))
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						for (DataSnapshot ds : dataSnapshot.getChildren()) {
							mChatList.add(ds.getValue(User.class));
							chatUserList();
						}
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {
					}
				});
	}

	private void chatUserList() {
		mChatAdapter = new ChatProfileImageAdapter(getActivity(), R.layout.layout_chat_listitem, mChatList);
		mListing.setAdapter(mChatAdapter);
		mListing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				useID = mChatList.get(position).getUser_id();
				photoID = mChatList.get(position).getPhotoURL();
				Bundle bund = new Bundle();
				bund.putString("UID1", useID);
				Fragment secusefrag = new MessageListFragment();
				secusefrag.setArguments(bund);
				if (getFragmentManager() != null) {
					getFragmentManager()
							.beginTransaction()
							.replace(R.id.fragment_chatcontainer, secusefrag)
							.commit();
				}
			}
		});
		mChatAdapter.notifyDataSetChanged();
	}
}