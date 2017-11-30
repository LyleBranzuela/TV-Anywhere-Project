package com.visiostudios.tvanywhere;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class StreamActivity extends AppCompatActivity {
    private WebView streamWebView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        final Toolbar myToolbar = (Toolbar) findViewById(R.id.grayBar3);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_tv_anywhere);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);

        Intent intent = getIntent();
        String playUrl = intent.getStringExtra("playUrl");
        String channelName = intent.getStringExtra("channelName");

        ImageButton dropDown = (ImageButton) findViewById(R.id.dropDown) ;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        streamWebView = (WebView) findViewById(R.id.streamWebView);
        streamWebView.setWebViewClient(new CustomWebViewClient());

        // Hides the ToolBar from the Activity
        myToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myToolbar.setVisibility(View.INVISIBLE);
            }
        });
        // Shows the ToolBar from the Activity
        dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myToolbar.setVisibility(View.VISIBLE);
            }
        });
        streamWebView.getSettings().setDomStorageEnabled(true);
        streamWebView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        streamWebView.getSettings().setJavaScriptEnabled(true);
        streamWebView.loadUrl(playUrl);

        if (channelName.equals("channel3")) {
            getSupportActionBar().setTitle(" Three TV Livestream");
        } else if (channelName.equals("channel4")) {
            getSupportActionBar().setTitle(" Bravo TV Livestream");
        } else {
            getSupportActionBar().setTitle(" The Edge Livestream");
        }

    }

//  This allows for a splash screen
//  (and hide elements once the page loads)
    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {
            webview.setVisibility(streamWebView.INVISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressBar.setVisibility(View.GONE);
            view.setVisibility(streamWebView.VISIBLE);
            super.onPageFinished(view, url);
        }
    }


    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
