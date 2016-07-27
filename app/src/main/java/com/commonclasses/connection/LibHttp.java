package com.commonclasses.connection;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.hadippa.AppConstants;
import com.hadippa.model.HttpNameValue;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LibHttp {

    private String url;

    private ArrayList<HttpNameValue> alPostParams;

    private final String crlf = "\r\n";

    private final String twoHyphens = "--";

    private final String boundary = "*****";

    public LibHttp() {
        url="";

        alPostParams = new ArrayList<>();

    }



    /**
     * Make web call to register
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    public String signUp(String fullName, String email , String password, String role) throws Exception {

     //   url = AppConstants.BASE_URL + AppConstants.SIGN_UP;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "display_name", fullName, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "email", email, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "password", password, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "role", role, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url register : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE register : "+jsonRes);

        return jsonRes;
    }

    public String login(Context context,String grant_type, String email ,
                        String password, String accessTokenFb, String device_token) throws Exception

    {

        url = AppConstants.BASE_URL +AppConstants.API_VERSION + AppConstants.LOGIN;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "client_id", AppConstants.CLIENT_ID, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "client_secret", AppConstants.CLIENT_SECRET, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "device_id", Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID), null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "device_os_version", String.valueOf(Build.VERSION.SDK_INT), null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "grant_type",grant_type, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "device_type","android", null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "device_token",device_token, null));

        if(grant_type.equals("facebook")){

            alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "code", accessTokenFb, null));

        }else {
            alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "username", email, null));

            alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "password", password, null));
        }

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url loginEmail : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE loginEmail : "+jsonRes);

        return jsonRes;
    }

    public String loginFb(String fbId, String deviceId,  String email, String name, String role, String deviceToken) throws Exception {

        url = AppConstants.BASE_URL + AppConstants.LOGIN_BY_THIRD_PARTY;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "thirdparty_id", fbId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "device_id", deviceId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "email", email, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "device_type", AppConstants.DEVICE_TYPE, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "login_through", AppConstants.LOGIN_STATUS_FB, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "device_token", deviceToken, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "display_name", name, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "role", role+"", null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url loginFb : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE loginFb : "+jsonRes);

        return jsonRes;

    }

    public String changePassword(String uId , String token, String currentPassword, String newPassword) throws Exception {

        url = AppConstants.BASE_URL + AppConstants.CHANGE_PASSWORD;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_id", uId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_token", token, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "current_password", currentPassword, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "new_password", newPassword, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url changePassword : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE changePassword : "+jsonRes);

        return jsonRes;
    }

    public String sendPassword(String email) throws Exception {

        url = AppConstants.BASE_URL + AppConstants.SEND_PASSWORD;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "email", email, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url sendPassword : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE sendPassword : "+jsonRes);

        return jsonRes;
    }

    public String resetPassword(String uId , String token, String newPassword, String tempPassword) throws Exception {

        url = AppConstants.BASE_URL + AppConstants.RESET_PASSWORD;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_id", uId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_token", token, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "new_password", newPassword, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "temp_password", tempPassword, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url changePassword : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE changePassword : "+jsonRes);

        return jsonRes;
    }


    /**
     *
     * @param method
     * @return
     * @throws Exception
     */
    public String getJSONResponse(String method) throws Exception {

        if (AppConstants.DEBUG) Log.d(AppConstants.DEBUG_TAG, "getJsonResponse");

        HttpURLConnection httpUrlConnection = null;

        OutputStream outputStream = null;

        InputStream inputStream = null;

        InputStreamReader in = null;

        try {
            URL urlObj = new URL(url);

            httpUrlConnection = (HttpURLConnection) urlObj.openConnection();

            httpUrlConnection.setReadTimeout(AppConstants.SOCKET_TIMEOUT * 1000);

            httpUrlConnection.setConnectTimeout(AppConstants.CONNECTION_TIMEOUT * 1000);

            httpUrlConnection.setDoInput(true);

            if(alPostParams.size() > 0)
            {
                httpUrlConnection.setUseCaches(false);
                httpUrlConnection.setRequestMethod(method);

                httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
                httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
                httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + this.boundary);

                outputStream = httpUrlConnection.getOutputStream();

                if (AppConstants.DEBUG)Log.d(AppConstants.DEBUG_TAG, "POST File : " + url);

                outputStream.write((this.crlf + this.twoHyphens + this.boundary + this.crlf).getBytes());

                for(int i=0;i<alPostParams.size();i++){

                    HttpNameValue postParam = alPostParams.get(i);

                    if(postParam.getType() == HttpNameValue.TEXT)
                    {
                        outputStream.write(("Content-Disposition: form-data; name=\"" + postParam.getName() + "\";"
                                + this.crlf
                                + this.crlf
                                + postParam.getValue()).getBytes());

                        outputStream.write(this.crlf.getBytes());
                        outputStream.write((this.twoHyphens + this.boundary + this.crlf).getBytes()); //end of part

                    }

                }
                outputStream.flush();
                outputStream.close();
            }
            else if(method.equalsIgnoreCase("POST"))
            {
                if (AppConstants.DEBUG) Log.d(AppConstants.DEBUG_TAG, "HTTP (POST) : " + url);
                httpUrlConnection.setRequestMethod("POST");
            }
            else
            {
                httpUrlConnection.setRequestMethod("GET");
                if (AppConstants.DEBUG) Log.d(AppConstants.DEBUG_TAG, "HTTP (GET) : " + url);
            }

            httpUrlConnection.connect();

            if (AppConstants.DEBUG) Log.d(AppConstants.DEBUG_TAG, "Response Code : " + httpUrlConnection.getResponseCode());

            if(httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK )
                inputStream = httpUrlConnection.getInputStream();
            else
                inputStream = httpUrlConnection.getErrorStream();

            in = new InputStreamReader(inputStream);

            StringBuilder sb = new StringBuilder();

            int read;

            char[] buff = new char[1024];

            while ((read = in.read(buff)) != -1) {
                sb.append(buff, 0, read);
            }

            if (AppConstants.DEBUG) Log.d(AppConstants.DEBUG_TAG, "Response text : " + sb.toString());

            if(httpUrlConnection.getResponseCode() != 200 )
            {
                throw new Exception("Server response is not OK");
            }

            return sb.toString();
        }

        catch (Exception e) {
            if (outputStream != null) {
                outputStream.close();
            }

            if (in != null) {
                in.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
            }

            throw e;
        }
    }
