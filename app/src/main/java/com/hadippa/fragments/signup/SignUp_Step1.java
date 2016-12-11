package com.hadippa.fragments.signup;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonclasses.imagetobase64.Base64;
import com.crop_image.Constants;
import com.crop_image.ImageCropActivity;
import com.crop_image.PicModeSelectDialogFragment;
import com.demo.AlbumStorageDirFactory;
import com.demo.BaseAlbumDirFactory;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.activities.SignUp;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yalantis.ucrop.UCrop;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.hadippa.R.id.tvNext;


/**
 * Created by alm-android on 01-12-2015.
 */
public class SignUp_Step1 extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    private Context mContext;

    Uri sourceUri;
    AlbumStorageDirFactory albumStorageDirFactory = null;
    final String JPEG_FILE_PREFIX = "Hadipaa_";
    final String JPEG_FILE_SUFFIX = ".jpg";
    String currentPhotoPath;
    String base64String="";

    public static SignUp_Step1 newInstance(int page, String title) {
        SignUp_Step1 fragmentFirst = new SignUp_Step1();
        Log.d("FRAGMENT_LOG", "Crewated ");
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    public static EditText edtFullname, edtPassword, edtConfirmPassword, edtEmail;
    RelativeLayout mainRel, imageLayout;
    ImageButton ivIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sign_up, null, false);

        mContext = getActivity();

        albumStorageDirFactory = new BaseAlbumDirFactory();

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

        mainRel = (RelativeLayout) view.findViewById(R.id.mainRel);
        imageLayout = (RelativeLayout) view.findViewById(R.id.imageLayout);
        edtFullname = (EditText) view.findViewById(R.id.edtFullName);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) view.findViewById(R.id.edtConfirmPassword);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        ivIcon = (ImageButton) view.findViewById(R.id.ivIcon);
        TextView tvNext = (TextView) view.findViewById(R.id.tvNext);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // SignUp.customViewPager.setCurrentItem(SignUp.customViewPager.getCurrentItem()+1);
                if (validate()) {

                    signUpStep_1();
                    //  SignUp.customViewPager.setCurrentItem(SignUp.customViewPager.getCurrentItem()+1);

                }


            }
        });

        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    selectImage();
                } else {
                    requestPermission();
                }
            }
        });

        return view;


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

          //  Log.d("onActivityResult","onActivityResult Fragemnt: RequestCode" + requestCode + " ResultCode: " + resultCode + " Path: " + data.getData().getPath());

                if (resultCode == RESULT_OK && requestCode == 175) {

                    sourceUri = Uri.fromFile(new File(currentPhotoPath));
                    Log.d("ImageUri>>", sourceUri.toString());
                    UCrop.Options options = new UCrop.Options();
                    options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                    options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                    options.setToolbarTitle(getString(R.string.app_name));
                    options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
                    UCrop.of(sourceUri, sourceUri).withOptions(options).withAspectRatio(1,1)
                            .start(getActivity(), UCrop.REQUEST_CROP);

                } else if (resultCode == RESULT_OK && requestCode == 176) {

                    sourceUri = data.getData();
                    Log.d("ImageUri>>", sourceUri.toString());
                    UCrop.Options options = new UCrop.Options();
                    options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                    options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                    options.setToolbarTitle(getString(R.string.app_name));

                    options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
                    UCrop.of(sourceUri, Uri.fromFile(setUpPhotoFile().getAbsoluteFile())).withOptions(options).withAspectRatio(1,1).start(getActivity(), UCrop.REQUEST_CROP);

                }else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {

                    Uri resultUri = UCrop.getOutput(data);
                    if (resultUri != null && currentPhotoPath != null) {
                        addPhotoGallery();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);

                        ivIcon.setImageBitmap(bitmap);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] b = baos.toByteArray();

                        base64String = Base64.encode(b);

                        Log.d("base64String>>",base64String);
                        currentPhotoPath = null;

                    }

                } else if (resultCode == RESULT_OK && requestCode == UCrop.RESULT_ERROR) {
                    Log.d("onActivityResult",  "UCrop Error");
                }
                //encode(myBitmap);

        } catch (Exception e) {

        }
    }




    boolean validate() {

        if (edtFullname.getText().toString().trim().length() == 0) {

            edtFullname.setError("Cannot be empty.");
            return false;

        }

        if (!edtFullname.getText().toString().trim().contains(" ")) {

            edtFullname.setError("Enter fullname.");
            return false;

        }
        if (edtEmail.getText().toString().trim().length() == 0) {

            edtEmail.setError("Cannot be empty.");
            return false;

        }

        if (edtPassword.getText().toString().trim().length() == 0) {

            edtPassword.setError("Cannot be empty.");
            return false;

        }

        if (edtConfirmPassword.getText().toString().trim().length() == 0) {

            edtConfirmPassword.setError("Cannot be empty.");
            return false;

        }

        if (!edtPassword.getText().toString().trim().equals(edtConfirmPassword.getText().toString().trim())) {

            edtConfirmPassword.setError("Password Mismatch");
            return false;

        }

        if (!AppConstants.isValidEmail(edtEmail.getText().toString().trim())) {

            edtEmail.setError("Invalid email.");
            return false;
        }

        return true;

    }


    private void signUpStep_1() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("client_id", AppConstants.CLIENT_ID);

            requestParams.add("client_secret", AppConstants.CLIENT_SECRET);

            requestParams.add("device_id", Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID));

            requestParams.add("device_os_version", String.valueOf(Build.VERSION.SDK_INT));

            requestParams.add("grant_type", sp.getString("grantType", "password"));

            requestParams.add("device_type", "android");

            requestParams.add("device_token", sp.getString("gcmId", ""));

            String seperated[] = edtFullname.getText().toString().trim().split(" ");
            requestParams.add("first_name", seperated[0]);

            requestParams.add("last_name", seperated[1]);

            requestParams.add("password", edtPassword.getText().toString().trim());
            requestParams.add("profile_photo",base64String);
            requestParams.add("confirm_password", edtConfirmPassword.getText().toString().trim());

            requestParams.add("email", edtEmail.getText().toString().trim());


            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.SIGN_UP_STEP_1, requestParams,
                new SignUpStep_1());
    }

    class SignUpStep_1 extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            //  dataScroll.setVisibility(View.GONE);
            AppConstants.showProgressDialog(getActivity(), "Please Wait");

        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
            Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));

//            updateDonut((int) ((totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("success")) {

                    editor.putInt("user", jsonObject.getInt("user"));
                    editor.commit();

                    SignUp.customViewPager.setCurrentItem(SignUp.customViewPager.getCurrentItem() + 1);

                } else {
                    AppConstants.showSnackBar(mainRel, "Could not submit details");
                }
                Log.d("async", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


            AppConstants.showSnackBar(mainRel, "Could not submit details");

            try {
                String response = new String(responseBody, "UTF-8");
                Log.d("async", "failure" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "failure" + e.toString());
            }
        }

    }

    private boolean checkPermission() {

        int CAMERA = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        int read_external = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int write_external = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (CAMERA == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED
                && write_external == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {
            //   requestPermission();
            return false;

        }
    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA
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

        TextView title = new TextView(getActivity());
        title.setText("Add Photo!");
        title.setBackgroundColor(Color.BLACK);
        title.setPadding(10, 15, 15, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);


        AlertDialog.Builder builder = new AlertDialog.Builder(
                getActivity());


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
        getActivity().sendBroadcast(mediaScanIntent);
    }
}



