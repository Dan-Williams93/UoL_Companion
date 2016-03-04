package com.example.daniel.university_of_lincoln_companion;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;

public class WebViewing extends AppCompatActivity {

    private WebView myWebView;
    private ProgressBar proWebLoading;
    private String strURL, strFrom;
    private ActiveStudent activeStudent = ActiveStudent.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viewing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        strFrom = getIntent().getExtras().getString("From");

        if (strFrom.equals("Blackboard")){
            getSupportActionBar().setTitle("Blackboard");
        }else{
            if(strFrom.equals("Email")){
                getSupportActionBar().setTitle("Email");
            }else {
                if(strFrom.equals("Timetable")){
                    getSupportActionBar().setTitle("Timetable");
                }else {
                    if(strFrom.equals("PC")){
                        getSupportActionBar().setTitle("Find PC");
                    }
                }
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        proWebLoading = (ProgressBar)findViewById(R.id.progWebLoading);
        //need
        strURL = String.valueOf(getIntent().getExtras().get("URL"));

        //strURL = "http://timetables.lincoln.ac.uk/mytimetable/13458204.htm";//String.valueOf(getIntent().getExtras().get("URL"));

        myWebView = (WebView)findViewById(R.id.WebView1);
        //myWebView.setWebViewClient(new WebViewClient());
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().getLoadWithOverviewMode();
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.getSettings().setBuiltInZoomControls(true);

        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                proWebLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                proWebLoading.setVisibility(View.INVISIBLE);
            }
        });

        myWebView.loadUrl(strURL);
        //myWebView.loadUrl("http://www.closetcooking.com/2011/08/buffalo-chicken-grilled-cheese-sandwich.html");

        myWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                //Intent i = new Intent(Intent.ACTION_VIEW);
                //i.setData(Uri.parse(url));
                //startActivity(i);

                String cookie = android.webkit.CookieManager.getInstance().getCookie(url);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.addRequestHeader("Cookie", cookie);
                request.allowScanningByMediaScanner();
                request.setDescription("Downloading from UoL");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                String fileName = URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url));

                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                DownloadManager dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()){
            myWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    public void returnHome(View view){

        String strStudentNumber = activeStudent.getStudentNumber();


        Intent dashIntent = new Intent(WebViewing.this, Dashboard.class);
        dashIntent.putExtra("username", strStudentNumber);
        dashIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(dashIntent);
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent i = new Intent(this, Dashboard.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                request.allowScanningByMediaScanner();
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download");
//                DownloadManager dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//                dm.enqueue(request);