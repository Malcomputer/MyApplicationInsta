package com.firechat.myapplicationtest.Fragments.HomeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firechat.myapplicationtest.MainActivity;
import com.firechat.myapplicationtest.Models.GetUser;
import com.firechat.myapplicationtest.Models.GetUserTest;
import com.firechat.myapplicationtest.R;

public class HomeFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		((MainActivity) getActivity()).getSupportActionBar().show();
		((MainActivity) getActivity()).getSupportActionBar().setTitle("Home");
		View home = inflater.inflate(R.layout.fragment_home, container, false);
		TextView main = home.findViewById(R.id.textview);
//		main.setText(GetUser.names());
		return home;
	}
}