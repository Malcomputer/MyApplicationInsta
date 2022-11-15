package com.firechat.myapplicationtest.Fragments.ProfileFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firechat.myapplicationtest.R;
import com.firechat.myapplicationtest.Adapters.GridImageAdapter;
import com.firechat.myapplicationtest.Utils.WrappingGridView;

import java.util.ArrayList;

public class ProfileUserPhotosFragment extends Fragment {

    private WrappingGridView mGridview;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View photos = inflater.inflate(R.layout.fragment_userphotos, container, false);
        mGridview = photos.findViewById(R.id.gridView);
        tempGridImage();
        return photos;
    }

    private void tempGridImage() {
        ArrayList<String> imgURL = new ArrayList<>();
        imgURL.add("https://images.pexels.com/photos/368893/pexels-photo-368893.jpeg?auto=compress&cs=tinysrgb&h=350");
        imgURL.add("https://images.pexels.com/photos/699466/pexels-photo-699466.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        imgURL.add("https://images.pexels.com/photos/595809/pexels-photo-595809.jpeg?auto=compress&cs=tinysrgb&h=350");
        imgURL.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT6kBYOboHQAty49wIiL_dXf-2aeUuvJYSbmij_YJk_I7nPLUcq");
        imgURL.add("https://images.pexels.com/photos/814499/pexels-photo-814499.jpeg?auto=compress&cs=tinysrgb&h=350");
        imgURL.add("https://static.photocdn.pt/images/articles/2017/04/28/iStock-546424192.jpg");
        imgURL.add("https://llandscapes-ee1.kxcdn.com/wp-content/uploads/2018/03/lake-irene-1679708_1920-e1520033192433.jpg");
        imgURL.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvoHa5nLLyZKAKzjy2Dmx_qm-CwWxpVVxKOYvuP_PJUbwy3RQPSA");
        imgURL.add("http://gardenclub.homedepot.com/wp-content/uploads/2017/11/landscape-SS_131937623-560X400.jpg");
        imgURL.add("https://iso.500px.com/wp-content/uploads/2014/07/big-one.jpg");
        imgURL.add("https://images.pexels.com/photos/368893/pexels-photo-368893.jpeg?auto=compress&cs=tinysrgb&h=350");
        imgURL.add("https://images.pexels.com/photos/699466/pexels-photo-699466.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        imgURL.add("https://images.pexels.com/photos/595809/pexels-photo-595809.jpeg?auto=compress&cs=tinysrgb&h=350");
        imgURL.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT6kBYOboHQAty49wIiL_dXf-2aeUuvJYSbmij_YJk_I7nPLUcq");
        imgURL.add("https://images.pexels.com/photos/814499/pexels-photo-814499.jpeg?auto=compress&cs=tinysrgb&h=350");
        imgURL.add("https://static.photocdn.pt/images/articles/2017/04/28/iStock-546424192.jpg");
        imgURL.add("https://llandscapes-ee1.kxcdn.com/wp-content/uploads/2018/03/lake-irene-1679708_1920-e1520033192433.jpg");
        imgURL.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvoHa5nLLyZKAKzjy2Dmx_qm-CwWxpVVxKOYvuP_PJUbwy3RQPSA");
        imgURL.add("http://gardenclub.homedepot.com/wp-content/uploads/2017/11/landscape-SS_131937623-560X400.jpg");
        imgURL.add("https://iso.500px.com/wp-content/uploads/2014/07/big-one.jpg");
        imgURL.add("https://images.pexels.com/photos/368893/pexels-photo-368893.jpeg?auto=compress&cs=tinysrgb&h=350");
        imgURL.add("https://images.pexels.com/photos/699466/pexels-photo-699466.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        imgURL.add("https://images.pexels.com/photos/595809/pexels-photo-595809.jpeg?auto=compress&cs=tinysrgb&h=350");
        imgURL.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT6kBYOboHQAty49wIiL_dXf-2aeUuvJYSbmij_YJk_I7nPLUcq");
        imgURL.add("https://images.pexels.com/photos/814499/pexels-photo-814499.jpeg?auto=compress&cs=tinysrgb&h=350");
        imgURL.add("https://static.photocdn.pt/images/articles/2017/04/28/iStock-546424192.jpg");
        imgURL.add("https://llandscapes-ee1.kxcdn.com/wp-content/uploads/2018/03/lake-irene-1679708_1920-e1520033192433.jpg");
        imgURL.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvoHa5nLLyZKAKzjy2Dmx_qm-CwWxpVVxKOYvuP_PJUbwy3RQPSA");
        imgURL.add("http://gardenclub.homedepot.com/wp-content/uploads/2017/11/landscape-SS_131937623-560X400.jpg");
        imgURL.add("https://iso.500px.com/wp-content/uploads/2014/07/big-one.jpg");

        loadImages(imgURL);
    }

    private void loadImages(ArrayList<String> imgURL) {
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth / 3;
        mGridview.setColumnWidth(imageWidth);
        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURL);
        mGridview.setAdapter(adapter);
    }
}