package com.firechat.myapplicationtest.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

	private final List<Fragment> swipelist = new ArrayList<>();

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getCount() {
		return swipelist.size();
	}

	@Override
	public Fragment getItem(int position) {
		return swipelist.get(position);
	}

	public void addFragment(Fragment fragment) {
		swipelist.add(fragment);
	}

}