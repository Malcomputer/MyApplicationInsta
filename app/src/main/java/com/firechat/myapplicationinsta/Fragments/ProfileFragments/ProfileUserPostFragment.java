package com.firechat.myapplicationinsta.Fragments.ProfileFragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firechat.myapplicationinsta.R;

public class ProfileUserPostFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View post = inflater.inflate(R.layout.fragment_userpost, container, false);
        return post;

    }
}