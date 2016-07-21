package com.hadippa;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by HP on 09-07-2016.
 */
public class Hadippa extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "proxima_regular.OTF");

        FontsOverride.setDefaultFont(this, "MONOSPACE", "proxima_regular.OTF");
     //   FontsOverride.setDefaultFont(this, "MONOSPACE", "MyFontAsset2.ttf");
     //   FontsOverride.setDefaultFont(this, "SERIF", "MyFontAsset3.ttf");
     //   FontsOverride.setDefaultFont(this, "SANS_SERIF", "MyFontAsset4.ttf");
    }

}
