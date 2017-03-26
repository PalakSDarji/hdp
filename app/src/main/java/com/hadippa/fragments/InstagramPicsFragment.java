package com.hadippa.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hadippa.R;
import com.hadippa.activities.InstagramPhotoViewerActivity;
import com.hadippa.model.UserProfile;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Palak on 26-03-2017.
 */

public class InstagramPicsFragment extends Fragment {

    private int[] imageviewIds = {R.id.iv_photo_1,R.id.iv_photo_2,R.id.iv_photo_3,R.id.iv_photo_4,R.id.iv_photo_5,R.id.iv_photo_6};
    private List<UserProfile.InstagramImagesBean.DataBean> pics;
    private int position;

    //newInstance constructor for creating fragment with arguments
    public static InstagramPicsFragment newInstance(int position, ArrayList<UserProfile.InstagramImagesBean.DataBean> pics) {

        InstagramPicsFragment fragmentFirst = new InstagramPicsFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putParcelableArrayList("data", pics);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    public InstagramPicsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position", 0);
        pics = getArguments().getParcelableArrayList("data");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insta_grid, container, false);

        RoundedTransformationBuilder builder = new RoundedTransformationBuilder().scaleType(ImageView.ScaleType.CENTER_CROP).cornerRadiusDp(10).oval(false);

        for(int i=0;i<pics.size();i++){
            ImageView imageView = (ImageView) view.findViewById(imageviewIds[i]);
            imageView.setVisibility(View.VISIBLE);

            Glide.with(getActivity())
                    .load(pics.get(i).getImages().getStandard_resolution().getUrl())
                    .placeholder(R.drawable.place_holder).error(R.drawable.place_holder)
                    .transform(builder.glide().build(getActivity()))
                    .into(imageView);

            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), InstagramPhotoViewerActivity.class);
                    intent.putExtra("pic",pics.get(finalI).getImages().getStandard_resolution().getUrl());
                    startActivity(intent);
                }
            });
        }

        return view;
    }
}
