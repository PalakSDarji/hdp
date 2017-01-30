package com.hadippa.activities;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hadippa.R;

import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Authorization", "Bearer " + getIntent().getExtras().getString("code"));
        try {
            webView = (WebView) findViewById(R.id.webview);


            final String postData = getIntent().getExtras().getString("data");

            webView.setWebViewClient(new WebViewClient() {
                /* On Android 1.1 shouldOverrideUrlLoading() will be called every time the user clicks a link,
                 * but on Android 1.5 it will be called for every page load, even if it was caused by calling loadUrl()! */
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url)
                {
        /* intercept all page load attempts and load yahoo.com instead */
                    String myAlternativeURL = "http://yahoo.com";
                    if (!url.equals(myAlternativeURL)) {
                        view.loadUrl(myAlternativeURL);
                        return true;
                    }

                    return false;
                }
            });


        }catch (Exception  e){

        }
        webView.loadUrl(getIntent().getExtras().getString("url"),extraHeaders);

        }
    }



