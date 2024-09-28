package com.firechat.myapplicationinsta.Fragments.ChatFragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firechat.myapplicationinsta.Adapters.ChatlistAdapter;
import com.firechat.myapplicationinsta.MainActivity;
import com.firechat.myapplicationinsta.Models.Message;
import com.firechat.myapplicationinsta.Models.User;
import com.firechat.myapplicationinsta.R;
import com.firechat.myapplicationinsta.Utils.WrappingListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.firebase.firestore.Query.Direction.ASCENDING;

public class MessageListFragment extends Fragment {

	private WrappingListView mChatlist;
	private EditText mTextbox;
	private ImageButton mBtnsend;
	private String text, userID, uUsername, myUsername, myProfilePhoto;

	private FirebaseAuth mAuth;
	private FirebaseFirestore mFirebaseFirestore;
	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference myRef;
	private DocumentReference myChatValue;

	private List<User> mUserList;
	private List<Message> messages;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFirebaseFirestore = FirebaseFirestore.getInstance();
		mFirebaseFirestore.setFirestoreSettings(new FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build());
		mAuth = FirebaseAuth.getInstance();
		mFirebaseDatabase = FirebaseDatabase.getInstance();
		myRef = mFirebaseDatabase.getReference();
		userID = mAuth.getUid();

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View chatList = inflater.inflate(R.layout.fragment_chatlist, container, false);
		bindViews(chatList);
		String hehe = getArguments().getString("UID1");
		getUser(hehe);
		sendText(hehe);
		readText(hehe);
//		messagesList();
		return chatList;
	}

	private void bindViews(View chatList) {
		mChatlist = chatList.findViewById(R.id.chatlist);
		mTextbox = chatList.findViewById(R.id.textbox);
		mBtnsend = chatList.findViewById(R.id.send);
		myChatValue = mFirebaseFirestore.collection("Messages").document(userID);

	}

	private void getUser(String hehe) {
		mUserList = new ArrayList<>();
		myRef.child(getString(R.string.dbname_users))
				.child(hehe)
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//						mUserList.add(dataSnapshot.getValue(User.class));
						uUsername = dataSnapshot.getValue(User.class).getUsername();
						((MainActivity) getActivity()).getSupportActionBar().setTitle((CharSequence) uUsername);

						myRef.child(getString(R.string.dbname_users))
								.child(userID)
								.addValueEventListener(new ValueEventListener() {
									@Override
									public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
										myUsername = dataSnapshot.getValue(User.class).getUsername();
										myProfilePhoto = dataSnapshot.getValue(User.class).getPhotoURL();
										Map<String, Object> myuid = new HashMap<>();
										myuid.put("myuser_id", userID);
										myuid.put("myusername", myUsername);
										myuid.put("myphoto", myProfilePhoto);
										myChatValue.set(myuid);
									}

									@Override
									public void onCancelled(@NonNull DatabaseError databaseError) {

									}
								});
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {
					}
				});
	}

	private void sendText(final String hehe) {
		mBtnsend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Message chat = new Message();
				chat.setMessage(mTextbox.getText().toString().trim());
				chat.setUser(myUsername);
				chat.setUser_id(userID);

				myChatValue.collection(hehe).add(chat);
				mTextbox.getText().clear();
			}
		});
	}

	private void readText(String hehe) {
		messages = new ArrayList<>();
		myChatValue.collection(hehe).orderBy("timestamp", ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
			@Override
			public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
				for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
					try {
						if (doc.getType() == DocumentChange.Type.ADDED) {
							Message sent_data = doc.getDocument().toObject(Message.class);
							messages.add(sent_data);
							ChatlistAdapter adapter = new ChatlistAdapter(getContext(), messages);
							mChatlist.setAdapter(adapter);
							mChatlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
									Toast.makeText(getContext(), messages.get(position).getMessage(), Toast.LENGTH_SHORT).show();
								}
							});
							adapter.notifyDataSetChanged();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
}