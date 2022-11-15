package com.firechat.myapplicationtest.Fragments.ProfileFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firechat.myapplicationtest.R;

public class ProfileUserPostFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View post = inflater.inflate(R.layout.fragment_userpost, container, false);
        return post;

    }
}