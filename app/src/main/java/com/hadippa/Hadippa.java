package com.hadippa;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by HP on 09-07-2016.
 */
public class Hadippa extends Application {

    private static Hadippa sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        FontsOverride.setDefaultFont(this, "DEFAULT", getResources().getString(R.string.proxima_reg));

    }

    public static synchronized Hadippa getInstance() {
        return sInstance;
    }

}
