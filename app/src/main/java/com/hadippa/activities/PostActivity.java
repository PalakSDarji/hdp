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

import com.hadippa.AppConstants;
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

        findViewById(R.id.ivCoffee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, CoffeeActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_COFFEE);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.ivGlass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDrinksDialog();
            }
        });

        findViewById(R.id.ivHobby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, CreateActivityActvity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_HOBBY);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.ivPlusBlue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, CreateActivityActvity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_CREATE_ACTIVITY);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.ivTravel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTravelDialog();
               /* Intent intent = new Intent(PostActivity.this, TravelActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_TRAVEL_FROM_POST);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            }
        });

        findViewById(R.id.ivEntertainment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, EntertainmentActivity.class);
               // intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_TRAVEL_FROM_POST);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }


    void showTravelDialog() {

        dialog1 = new Dialog(this, R.style.DialogSlideAnim);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_travel);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        WindowManager.LayoutParams params = dialog1.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog1.getWindow().setAttributes(params);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));

        ImageView ivClose = (ImageView) dialog1.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        ImageView ivAir = (ImageView) dialog1.findViewById(R.id.ivAir);
        ivAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, TravelActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_TRAVEL_FROM_POST);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivBus = (ImageView) dialog1.findViewById(R.id.ivBus);
        ivBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, TravelActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_TRAVEL_FROM_POST);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivTrain = (ImageView) dialog1.findViewById(R.id.ivTrain);
        ivTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, TravelActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_TRAVEL_FROM_POST);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        dialog1.show();
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

        ImageView ivIndoor = (ImageView) dialog1.findViewById(R.id.ivIndoor);
        ivIndoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, CreateActivityActvity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_INDOOR_SPORTS);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView outIndoor = (ImageView) dialog1.findViewById(R.id.ivOutdoor);
        outIndoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, CreateActivityActvity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_OUTDOOR_SPORTS);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

        ImageView ivNightClub = (ImageView) dialog1.findViewById(R.id.ivNightClub);
        ivNightClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, CoffeeActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_NIGHTCLUB);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivLounge = (ImageView) dialog1.findViewById(R.id.ivLounge);
        ivLounge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, CoffeeActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_LOUNGE);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivParty = (ImageView) dialog1.findViewById(R.id.ivParty);
        ivParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, CreateActivityActvity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_PARTY);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivStandup = (ImageView) dialog1.findViewById(R.id.ivStandup);
        ivStandup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, CreateActivityActvity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_PARTY);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        dialog1.show();
    }
}
