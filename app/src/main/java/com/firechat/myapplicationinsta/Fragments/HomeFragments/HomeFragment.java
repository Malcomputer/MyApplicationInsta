package com.firechat.myapplicationinsta.Fragments.HomeFragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firechat.myapplicationinsta.MainActivity;
import com.firechat.myapplicationinsta.Models.GetUser;
import com.firechat.myapplicationinsta.Models.GetUserTest;
import com.firechat.myapplicationinsta.R;

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