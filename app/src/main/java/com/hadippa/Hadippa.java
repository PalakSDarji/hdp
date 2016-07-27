package com.hadippa;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by HP on 09-07-2016.
 */
public class Hadippa extends Application {

    private static Hadippa sInstance;
    private static Typeface bold;

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "proxima_regular.OTF");

        FontsOverride.setDefaultFont(this, "MONOSPACE", "proxima_regular.OTF");
     //   FontsOverride.setDefaultFont(this, "MONOSPACE", "MyFontAsset2.ttf");
     //   FontsOverride.setDefaultFont(this, "SERIF", "MyFontAsset3.ttf");
     //   FontsOverride.setDefaultFont(this, "SANS_SERIF", "MyFontAsset4.ttf");

        sInstance = this;
        bold = Typeface.createFromAsset(getApplicationContext().getAssets(), "proxima_bold.OTF");
    }


    public static Typeface getBold() {
        return bold;
    }

    public static void setBold(Typeface bold) {
        Hadippa.bold = bold;
    }

    public static synchronized Hadippa getInstance() {
        return sInstance;
    }

}
