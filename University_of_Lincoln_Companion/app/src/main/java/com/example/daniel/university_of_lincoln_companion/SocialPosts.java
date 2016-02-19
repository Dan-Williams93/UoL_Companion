package com.example.daniel.university_of_lincoln_companion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

public class SocialPosts extends AppCompatActivity {

    private ArrayList<String> arlPostId = new ArrayList<String>();
    private ArrayList<String> arlPosts = new ArrayList<String>();
    private ArrayList<String> arlPosterNames = new ArrayList<String>();
    private ArrayList<String> arlPostDate = new ArrayList<String>();
    private ArrayList<String> arlNumberOfPostComments = new ArrayList<String>();
    private String strResponse, strQueryCode, strSelectedStatus, strSelectedStatusCode, strSelectedNumComments, strPoster;
    private ListView list;
    private TextView tvMessage;
    private ProgressBar progSocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_posts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = (ListView) findViewById(R.id.lstPosts);
        tvMessage = (TextView)findViewById(R.id.tvMessageSocial);
        progSocial = (ProgressBar)findViewById(R.id.progSocial);

        progSocial.setVisibility(View.INVISIBLE);
        tvMessage.setVisibility(View.GONE);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                strSelectedStatus = arlPosts.get(position);
                strSelectedStatusCode = arlPostId.get(position);
                strSelectedNumComments = arlNumberOfPostComments.get(position);
                strPoster = arlPosterNames.get(position);

                //if(!strSelectedNumComments.equals("0")) {

                    Intent statusIntent = new Intent(SocialPosts.this, SocialPostComments.class);
                    statusIntent.putExtra("statusID", strSelectedStatusCode);
                    statusIntent.putExtra("status", strSelectedStatus);
                    statusIntent.putExtra("statusNumComments", strSelectedNumComments);
                    statusIntent.putExtra("statusPoster", strPoster);
                    startActivity(statusIntent);
                //}else{
                    //SHOW DIALOG SAYING NO COMMENTS
                //}
            }
        });

        if (CheckConnection()) {
            new getPosts().execute();
        }else NoConnectionDialog();
    }

    public Boolean CheckConnection(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); //CREATE A NEW CONNECTION MANAGER
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();  //GETS THE INFORMATION OF THE ACTIVE NETWORK

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting(); //RETURNS TRUE IS THERE IS A CONNECTION OT A CONNECTING PROCESS
    }

    //SHOWS NO CONNECTION DIALOG
    public void NoConnectionDialog(){
        AlertDialog alertNoActiveUser = new AlertDialog.Builder(this).create();
        alertNoActiveUser.setTitle("No Connection");
        alertNoActiveUser.setMessage("Please connect to the internet and try again");

        alertNoActiveUser.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();   //CLOSES THE DIALOG
            }
        });
        alertNoActiveUser.show();
    }

    public void goToWritePost(View view){
        startActivity(new Intent(this, CreatePost.class));
        this.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        arlPostId.clear();
        arlPosts.clear();
        arlPosterNames.clear();
        arlPostDate.clear();
        arlNumberOfPostComments.clear();

        if (CheckConnection()) {
            new getPosts().execute();
        }else NoConnectionDialog();
    }

    private class getPosts extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progSocial.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            String strLoginURL = "http://82.14.95.59/uol_companion/getposts.php";

            if (CheckConnection()) {
                try {
                    URL loginURL = new URL(strLoginURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data = URLEncoder.encode("post_course", "UTF-8") + "=" + URLEncoder.encode("computer science", "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    strResponse = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        strResponse += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = new JSONObject(strResponse);

                    strQueryCode = jsonObject.getString("status");

                    if (strQueryCode.equals("1")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("posts");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            //CREATES A JSON OBJECT FOR THE ITEM AT THE CURRENT POSITION
                            JSONObject json_message = jsonArray.getJSONObject(i);

                            if (json_message != null) {

                                arlPostId.add(json_message.getString("status_id"));
                                arlPosts.add(json_message.getString("status"));
                                arlPosterNames.add(json_message.getString("poster_name"));
                                arlPostDate.add(json_message.getString("post_date"));
                                arlNumberOfPostComments.add(json_message.getString("num_comments"));
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else NoConnectionDialog();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (strQueryCode.equals("1")) {
                Collections.reverse(arlPostId);
                Collections.reverse(arlPosts);
                Collections.reverse(arlPosterNames);
                Collections.reverse(arlPostDate);
                Collections.reverse(arlNumberOfPostComments);

                //region PASS DATA TO CUSTOM LIST VIEW ADAPTER
                custom_list_adapter_socialposts custom_listView_adapter = new custom_list_adapter_socialposts(SocialPosts.this, arlPosts, arlPosterNames, arlPostDate, arlNumberOfPostComments);
                list.setAdapter(custom_listView_adapter);
            }else {

                tvMessage.setVisibility(View.VISIBLE);
            }

            progSocial.setVisibility(View.GONE);

        }
    }

}

//##########################################################################################
//# RETRIEVE THE POST ID, POSTER NAME, POST, POST DATE, NUM OF POST COMMENTS
//# PUT THE RESULTS IN THE RELEVANT ARRAY LISTS
//##########################################################################################