/*

    public String getCategories() throws Exception {

        url = AppConstants.BASE_URL + AppConstants.LIST_CATEGORIES;

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url getCategories : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE getCategories : "+jsonRes);

        return jsonRes;
    }

    public String getTasks(String uId, String token, String lat, String lng, String day, String radius, String page) throws Exception {

        url = AppConstants.BASE_URL + AppConstants.LIST_TASK;

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "POST Params : "+uId+","+token+","+lat+","+lng+","+day+","+radius+","+page);

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_id", uId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_token", token, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "latitude", lat, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "longitude", lng, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "localtime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "day", "0", null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "radius", radius, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "page", page, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url getTasks : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE getTasks : "+jsonRes);

        return jsonRes;
    }

    public String getAllTasks(String uId, String token) throws Exception {

        url = AppConstants.BASE_URL + AppConstants.LIST_TASK;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_id", uId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_token", token, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url getAllTasks : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE getAllTasks : "+jsonRes);

        return jsonRes;
    }

    public String getUserProfile(String uId, String token, String otherUserId) throws Exception {

        url =  AppConstants.GET_OTHER_USER_PROFILE;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_id", uId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_token", token, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "other_user_id", otherUserId, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url getUserProfile : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE getUserProfile : "+jsonRes);

        return jsonRes;
    }

    public String getFeedComments(String uId , String token, String feedId) throws Exception {

        url = AppConstants.BASE_URL + AppConstants.GET_FEED_COMMENT;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_id", uId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_token", token, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "feed_id", feedId, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url getFeedComments : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE getFeedComments : "+jsonRes);

        return jsonRes;
    }

    public String addComment(String uId , String token, String feedId, String commentText, String feedOwnerId) throws Exception {

        url = AppConstants.BASE_URL + AppConstants.DO_FEED_COMMENT;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_id", uId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_token", token, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "feed_id", feedId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "comment_text", commentText, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "feed_owner_id", feedOwnerId, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url addComment : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE addComment : "+jsonRes);

        return jsonRes;
    }

    public String deleteTask(String uId , String token, String feedId) throws Exception {

        url = AppConstants.BASE_URL + AppConstants.DELETE_TASK;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_id", uId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_token", token, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "task_id", feedId, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url deleteTask : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE deleteTask : "+jsonRes);

        return jsonRes;
    }

    public String connectTask(String uId , String token, String feedId) throws Exception {

        url = AppConstants.BASE_URL + AppConstants.CONNECT_TASK;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_id", uId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_token", token, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "task_id", feedId, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url connectTask : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE connectTask : "+jsonRes);

        return jsonRes;
    }

    public String cancelTask(String uId , String token, String feedId) throws Exception {

        url = AppConstants.BASE_URL + AppConstants.CANCEL_TASK;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_id", uId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_token", token, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "task_id", feedId, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url cancelTask : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE cancelTask : "+jsonRes);

        return jsonRes;
    }

    public String acceptTask(String uId , String token, String feedId) throws Exception {

        url = AppConstants.BASE_URL + AppConstants.ACCEPT_TASK;

        alPostParams.clear();

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_id", uId, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "user_token", token, null));

        alPostParams.add(new HttpNameValue(HttpNameValue.TEXT, "task_id", feedId, null));

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "Url acceptTask : "+url);

        String jsonRes = getJSONResponse("POST");

        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "RESPONSE acceptTask : "+jsonRes);

        return jsonRes;
    }
*/


}
