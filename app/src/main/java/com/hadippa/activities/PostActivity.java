package com.hadippa.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hadippa.R;

public class PostActivity extends AppCompatActivity {

    Dialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);

        initUi();
    }

    private void initUi() {

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        findViewById(R.id.ivSports).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSportsDialog();
            }
        });

        findViewById(R.id.ivGlass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDrinksDialog();
            }
        });


    }


    void showSportsDialog() {


        /*Bitmap f = fastblur(v,15);*/
        dialog1 = new Dialog(this, R.style.DialogSlideAnim);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_sports);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        WindowManager.LayoutParams params = dialog1.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog1.getWindow().setAttributes(params);
        //Drawable d = new BitmapDrawable(getResources(), blurred);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));

        ImageView ivClose = (ImageView) dialog1.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        ImageView ivAdve = (ImageView) dialog1.findViewById(R.id.ivAdve);
        ivAdve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                showSportsAdveDialog();
            }
        });

        dialog1.show();
    }

    void showSportsAdveDialog() {


        /*Bitmap f = fastblur(v,15);*/
        dialog1 = new Dialog(this, R.style.DialogSlideAnim);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_sports_adve);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        WindowManager.LayoutParams params = dialog1.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog1.getWindow().setAttributes(params);
        //Drawable d = new BitmapDrawable(getResources(), blurred);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));

        ImageView ivClose = (ImageView) dialog1.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        ImageView ivSport = (ImageView) dialog1.findViewById(R.id.ivSport);
        ivSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                showSportsDialog();
            }
        });

        dialog1.show();
    }

    void showDrinksDialog() {


        /*Bitmap f = fastblur(v,15);*/
        dialog1 = new Dialog(this, R.style.DialogSlideAnim);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_drinks);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        WindowManager.LayoutParams params = dialog1.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog1.getWindow().setAttributes(params);
        //Drawable d = new BitmapDrawable(getResources(), blurred);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));

        ImageView ivClose = (ImageView) dialog1.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.show();
    }
}
