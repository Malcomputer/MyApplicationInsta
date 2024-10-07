package com.firechat.myapplicationinsta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firechat.myapplicationinsta.Models.User;
import com.firechat.myapplicationinsta.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatProfileImageAdapter extends ArrayAdapter<User> {


	private LayoutInflater mInflater;
	private List<User> mChat = null;
	private int layoutResource;
	private ImageLoader mImage;
	private Context mContext;

	public ChatProfileImageAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<User> object) {
		super(context, resource, object);
		mContext = context;
		mImage = ImageLoader.getInstance();
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutResource = resource;
		this.mChat = object;
	}

	private static class ViewHolder {
		CircleImageView profileImage;
	}

	@NonNull
	@Override
	public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


		final ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(layoutResource, parent, false);
			holder = new ViewHolder();

			holder.profileImage = convertView.findViewById(R.id.chat_profile_image);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			mImage.displayImage(getItem(position).getPhotoURL(), holder.profileImage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertView;
	}
}