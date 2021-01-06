package com.stepmobile.OCSS2Android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.stepmobile.OCSS2Android.utilities.UIUtils;
import com.stepmobile.OCSS2Android.utilities.WebViewUtil;

public class MainActivity extends AppCompatActivity {

    private UIUtils uiUtils;
    private WebViewUtil webViewUtil;
    private boolean intentHandled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup Theme
        //setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Helpers
        uiUtils = new UIUtils(this);
        webViewUtil = new WebViewUtil(this, uiUtils);

        // Setup App
        webViewUtil.setupWebView();
        uiUtils.changeRecentAppsIcon();

        // Check for Intents
        try {
            Intent i = getIntent();
            String intentAction = i.getAction();
            // Handle URLs opened in Browser
            if (!intentHandled && intentAction != null && intentAction.equals(Intent.ACTION_VIEW)){
                Uri intentUri = i.getData();
                String intentText = "";
                if (intentUri != null){
                    intentText = intentUri.toString();
                }
                // Load up the URL specified in the Intent
                if (!intentText.equals("")) {
                    intentHandled = true;
                    webViewUtil.loadIntentUrl(intentText);
                }
            } else {
                // Load up the Web App
                //webViewUtil.loadHome();
            }
        } catch (Exception e) {
            // Load up the Web App
            webViewUtil.loadHome();
        }
    }

    @Override
    protected void onPause() {
        webViewUtil.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        webViewUtil.onResume();
        // retrieve content from cache primarily if not connected
        webViewUtil.forceCacheIfOffline();
        super.onResume();
    }

    // Handle back-press in browser
    @Override
    public void onBackPressed() {
        if (!webViewUtil.goBack()) {
            super.onBackPressed();
        }
    }

}