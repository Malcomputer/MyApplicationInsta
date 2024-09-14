package com.firechat.myapplicationtest.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firechat.myapplicationtest.Models.Message;
import com.firechat.myapplicationtest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ChatlistAdapter extends BaseAdapter {

	private List<Message> txt;
	private final int ME = 0, YOU = 1;
	private LayoutInflater inflater;

	public ChatlistAdapter(Context context, List<Message> message) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.txt = message;
	}

	@Override
	public int getItemViewType(int position) {
		if (txt.get(position).getUser_id().equals(FirebaseAuth.getInstance().getUid())) {
			return ME;
		} else {
			return YOU;
		}
	}


	@Override
	public int getCount() {
		return txt.size();
	}

	@Override
	public Object getItem(int position) {
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	private static class ViewHolder {
		TextView txtname, txttitle;
		ImageView senderIMG;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			if (txt.get(position).getUser_id().equals(FirebaseAuth.getInstance().getUid())) {
				convertView = inflater.inflate(R.layout.me_text, parent, false);
				holder.txtname = convertView.findViewById(R.id.activity_name);
				convertView.setTag(holder);
				holder.txtname.setText(txt.get(position).getMessage());
			} else {
				convertView = inflater.inflate(R.layout.you_text, parent, false);
				holder.txttitle = convertView.findViewById(R.id.name);
				holder.txtname = convertView.findViewById(R.id.activity_name);
				holder.senderIMG = convertView.findViewById(R.id.activity_image);
				convertView.setTag(holder);
				holder.txtname.setText(txt.get(position).getMessage());
				holder.txttitle.setText((CharSequence) txt.get(position).getUser());
				FirebaseDatabase
						.getInstance()
						.getReference()
						.child(String.valueOf(R.string.dbname_users))
						.child(txt.get(position).getUser_id())
						.child(String.valueOf(R.string.dbname_profilephoto))
						.addValueEventListener(new ValueEventListener() {
							@Override
							public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
								ImageLoader.getInstance().displayImage((String) dataSnapshot.getValue(), holder.senderIMG);
							}

							@Override
							public void onCancelled(@NonNull DatabaseError databaseError) {
							}
						});
			}
		}
		return convertView;
	}
}