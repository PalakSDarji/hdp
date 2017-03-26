package com.hadippa.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.hadippa.R;
import com.hadippa.WrapContentViewPager;
import com.hadippa.fragments.InstagramPicsFragment;
import com.hadippa.model.UserProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Palak on 26-03-2017.
 */

public class InstaGridViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = InstaGridViewPagerAdapter.class.getSimpleName();
    private int PIC_IN_ONE_SCREEN;
    private ArrayList<UserProfile.InstagramImagesBean.DataBean> pics;
    private HashMap<Integer,ArrayList<UserProfile.InstagramImagesBean.DataBean>> instas = new HashMap<>();

    public InstaGridViewPagerAdapter(Context context,FragmentManager fm, ArrayList<UserProfile.InstagramImagesBean.DataBean> pics) {
        super(fm);
        this.pics = pics;
        getPicsForFragment();
        PIC_IN_ONE_SCREEN = context.getResources().getInteger(R.integer.insta_grid_column) * 2 ;
    }

    public void setData(ArrayList<UserProfile.InstagramImagesBean.DataBean> pics){
        this.pics = pics;
        getPicsForFragment();
        notifyDataSetChanged();
    }

    private int getFragmentSize(){
        float f = (float)pics.size()/(float)PIC_IN_ONE_SCREEN;
        Log.v(TAG,"Pics 33: " + (int)Math.ceil(f));
        return (int)Math.ceil(f);
    }

    private void getPicsForFragment(){

        if(pics != null && !pics.isEmpty()){

            Log.v(TAG,"Pics 11: " + pics.size());
            /*pics.remove(5);
            pics.remove(6);
            pics.remove(7);
            pics.remove(8);*/
            Log.v(TAG,"Pics 22: " + pics.size());
            int counter = -1;
            ArrayList<UserProfile.InstagramImagesBean.DataBean> instaPics = null;

            for(int i=0;i<pics.size();i++){

                if( i % PIC_IN_ONE_SCREEN == 0){

                    counter = counter + 1;
                    instaPics = new ArrayList<>();
                    instaPics.add(pics.get(i));
                    instas.put(counter,instaPics);
                }
                else{
                    instaPics.add(pics.get(i));
                }
            }
        }
        Log.v(TAG,"Insta Pics : " + instas);
    }

    @Override
    public Fragment getItem(int position) {

        return InstagramPicsFragment.newInstance(position,instas.get(position));
    }

    @Override
    public int getCount() {
        return getFragmentSize();
    }
}
