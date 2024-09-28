package com.firechat.myapplicationinsta.Fragments.ShareFragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firechat.myapplicationinsta.R;
import com.firechat.myapplicationinsta.Utils.Permissions;

public class GalleryFragment extends Fragment {

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Permissions.checkPermissions(getContext(), Permissions.PERMISSIONS[1]);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View gallery = inflater.inflate(R.layout.fragment_gallery, container, false);
		return gallery;
	}
}