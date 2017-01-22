package com.hadippa.instagram;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.hadippa.R;

import java.util.ArrayList;


/**
 * Created by Alm on 9/5/2016.
 */
public class Insta {

    final Activity activity;
    final ArrayList<String> list = new ArrayList<String>();

    final InstagramSession instagramSession;
    final Instagram instagram;

    public Insta(Activity activity) {
        this.activity = activity;

        instagram = new Instagram(activity, activity.getString(R.string.instagram_client_key),
                activity.getString(R.string.instagram_client_secret), activity.getString(R.string.instagram_redirect_url));
        instagramSession = instagram.getSession();
        instagramSession.reset();

    }

    public void login() {
        instagram.authorize(mAuthListener);


    }

    public ArrayList<String> instaUserData() {
        return list;
    }

    public String getAccessToken(){


        return instagramSession.getAccessToken();
    }
    private Instagram.InstagramAuthListener mAuthListener = new Instagram.InstagramAuthListener() {
        @Override
        public void onSuccess(InstagramUser user) {

            if (instagramSession.isActive()) {

                InstagramUser instagramUser = instagramSession.getUser();

                instagramSession.store(instagramUser);
                list.clear();
                list.add(instagramUser.id);
                list.add(instagramUser.username);
                list.add(instagramUser.fullName);
                list.add(instagramUser.profilPicture);

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("_insta_",getAccessToken());
                editor.commit();

                Log.d("user>>>",getAccessToken());
            }
        }

        @Override
        public void onError(String error) {
            list.clear();
        }

        @Override
        public void onCancel() {
            list.clear();
        }
    };

}
