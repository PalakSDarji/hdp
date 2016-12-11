/*
package com.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.View;

import com.beh.Adpaters.DrawerAdapter;
import com.beh.AlbumStorageDirFactory;
import com.beh.BaseAlbumDirFactory;
import com.beh.BaseClasses.BaseActivity;
import com.beh.Constants;
import com.beh.ModelClasses.DrawerRow;
import com.beh.ModelClasses.UserData;
import com.beh.R;
import com.beh.Adpaters.RecentPhotosAdapter;
import com.beh.databinding.ActivityHomeBinding;
import com.beh.databinding.AppBarHomeBinding;
import com.beh.databinding.ContentHomeBinding;
import com.beh.databinding.NavHeaderHomeBinding;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    ActivityHomeBinding homeBinding;
    AppBarHomeBinding appBarHomeBinding;
    ContentHomeBinding contentHomeBinding;
    NavHeaderHomeBinding navHeaderHomeBinding;

    LinearLayoutManager layoutManager_drawer;
    DrawerAdapter drawerAdapter;

    GridLayoutManager layoutManager_recentPhotos;
    RecentPhotosAdapter recentPhotosAdapter;

    boolean appBarIsExpanded = true;

    Constants constants = new Constants(this);

    Uri sourceUri;
    AlbumStorageDirFactory albumStorageDirFactory = null;
    final String JPEG_FILE_PREFIX = "PhotoApp_";
    final String JPEG_FILE_SUFFIX = ".jpg";
    String currentPhotoPath;

    boolean isSharePhoto = false;

    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeBinding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        navHeaderHomeBinding = DataBindingUtil.findBinding(homeBinding.navHeaderHome.getRoot());
        appBarHomeBinding = DataBindingUtil.findBinding(homeBinding.appBarHome.getRoot());
        contentHomeBinding = DataBindingUtil.findBinding(appBarHomeBinding.contentHome.getRoot());

        if (constants.checkUserData()) {
            userData = constants.getUserData();

            constants.loadCircleImage(navHeaderHomeBinding.civProfile, userData.getPhotoUrl(), R.drawable.ph_male);
            constants.loadCircleImage(appBarHomeBinding.civProfile, userData.getPhotoUrl(), R.drawable.ph_male);

            navHeaderHomeBinding.tvName.setText(userData.getDisplayName());
            appBarHomeBinding.tvName.setText(userData.getDisplayName());
        }

        List<DrawerRow> list = new ArrayList<>();
        list.add(new DrawerRow(getResources().getString(R.string.home), getResources().getDrawable(R.drawable.home)));
        list.add(new DrawerRow(getResources().getString(R.string.profile), getResources().getDrawable(R.drawable.user_w)));
        list.add(new DrawerRow(getResources().getString(R.string.myAccount), getResources().getDrawable(R.drawable.settings_w)));
        list.add(new DrawerRow(getResources().getString(R.string.logout), getResources().getDrawable(R.drawable.logout)));

        drawerAdapter = new DrawerAdapter(list, HomeActivity.this);
        layoutManager_drawer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
        homeBinding.rvNav.setLayoutManager(layoutManager_drawer);
        homeBinding.rvNav.setAdapter(drawerAdapter);

        List<Drawable> drawableList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            drawableList.add(getResources().getDrawable(R.drawable.selector_photos));
        }

        recentPhotosAdapter = new RecentPhotosAdapter(drawableList, HomeActivity.this);
        layoutManager_recentPhotos = new GridLayoutManager(HomeActivity.this, 4);
        contentHomeBinding.rvRecentPhotos.setLayoutManager(layoutManager_recentPhotos);
        contentHomeBinding.rvRecentPhotos.setAdapter(recentPhotosAdapter);

        appBarHomeBinding.appBarHome.addOnOffsetChangedListener(this);

        albumStorageDirFactory = new BaseAlbumDirFactory();

    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {

        if (appBarIsExpanded && !homeBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {

            finishAffinity();

        } else {

            if (homeBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                homeBinding.drawerLayout.closeDrawer(GravityCompat.START, true);
            }

            if (!appBarIsExpanded) {
                appBarIsExpanded = true;
                appBarHomeBinding.appBarHome.setExpanded(true, true);
            }

        }

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        Log.d("Offset>>>", String.valueOf(verticalOffset));
        if (verticalOffset == 0) {
            appBarIsExpanded = true;
        } else {
            appBarIsExpanded = false;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_drawer:
                if (homeBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    homeBinding.drawerLayout.closeDrawer(GravityCompat.START, true);
                } else {
                    homeBinding.drawerLayout.openDrawer(GravityCompat.START, true);
                }
                break;
            case R.id.iv_profile:
                startActivity(new Intent(HomeActivity.this, MyAccountActivity.class));
                break;
            case R.id.iv_settings:
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                break;
            case R.id.btn_photos:
                constants.popSnackBar("Photos");
                break;
            case R.id.btn_favourites:
                constants.popSnackBar("Favourites");
                break;
            case R.id.btn_notifications:
                constants.popSnackBar("Notifications");
                break;
            case R.id.btn_takePhotos:
                takePhoto();
                break;
            case R.id.btn_sharePhotos:
                sharePhoto();
                break;
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == constants.IMAGE_CAPTURE_REQUEST && resultCode == RESULT_OK) {

            isSharePhoto = false;
            sourceUri = Uri.fromFile(new File(currentPhotoPath));
            Log.d("ImageUri>>", sourceUri.toString());
            UCrop.Options options = new UCrop.Options();
            options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
            options.setToolbarTitle(getString(R.string.app_name));
            options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
            UCrop.of(sourceUri, sourceUri).withOptions(options).start(HomeActivity.this, UCrop.REQUEST_CROP);


        } else if (requestCode == constants.IMAGE_SHARE_REQUEST && resultCode == RESULT_OK) {

            try {

                isSharePhoto = true;
                sourceUri = data.getData();
                Log.d("ImageUri>>", sourceUri.toString());
                UCrop.Options options = new UCrop.Options();
                options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                options.setToolbarTitle(getString(R.string.app_name));
                options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
                UCrop.of(sourceUri, Uri.fromFile(setUpPhotoFile().getAbsoluteFile())).withOptions(options).start(HomeActivity.this, UCrop.REQUEST_CROP);

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {

            Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null && currentPhotoPath != null) {
                addPhotoGallery();
                resultUri = null;
                currentPhotoPath = null;
                if (!isSharePhoto) {
                    constants.popSnackBar(getString(R.string.photoUploaded), contentHomeBinding.tvStatusView);
                } else {
                    constants.popSnackBar(getString(R.string.photoShared), contentHomeBinding.tvStatusView);
                }
            }

        } else if (resultCode == RESULT_OK && requestCode == UCrop.RESULT_ERROR) {

            Log.d(getString(R.string.app_name), UCrop.getError(data).getMessage());
            constants.popSnackBar(getString(R.string.errorSomething));

        }

    }

    public String getAlbumName() {
        return getString(R.string.app_name);
    }

    public File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = albumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d(getString(R.string.app_name), "Failed to Create Directory");
                    }
                }
            } else {
                storageDir.mkdirs();
                if (storageDir.exists()) {
                    Log.d(getString(R.string.app_name), "Success to Create Directory");
                    return storageDir;
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External Storage is not Mounted READ/WRITE.");
        }

        return storageDir;
    }

    public void takePhoto() {

        if (constants.checkSinglePermission(android.Manifest.permission.CAMERA)
                && constants.checkSinglePermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = null;
            try {
                file = setUpPhotoFile();
                currentPhotoPath = file.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            } catch (IOException e) {
                if (e != null) {
                    e.printStackTrace();
                }
                file = null;
                currentPhotoPath = null;
                return;
            }
            startActivityForResult(takePictureIntent, constants.IMAGE_CAPTURE_REQUEST);

        } else {
            constants.requestPermission(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    }

    public void sharePhoto() {

        if (constants.checkSinglePermission(android.Manifest.permission.CAMERA)
                && constants.checkSinglePermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            Intent shareIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(shareIntent, constants.IMAGE_SHARE_REQUEST);

        } else {
            constants.requestPermission(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    }

    public File setUpPhotoFile() throws IOException {
        File file = createImageFile();
        currentPhotoPath = file.getAbsolutePath();
        return file;
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    public void addPhotoGallery() {

        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File file = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == constants.PERMISSION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            constants.popSnackBar(getString(R.string.permissionGranted));
        } else {
            constants.popSnackBar(getString(R.string.permissionRequired));
        }

    }

}
*/
