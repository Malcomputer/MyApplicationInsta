package com.firechat.myapplicationtest.Fragments.ShareFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firechat.myapplicationtest.Adapters.FragmentAdapter;
import com.firechat.myapplicationtest.MainActivity;
import com.firechat.myapplicationtest.R;

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