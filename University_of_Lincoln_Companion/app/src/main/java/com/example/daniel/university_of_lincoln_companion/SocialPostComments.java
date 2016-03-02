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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class SocialPostComments extends AppCompatActivity {

    private TextView tvStatus, tvS, tvP;
    private EditText etComment;
    private ListView lstComments;
    private LinearLayout layout;
    private String strStatus, strStatusID, strQueryCode, strResponse, strComment, strDate, strName, strPostResponse, strNumComments, strPoster;
    private ArrayList<String> arlCommentID = new ArrayList<String>();
    private ArrayList<String> arlPosterNames = new ArrayList<String>();
    private ArrayList<String> arlComment = new ArrayList<String>();
    private ArrayList<String> arlPostDate = new ArrayList<String>();
    private ActiveStudent activeStudent = ActiveStudent.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_post_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //tvStatus = (TextView)findViewById(R.id.tvStatus);
        etComment = (EditText)findViewById(R.id.etComment);
        lstComments = (ListView)findViewById(R.id.lstComments);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //hides on start


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        strStatusID = getIntent().getExtras().getString("statusID");
        strStatus = getIntent().getExtras().getString("status");
        strNumComments = getIntent().getExtras().getString("statusNumComments");
        strPoster = getIntent().getExtras().getString("statusPoster");

        strName = activeStudent.getName();
        //tvStatus.setText(strStatusID + ": " + strStatus);

        //CREATE HEADER FOR LISTVIEW
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.custom_list_header, lstComments, false);
        tvS = (TextView)header.findViewById(R.id.tvStatusComment);
        tvP = (TextView)header.findViewById(R.id.tvPoster);
        tvP.setText(strPoster);
        tvS.setText(/*strStatusID + ": " +*/ strStatus);
        lstComments.addHeaderView(header, null, false);

        etComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!etComment.isFocused()){
                    HideKeyboard();
                }
            }
        });

        if (CheckConnection()) {
            new getComments().execute();
        }else NoConnectionDialog();
    }

    public void HideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etComment.getWindowToken(), 0);
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

    public void postComment(View view) {

        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        strDate = currentDate.format(date);

        strComment = etComment.getText().toString();

        if (CheckConnection()) {
            if (!strComment.equals("") && !strComment.equals(" ") && !strComment.equals(null)) {
                new postStatusComment().execute();

                arlComment.clear();
                arlPosterNames.clear();
                arlPostDate.clear();
            }else{
                Toast.makeText(SocialPostComments.this, "Invalid Comment", Toast.LENGTH_SHORT).show();
            }
        }else NoConnectionDialog();
    }

    private class getComments extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            String strURL = "http://82.14.95.59/uol_companion/getcomments.php";

            if (CheckConnection()) {
                try {
                    URL url = new URL(strURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data = URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(strStatusID, "UTF-8");

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
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = new JSONObject(strResponse);

                    strQueryCode = jsonObject.getString("status");

                    if (strQueryCode.equals("1")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        for (int i = 0; i <= jsonArray.length(); i++) {

                            //CREATES A JSON OBJECT FOR THE ITEM AT THE CURRENT POSITION
                            JSONObject json_message = jsonArray.getJSONObject(i);

                            if (json_message != null) {
                                arlCommentID.add(json_message.getString("comment_id"));
                                arlPosterNames.add(json_message.getString("commenter_name"));
                                arlPostDate.add(json_message.getString("comment_date"));
                                arlComment.add(json_message.getString("comment"));
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
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (strQueryCode.equals("1")) {
                //region PASS DATA TO CUSTOM LIST VIEW ADAPTER

                custom_list_adapter_socialcomments custom_listView_adapter = new custom_list_adapter_socialcomments(SocialPostComments.this, arlComment, arlPosterNames, arlPostDate);
                lstComments.setAdapter(custom_listView_adapter);
            }else{
                //SHOW NO RESULTS DIALOG
                lstComments.setAdapter(null);
            }
        }
    }

    private class postStatusComment extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            String strLoginURL = "http://82.14.95.59/uol_companion/postcomment.php";

            if (CheckConnection()) {
                try {
                    URL loginURL = new URL(strLoginURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data = URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(strStatusID, "UTF-8") + "&" +
                            URLEncoder.encode("commenter_name", "UTF-8") + "=" + URLEncoder.encode(strName, "UTF-8") + "&" +
                            URLEncoder.encode("comment_date", "UTF-8") + "=" + URLEncoder.encode(strDate, "UTF-8") + "&" +
                            URLEncoder.encode("comment", "UTF-8") + "=" + URLEncoder.encode(strComment, "UTF-8") + "&" +
                            URLEncoder.encode("numComments", "UTF-8") + "=" + URLEncoder.encode(strNumComments, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    strPostResponse = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        strPostResponse += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else NoConnectionDialog();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (strPostResponse.equals("1")){
                etComment.setText("");
                Toast.makeText(SocialPostComments.this, "Comment Posted", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(SocialPostComments.this, Dashboard.class));

                if (CheckConnection()) {
                    new getComments().execute();
                }else NoConnectionDialog();
            }else {
                //SHOW DIALOG
            }

            strPostResponse = "";
            strComment = "";
        }
    }
}
