package com.firechat.myapplicationtest.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firechat.myapplicationtest.Models.User;
import com.firechat.myapplicationtest.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends ArrayAdapter<User> {


	private LayoutInflater mInflater;
	private List<User> mUsers = null;
	private int layoutResource;
	private ImageLoader mImage;
	private Context mContext;

	public UserListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<User> objects) {
		super(context, resource, objects);
		mContext = context;
		mImage = ImageLoader.getInstance();
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutResource = resource;
		this.mUsers = objects;
	}

	private static class ViewHolder {
		TextView username, name;
		ImageView onlineIndicator;
		CircleImageView profileImage;
	}

	@NonNull
	@Override
	public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


		final ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(layoutResource, parent, false);
			holder = new ViewHolder();

			holder.username = convertView.findViewById(R.id.username);
			holder.name = convertView.findViewById(R.id.name);
			holder.profileImage = convertView.findViewById(R.id.profile_image);
			holder.onlineIndicator = convertView.findViewById(R.id.onlineIndicator);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}


		holder.username.setText(getItem(position).getUsername());
		holder.name.setText(getItem(position).getName());
		try {
			if (getItem(position).isOnline()) {
				holder.onlineIndicator.setBackgroundResource(R.drawable.shape_bubble_online);
			} else {
				holder.onlineIndicator.setBackgroundResource(R.drawable.shape_bubble_offline);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mImage.displayImage(getItem(position).getPhotoURL(), holder.profileImage);
		notifyDataSetChanged();

		return convertView;
	}
}