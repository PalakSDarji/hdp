package com.hadippa.activities;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.commonclasses.imagetobase64.Base64;
import com.demo.AlbumStorageDirFactory;
import com.demo.BaseAlbumDirFactory;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.model.Contact;
import com.hadippa.model.UserProfile;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class EditProfilePicsActivity extends AppCompatActivity {

    private static final int PIC_ITEM = 0;
    private static final int ADD_ITEM = 1;

    public static RecyclerView mRecyclerView;
    ProgressBar progressBar;
    CustomAdapter customAdapter;
    private ImageView imageBack;
    Uri sourceUri;
    AlbumStorageDirFactory albumStorageDirFactory = null;
    final String JPEG_FILE_PREFIX = "Hadipaa_";
    final String JPEG_FILE_SUFFIX = ".jpg";
    String currentPhotoPath;
    String base64String = "";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    UserProfile.UserBean userBean;

    @BindView(R.id.ivPlus)
    ImageView ivPlus;

    ArrayList<String> picUrls = new ArrayList<>();

    int longClickedImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_people);

        ButterKnife.bind(this);
        sp = PreferenceManager.getDefaultSharedPreferences(EditProfilePicsActivity.this);
        editor = sp.edit();
        albumStorageDirFactory = new BaseAlbumDirFactory();
        userBean = (UserProfile.UserBean) getIntent().getSerializableExtra("data");



        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        ((TextView) findViewById(R.id.tvHeader)).setText(getString(R.string.edit_photo));

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        final GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (customAdapter != null) {
                    switch (customAdapter.getItemViewType(position)) {
                        case ADD_ITEM:
                            return 3;
                        case PIC_ITEM:
                            return 1; //number of columns of the grid
                        default:
                            return -1;
                    }
                } else {
                    return -1;
                }
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);


        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceOldOne = false;
                if (picUrls.size() == 6) {

                    Toast.makeText(EditProfilePicsActivity.this, "Cannot add more photos", Toast.LENGTH_LONG).show();
                    return;
                }
                if (checkPermission()) {

                    selectImage();
                } else {
                    requestPermission();
                }
            }
        });

        setData(userBean);

    }


    void setData(UserProfile.UserBean userBean) {

        picUrls.clear();

        for (int i = 0; i < userBean.getProfile_photos().size(); i++) {

                picUrls.add(userBean.getProfile_photos().get(i).getImage());



        }
        if(customAdapter==null) {
            customAdapter = new CustomAdapter(this, picUrls);
            mRecyclerView.setAdapter(customAdapter);
        }else{
            customAdapter.notifyDataSetChanged();
        }
        //picUrls.add("ADD NEW");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("data", userBean);

        setResult(Activity.RESULT_OK, resultIntent);
        finish();

        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        LayoutInflater inflator;
        List<String> alPics;

        CustomAdapter(Context context, List<String> alPics) {

            inflator = LayoutInflater.from(context);
            this.alPics = alPics;
        }

        @Override
        public int getItemViewType(int position) {
            /*if (position == alPics.size() - 1) {
                return ADD_ITEM;
            }*/
            return PIC_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v;

            if (viewType == ADD_ITEM) {
                v = inflator.inflate(R.layout.item_add_pic, viewGroup, false);
                return new AddViewHolder(v);
            } else {
                v = inflator.inflate(R.layout.joined_people_item, viewGroup, false);
                return new PicViewHolder(v);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

            if (viewHolder instanceof PicViewHolder) {

                PicViewHolder picViewHolder = (PicViewHolder) viewHolder;

                RequestManager requestManager = Glide.with(EditProfilePicsActivity.this);
                requestManager.load(alPics.get(position)).into(picViewHolder.image_view);
                picViewHolder.text_view.setVisibility(View.GONE);


                picViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        replaceOldOne = true;

                        oldPicNumber = position;
                        if (checkPermission()) {

                            selectImage();
                        } else {
                            requestPermission();
                        }
                    }
                });

                picViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {


                        longClickedImage = position;

                        Toast.makeText(EditProfilePicsActivity.this, "Long Clicked  >> " + position, Toast.LENGTH_LONG).show();

                        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(EditProfilePicsActivity.this);
                        LinearLayout optionsEditPhoto = (LinearLayout) LayoutInflater.from(EditProfilePicsActivity.this).inflate(R.layout.options_edit_photo, null);

                        optionsEditPhoto.findViewById(R.id.tvSetMainPhoto).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO do something to set this pic as main pic
                                if (bottomSheetDialog != null && bottomSheetDialog.isShowing())
                                    bottomSheetDialog.dismiss();
                            }
                        });

                        optionsEditPhoto.findViewById(R.id.tvDeletePhoto).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO do something to delete this pic
                                if (bottomSheetDialog != null && bottomSheetDialog.isShowing())

                                    deletePic(longClickedImage);

                                bottomSheetDialog.dismiss();
                            }
                        });
                        bottomSheetDialog.setContentView(optionsEditPhoto);
                        bottomSheetDialog.show();

                        return false;
                    }
                });
            } else if (viewHolder instanceof AddViewHolder) {

                AddViewHolder addViewHolder = (AddViewHolder) viewHolder;
                //do something..

                addViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        replaceOldOne = false;
                        if (picUrls.size() == 6) {

                            Toast.makeText(EditProfilePicsActivity.this, "Cannot add more photos", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (checkPermission()) {

                            selectImage();
                        } else {
                            requestPermission();
                        }
                    }
                });
            }
        }


        @Override
        public int getItemCount() {

            return alPics.size();
        }
    }

    public class PicViewHolder extends RecyclerView.ViewHolder {
        private ImageView image_view;
        private TextView text_view;

        public PicViewHolder(final View v) {
            super(v);


            image_view = (ImageView) v.findViewById(R.id.image_view);
            text_view = (TextView) v.findViewById(R.id.text_view);
        }
    }


    public class AddViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlContainer;

        public AddViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent1 = new Intent(ChatPlusActivity.this, ChatActivity.class);
                    intent1.putExtra("contact",contacts.get(getAdapterPosition()));
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                    Toast.makeText(getApplicationContext(), "Add photo", Toast.LENGTH_LONG).show();
                }
            });

            rlContainer = (RelativeLayout) v.findViewById(R.id.rlContainer);
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout) findViewById(R.id.activity_group_people)), intent.getExtras().getString("messageData"));
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(broadcastReceiver, new IntentFilter("SNACKBAR_MESSAGE"));
    }


    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 239) {

                } else if (resultCode == RESULT_OK && requestCode == 175) {

                    sourceUri = Uri.fromFile(new File(currentPhotoPath));
                    Log.d("ImageUri>>", sourceUri.toString());
                    UCrop.Options options = new UCrop.Options();
                    options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                    options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                    options.setToolbarTitle(getString(R.string.app_name));
                    options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
                    UCrop.of(sourceUri, sourceUri).withOptions(options).withAspectRatio(1, 1)
                            .start(EditProfilePicsActivity.this, UCrop.REQUEST_CROP);

                } else if (resultCode == RESULT_OK && requestCode == 176) {

                    sourceUri = data.getData();
                    Log.d("ImageUri>>", sourceUri.toString());
                    UCrop.Options options = new UCrop.Options();
                    options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                    options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                    options.setToolbarTitle(getString(R.string.app_name));

                    options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
                    UCrop.of(sourceUri, Uri.fromFile(setUpPhotoFile().getAbsoluteFile())).
                            withOptions(options).withAspectRatio(1, 1).start(EditProfilePicsActivity.this, UCrop.REQUEST_CROP);

                } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {

                    Uri resultUri = UCrop.getOutput(data);
                    if (resultUri != null && currentPhotoPath != null) {
                        addPhotoGallery();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] b = baos.toByteArray();

                        base64String = Base64.encode(b);

                        if (replaceOldOne) {
                            updateProfilePic(oldPicNumber);
                        } else {
                            updateProfilePic(picUrls.size());
                        }
                        Log.d("base64String>>", base64String);
                        currentPhotoPath = null;

                    }

                } else if (resultCode == RESULT_OK && requestCode == UCrop.RESULT_ERROR) {
                    Log.d("onActivityResult", "UCrop Error");
                }
            }
        } catch (Exception e) {
        }
    }

    private boolean checkPermission() {

        int CAMERA = ContextCompat.checkSelfPermission(EditProfilePicsActivity.this, Manifest.permission.CAMERA);
        int read_external = ContextCompat.checkSelfPermission(EditProfilePicsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write_external = ContextCompat.checkSelfPermission(EditProfilePicsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (CAMERA == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED
                && write_external == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {
            //   requestPermission();
            return false;

        }
    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(EditProfilePicsActivity.this, new String[]{Manifest.permission.CAMERA
                , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 10001);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10001:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //  Snackbar.make(rel, "Permission Granted.", Snackbar.LENGTH_LONG).show();

                    selectImage();
                } else {

                    //  Snackbar.make(rel, "Permission Denied.", Snackbar.LENGTH_LONG).show();

                }
                break;
        }
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        TextView title = new TextView(EditProfilePicsActivity.this);
        title.setText("Add Photo!");
        title.setBackgroundColor(Color.BLACK);
        title.setPadding(10, 15, 15, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);


        AlertDialog.Builder builder = new AlertDialog.Builder(
                EditProfilePicsActivity.this);


        builder.setCustomTitle(title);

        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    // Intent intent = new
                    // Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePhoto();

                } else if (items[item].equals("Choose from Library")) {

                    sharePhoto();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    boolean replaceOldOne = false;
    int oldPicNumber = 0;

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
        startActivityForResult(takePictureIntent, 175);
    }

    public void sharePhoto() {

        Intent shareIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(shareIntent, 176);


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
        EditProfilePicsActivity.this.sendBroadcast(mediaScanIntent);
    }

    private void updateProfilePic(int number) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("user_id", getIntent().getExtras().getString(AppConstants.FETCH_USER_KEY));
            requestParams.add("profile_photo", base64String);
            requestParams.add("flag", String.valueOf(number));

            Log.d("fetchProfile>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.UPDATE_PIC, requestParams,
                new UpdateProfilePic());
    }

    class UpdateProfilePic extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            //  if (!getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {

            AppConstants.showProgressDialog(EditProfilePicsActivity.this, "Please Wait");
            //    }
        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                Log.d("fetchProfile>>", "success" + response);

                JSONObject jsonObjectRes = new JSONObject(response);
                if (jsonObjectRes.getBoolean("success")) {

                    editor.putString("userData", jsonObjectRes.getJSONObject("user").toString());
                    editor.commit();

                    try {
                        JSONObject jsonObject = new JSONObject(sp.getString("userData", ""));
                        userBean = new UserProfile.UserBean();
                        userBean.setId(jsonObject.getInt("id"));
                        userBean.setFirst_name(jsonObject.getString("first_name"));
                        userBean.setLast_name(jsonObject.getString("last_name"));
                        userBean.setOccupation(jsonObject.getString("occupation"));
                        userBean.setInterested_in(jsonObject.getString("interested_in"));
                        userBean.setLanuage_known(jsonObject.getString("lanuage_known"));
                        userBean.setCity(jsonObject.getString("city"));
                        userBean.setCompany(jsonObject.getString("company"));
                        userBean.setAge_range_from(jsonObject.getInt("age_range_from"));
                        userBean.setAge_range_to(jsonObject.getInt("age_range_to"));
                        userBean.setMobile(jsonObject.getLong("mobile"));
                        userBean.setZodiac(jsonObject.getString("zodiac"));
                        userBean.setProfile_photo(jsonObject.getString("profile_photo"));
                        userBean.setPrivate_account(jsonObject.getInt("private_account"));
                        userBean.setDob(jsonObject.getString("dob"));
                        if(jsonObject.has("age")) {
                            userBean.setAge(jsonObject.getInt("age"));
                        }
                        List<UserProfile.UserBean.ProfilePhotosBean> profilePhotosBeen = new ArrayList<>();
                        for(int i =0 ;i < jsonObject.getJSONArray("profile_photos").length();i++){
                            JSONObject jsonObject1 = jsonObject.getJSONArray("profile_photos").getJSONObject(i);
                            UserProfile.UserBean.ProfilePhotosBean photosBean = new UserProfile.UserBean.ProfilePhotosBean();
                            photosBean.setIdX(jsonObject1.getInt("id"));
                            photosBean.setImage(jsonObject1.getString("image"));
                            photosBean.setUser_id(jsonObject1.getInt("user_id"));
                            profilePhotosBeen.add(photosBean);
                        }
                        userBean.setProfile_photos(profilePhotosBeen);
                        setData(userBean);

                        customAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
/*

                    RequestManager requestManager= Glide.with(EditProfilePicsActivity.this);
                    requestManager.load(userBean.getProfile_photo()).error(R.drawable.place_holder)
                            .placeholder(R.drawable.place_holder).into(ivProfilePic);
*/

                }

                Log.d("fetchProfile>>", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }


        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }


    }

    private void deletePic(int number) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("index", String.valueOf(number));

            Log.d("fetchProfile>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.DELETE_PIC, requestParams,
                new DeletePic());
    }

    class DeletePic extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            //  if (!getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {

            AppConstants.showProgressDialog(EditProfilePicsActivity.this, "Please Wait");
            //    }
        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                Log.d("fetchProfile>>", "success" + response);

                JSONObject jsonObjectRes = new JSONObject(response);
                if (jsonObjectRes.getBoolean("success")) {

                    Toast.makeText(EditProfilePicsActivity.this, "Pic Deleted", Toast.LENGTH_LONG).show();

                    if (jsonObjectRes.has("userData")) {

                        editor.putString("userData", jsonObjectRes.getJSONObject("user").toString());
                        editor.commit();

                        try {
                            JSONObject jsonObject = new JSONObject(sp.getString("userData", ""));
                            userBean = new UserProfile.UserBean();
                            userBean.setId(jsonObject.getInt("id"));
                            userBean.setFirst_name(jsonObject.getString("first_name"));
                            userBean.setLast_name(jsonObject.getString("last_name"));
                            userBean.setOccupation(jsonObject.getString("occupation"));
                            userBean.setInterested_in(jsonObject.getString("interested_in"));
                            userBean.setLanuage_known(jsonObject.getString("lanuage_known"));
                            userBean.setCity(jsonObject.getString("city"));
                            userBean.setCompany(jsonObject.getString("company"));
                            userBean.setAge_range_from(jsonObject.getInt("age_range_from"));
                            userBean.setAge_range_to(jsonObject.getInt("age_range_to"));
                            userBean.setMobile(jsonObject.getLong("mobile"));
                            userBean.setZodiac(jsonObject.getString("zodiac"));
                            userBean.setProfile_photo(jsonObject.getString("profile_photo"));
                            userBean.setProfile_photo_1(jsonObject.getString("profile_photo_1"));
                            userBean.setProfile_photo_2(jsonObject.getString("profile_photo_2"));
                            userBean.setProfile_photo_3(jsonObject.getString("profile_photo_3"));
                            userBean.setProfile_photo_4(jsonObject.getString("profile_photo_4"));
                            userBean.setProfile_photo_5(jsonObject.getString("profile_photo_5"));
                            userBean.setPrivate_account(jsonObject.getInt("private_account"));
                            userBean.setDob(jsonObject.getString("dob"));
                            userBean.setAge(jsonObject.getInt("age"));

                            List<UserProfile.UserBean.ProfilePhotosBean> profilePhotosBeen = new ArrayList<>();
                            for(int i =0 ;i < jsonObject.getJSONArray("profile_photos").length();i++){
                                JSONObject jsonObject1 = jsonObject.getJSONArray("profile_photos").getJSONObject(i);
                                UserProfile.UserBean.ProfilePhotosBean photosBean = new UserProfile.UserBean.ProfilePhotosBean();
                                photosBean.setIdX(jsonObject1.getInt("id"));
                                photosBean.setImage(jsonObject1.getString("image"));
                                photosBean.setUser_id(jsonObject1.getInt("user_id"));
                                profilePhotosBeen.add(photosBean);
                            }
                            userBean.setProfile_photos(profilePhotosBeen);
                            setData(userBean);

                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                } else {
/*

                    RequestManager requestManager= Glide.with(EditProfilePicsActivity.this);
                    requestManager.load(userBean.getProfile_photo()).error(R.drawable.place_holder)
                            .placeholder(R.drawable.place_holder).into(ivProfilePic);
*/

                }

                Log.d("fetchProfile>>", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }


        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }


    }


}
