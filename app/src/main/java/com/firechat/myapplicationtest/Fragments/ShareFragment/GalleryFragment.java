package com.firechat.myapplicationtest.Fragments.ShareFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firechat.myapplicationtest.R;
import com.firechat.myapplicationtest.Utils.Permissions;

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