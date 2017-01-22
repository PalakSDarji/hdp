package com.hadippa.instagram;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;


public class Instagram {
    private Context mContext;

    private InstagramDialog mDialog;
    private InstagramAuthListener mListener;
    private InstagramSession mSession;

    private String mClientId;
    private String mClientSecret;
    private String mRedirectUri;

    /**
     * Instantiate new object of this class.
     *
     * @param context      Context
     * @param clientId     Client id
     * @param clientSecret Client secret
     * @param redirectUri  Redirect uri
     */
    public Instagram(Context context, final String clientId, String clientSecret, String redirectUri) {
        mContext = context;

        mClientId = clientId;
        mClientSecret = clientSecret;
        mRedirectUri = redirectUri;

        String authUrl = Cons.AUTH_URL + "client_id=" + mClientId + "&redirect_uri=" + mRedirectUri + "&response_type=code";

        mSession = new InstagramSession(context);

        mDialog = new InstagramDialog(context, authUrl, redirectUri, new InstagramDialog.InstagramDialogListener() {

            @Override
            public void onSuccess(String code) {
                Intent intent = new Intent("INSTA_GRAM");
                intent.putExtra("code",code);
                mContext.sendBroadcast(intent);
                // / retreiveAccessToken(code);
            }

            @Override
            public void onError(String error) {
                mListener.onError(error);
            }

            @Override
            public void onCancel() {
                mListener.onCancel();

            }
        });
    }

    /**
     * Authorize user.
     *
     * @param listener Auth listner
     */
    public void authorize(InstagramAuthListener listener) {
        mListener = listener;

        mDialog.show();
    }

    /**
     * Reset session.
     */
    public void resetSession() {
        mSession.reset();

        mDialog.clearCache();
    }

    /**
     * Get session.
     *
     * @return Instagram session.
     */
    public InstagramSession getSession() {
        return mSession;
    }

    /**
     * Retreive access token.
     *
     * @param code
     */


    public interface InstagramAuthListener {
        public abstract void onSuccess(InstagramUser user);

        public abstract void onError(String error);

        public abstract void onCancel();
    }
}