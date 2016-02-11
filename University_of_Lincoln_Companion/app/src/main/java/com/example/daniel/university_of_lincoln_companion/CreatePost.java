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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class CreatePost extends AppCompatActivity {

    private TextView tvMessage;
    private EditText etStatus;
    private ActiveStudent StudentDetails = ActiveStudent.getInstance();
    private String strName, strCourse, strYear, strStudentNumber, strDate, strStatus, strResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvMessage = (TextView) findViewById(R.id.tvMessage);
        etStatus = (EditText) findViewById(R.id.etStatus);

        strName = StudentDetails.getName();
        strCourse = StudentDetails.getCourse();
        strYear = StudentDetails.getYear();
        strStudentNumber = StudentDetails.getStudentNumber();
    }

    public void postStatus(View view){

        strStatus = etStatus.getText().toString();

        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        strDate = currentDate.format(date);

        new createPosts().execute();
    }

    private class createPosts extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {

            String strLoginURL = "http://82.14.95.59/uol_companion/poststatus.php";

            try {
                URL loginURL = new URL(strLoginURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("poster_name", "UTF-8") + "=" + URLEncoder.encode(strName, "UTF-8") + "&" +
                        URLEncoder.encode("post", "UTF-8") + "=" + URLEncoder.encode(strStatus, "UTF-8") + "&" +
                        URLEncoder.encode("post_date", "UTF-8") + "=" + URLEncoder.encode(strDate, "UTF-8") + "&" +
                        URLEncoder.encode("post_course", "UTF-8") + "=" + URLEncoder.encode(strCourse, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                strResponse = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null){
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
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (strResponse.equals("1")) {
                Toast.makeText(CreatePost.this, "Successfully Posted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreatePost.this, SocialPosts.class));
            }else{
                //change toast to dialog
                Toast.makeText(CreatePost.this, "Unable to post", Toast.LENGTH_SHORT).show();
            }

            strStatus = null;
        }
    }
}
