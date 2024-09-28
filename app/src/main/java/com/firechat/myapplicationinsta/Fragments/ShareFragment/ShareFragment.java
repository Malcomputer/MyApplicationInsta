package com.firechat.myapplicationinsta.Fragments.ShareFragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firechat.myapplicationinsta.Adapters.FragmentAdapter;
import com.firechat.myapplicationinsta.MainActivity;
import com.firechat.myapplicationinsta.R;

public class ShareFragment extends Fragment {

	private FragmentAdapter fragmentAdapter;
	private ViewPager pager;
	private TabLayout tab;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		((MainActivity) getActivity()).getSupportActionBar().hide();
		View share = inflater.inflate(R.layout.fragment_share, container, false);

		fragmentAdapter = new FragmentAdapter(getFragmentManager());
		fragmentAdapter.addFragment(new GalleryFragment());
		fragmentAdapter.addFragment(new CameraFragment());

		pager = share.findViewById(R.id.pager);
		pager.setAdapter(fragmentAdapter);
		pager.setCurrentItem(0);

		tab = share.findViewById(R.id.tabs);
		tab.setupWithViewPager(pager);

		tab.getTabAt(0).setText("Gallery");
		tab.getTabAt(1).setText("Camera");

		return share;
	}
}