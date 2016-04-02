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

    //region GLOBAL VARIABLES
    private WebView myWebView;
    private ProgressBar proWebLoading;
    private String strURL, strFrom;
    private ActiveStudent activeStudent = ActiveStudent.getInstance();
    private static final String PREFS_NAME = "MyPrefereces";
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viewing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        strFrom = getIntent().getExtras().getString("From");

        //region SET SPECIFIC ACTIVITY TITLE
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
        //endregion

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        proWebLoading = (ProgressBar)findViewById(R.id.progWebLoading);
        strURL = String.valueOf(getIntent().getExtras().get("URL"));

        //region WEBVIEW CREATION
        myWebView = (WebView)findViewById(R.id.WebView1);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().getLoadWithOverviewMode();
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.getSettings().setBuiltInZoomControls(true);
        //endregion

        //region CREATE A CLIENT AND TRACK LOAD PROGRESSION
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
        //endregion

        myWebView.loadUrl(strURL);

        //region CREATE DOWNLOAD LISTENER, THAT STARTS DOWNLOAD MANAGER WHEN USR SELECTE DOWNLOADABLE FILE
        myWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                //CREATE COOKIE MANAGER TO STORE PAGE AUTHENTICATION
                String cookie = android.webkit.CookieManager.getInstance().getCookie(url);

                //region CREATE DOWNLOAD MANAGER, PASS DOWNLOAD URL AND SET IDENTIFICATION DATA
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.addRequestHeader("Cookie", cookie);
                request.allowScanningByMediaScanner();
                request.setDescription("Downloading from UoL");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                String fileName = URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url));

                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                DownloadManager dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                //endregion
            }
        });
        //endregion
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()){
            myWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                //region START DASHBOARD ACTIVITY CLEARING ACTIVITY STACK
                Intent i = new Intent(this, Dashboard.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                //endregion
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //region SET ACTIVE USER DATA
        SharedPreferences myPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        activeStudent.setStudentNumber(myPrefs.getString("Username", null));
        activeStudent.setFirstName(myPrefs.getString("StudentFirstName", null));
        activeStudent.setLastName(myPrefs.getString("StudentLastName", null));
        activeStudent.setCourse(myPrefs.getString("StudentCourse", null));
        activeStudent.setYear(myPrefs.getString("StudentCourseYear", null));
        String strGetName = activeStudent.getFirstName() + " " + activeStudent.getLastName();
        activeStudent.setName(strGetName);
        //endregion
    }
}
