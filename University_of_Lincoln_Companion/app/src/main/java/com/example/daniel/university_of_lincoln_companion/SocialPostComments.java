package com.example.daniel.university_of_lincoln_companion;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

    private TextView tvStatus;
    private EditText etComment;
    private ListView lstComments;
    private String strStatus, strStatusID, strQueryCode, strResponse, strComment, strDate, strName, strPostResponse, strNumComments;
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

        tvStatus = (TextView)findViewById(R.id.tvStatus);
        etComment = (EditText)findViewById(R.id.etComment);
        lstComments = (ListView)findViewById(R.id.lstComments);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        strStatusID = getIntent().getExtras().getString("statusID");
        strStatus = getIntent().getExtras().getString("status");
        strNumComments = getIntent().getExtras().getString("statusNumComments");

        strName = activeStudent.getName();
        tvStatus.setText(strStatusID + ": " + strStatus);

        new getComments().execute();
    }

    public void postComment(View view){

        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        strDate = currentDate.format(date);

        strComment = etComment.getText().toString();

        new postStatusComment().execute();

        arlComment.clear();
        arlPosterNames.clear();
        arlPostDate.clear();
    }

    private class getComments extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            String strURL = "http://82.14.95.59/uol_companion/getcomments.php";

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

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (strQueryCode.equals("1")) {
                //region PASS DATA TO CUSTOM LIST VIEW ADAPTER
                custom_list_adapter_socialcomments custom_listView_adapter = new custom_list_adapter_socialcomments(SocialPostComments.this, arlComment, arlPosterNames, arlPostDate);
                lstComments.setAdapter(custom_listView_adapter);
            }
        }
    }

    private class postStatusComment extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            String strLoginURL = "http://82.14.95.59/uol_companion/postcomment.php";

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
                        URLEncoder.encode("comment", "UTF-8") + "=" + URLEncoder.encode(strComment, "UTF-8")+ "&" +
                        URLEncoder.encode("numComments", "UTF-8") + "=" + URLEncoder.encode(strNumComments, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                strPostResponse = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null){
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

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (strPostResponse.equals("1")){
                etComment.setText("");
                Toast.makeText(SocialPostComments.this, "Comment Posted", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(SocialPostComments.this, Dashboard.class));
                new getComments().execute();
            }

            strPostResponse = "";
            strComment = "";
        }
    }
}
