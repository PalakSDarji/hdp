package com.hadippa.twowaygrid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.hadippa.R;
import com.hadippa.activities.ProfileActivity;
import com.hadippa.model.UserProfile;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Palak on 23-03-2017.
 */

public class InstagramPicAdapter extends BaseAdapter {

    private Context context;
    private List<UserProfile.InstagramImagesBean.DataBean> pics;
    private LayoutInflater layoutInflater;
    private RoundedTransformationBuilder builder;

    public InstagramPicAdapter(Context context, List<UserProfile.InstagramImagesBean.DataBean> pics) {
        this.context = context;
        this.pics = pics;
        layoutInflater = LayoutInflater.from(context);

        builder = new RoundedTransformationBuilder().scaleType(ImageView.ScaleType.CENTER_CROP).cornerRadiusDp(10).oval(false);
    }

    @Override
    public int getCount() {
        return pics.size();
    }

    @Override
    public UserProfile.InstagramImagesBean.DataBean getItem(int position) {
        return pics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MyViewHolder myViewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_insta_pic, parent, false);
            myViewHolder = new MyViewHolder();
            myViewHolder.iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
            convertView.setTag(myViewHolder);
        }
        else{
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        Glide.with(context)
                .load(pics.get(position).getImages().getStandard_resolution().getUrl())
                .placeholder(R.drawable.place_holder).error(R.drawable.place_holder)
                .transform(builder.glide().build(context))
                .into(myViewHolder.iv_photo);

        return convertView;
    }

    public class MyViewHolder{
        private ImageView iv_photo;
    }
}
