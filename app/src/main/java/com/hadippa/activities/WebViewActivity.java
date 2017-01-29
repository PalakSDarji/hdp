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

        try {
            webView = (WebView) findViewById(R.id.webview);
            Map<String, String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put("Authorization", "Bearer " + getIntent().getExtras().getString("code"));
            final String postData = getIntent().getExtras().getString("data");


            webView.setWebViewClient( new WebViewClient()
            {
                public WebResourceResponse shouldInterceptRequest(WebView _view, String _url)
                {
                    URL url;
                    try {
                        url = new URL(_url);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        return null;
                    }

                    HttpURLConnection urlConnection;
                    try {
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestProperty("Authorization", "Bearer " + getIntent().getExtras().getString("code"));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }

                    WebResourceResponse response = null;
                    try {

                        response = new WebResourceResponse( urlConnection.getContentType(),
                                urlConnection.getContentEncoding(),
                                urlConnection.getInputStream() );
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        webView.postUrl(getIntent().getExtras().getString("url"), EncodingUtils.getBytes(postData, "utf-8"));
                        urlConnection.disconnect();
                    }

                    return response;
                }
            });

        }catch (Exception  e){

        }
      /*  Request request = new Request.Builder().url("https://www.meraevents.com/web/api/v1/booking").header("Authorization", "Bearer 3e23e72f9fd842e0ee87fd842c968aeadd3422ad").build();
       // webView.loadUrl(getIntent().getExtras().getString("url"));

        WebViewClient webViewClient = new WebViewClient();
        webView.setWebViewClient(webViewClient);*/
        }
    }



