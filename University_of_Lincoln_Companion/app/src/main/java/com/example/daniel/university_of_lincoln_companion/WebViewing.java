package com.example.daniel.university_of_lincoln_companion;

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
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.net.MalformedURLException;
import java.net.URL;

public class WebViewing extends Activity {

    private WebView myWebView;
    private ProgressBar proWebLoading;
    private String strURL;
    private ActiveStudent activeStudent = ActiveStudent.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viewing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

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

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
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
        startActivity(dashIntent);
        this.finish();
    }
}



/*
get from intent where the intent is sent from
if from blackboard see if there is a url stored
if there is load it
if not load original
 */




//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                request.allowScanningByMediaScanner();
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download");
//                DownloadManager dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//                dm.enqueue(request);