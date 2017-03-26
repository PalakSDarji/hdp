package com.hadippa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.hadippa.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstagramPhotoViewerActivity extends AppCompatActivity {

    private String pic;

    @BindView(R.id.ivTouch)
    PhotoView ivTouch;

    @BindView(R.id.pbLoader)
    ProgressBar pbLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_photo_viewer);
        ButterKnife.bind(this);

        pic = getIntent().getStringExtra("pic");

        Glide.with(this)
                .load(pic)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pbLoader.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(ivTouch);


        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
