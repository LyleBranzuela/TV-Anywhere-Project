package com.visiostudios.tvanywhere;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

public class StreamActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        Intent intent = getIntent();
        String playUrl = intent.getStringExtra("playUrl");

        WebView streamWebView = (WebView) findViewById(R.id.streamWebView);
        WebSettings webSettings = streamWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        streamWebView.loadUrl(playUrl);

    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
