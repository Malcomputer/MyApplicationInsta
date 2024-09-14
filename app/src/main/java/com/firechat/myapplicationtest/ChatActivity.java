package com.firechat.myapplicationtest;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firechat.myapplicationtest.Models.Dialog;
import com.firechat.myapplicationtest.Utils.Libraries.LibUtils.DateFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity implements DateFormatter.Formatter {

//	private DialogsList dialogsList;
	private ArrayList<Dialog> mChatList;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
//		dialogsList = findViewById(R.id.dialogsList);
		getChat();
		initAdapter();
	}

	private void getChat() {
		mChatList = new ArrayList<>();
		FirebaseDatabase.getInstance().getReference()
				.child(getString(R.string.dbname_dialog))
				.child(FirebaseAuth.getInstance().getUid())
				.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						for (DataSnapshot ds : dataSnapshot.getChildren()) {
							mChatList.add(ds.getValue(Dialog.class));
						}
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {

					}
				});

	}

	private void initAdapter() {
//		DialogsListAdapter dialogsAdapter = new DialogsListAdapter(R.layout.item_dialog);
//		dialogsAdapter.setItems(mChatList);

//		dialogsAdapter.setOnDialogClickListener(this);
//		dialogsAdapter.setOnDialogLongClickListener(this);
//		dialogsAdapter.setDatesFormatter(this);

//		dialogsList.setAdapter(dialogsAdapter);
	}


	@Override
	public String format(Date date) {
		if (DateFormatter.isToday(date)) {
			return DateFormatter.format(date, DateFormatter.Template.TIME);
		} else if (DateFormatter.isYesterday(date)) {
			return getString(R.string.date_header_yesterday);
		} else if (DateFormatter.isCurrentYear(date)) {
			return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH);
		} else {
			return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
		}
	}
}
