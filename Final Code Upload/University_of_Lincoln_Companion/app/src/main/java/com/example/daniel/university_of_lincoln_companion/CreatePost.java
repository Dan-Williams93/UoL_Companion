package com.example.daniel.university_of_lincoln_companion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

    //region GLOBAL VARIABLES
    private TextView tvMessage;
    private EditText etStatus;
    private String strName, strCourse, strYear, strStudentNumber, strDate, strStatus, strResponse;
    private static final String PREFS_NAME = "MyPrefereces";
    private ActiveStudent As = ActiveStudent.getInstance();
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        //region CASTING
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        etStatus = (EditText) findViewById(R.id.etStatus);
        //endregion

        //region SET ACTIVE USER DETAILS
        SharedPreferences myPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        As.setStudentNumber(myPrefs.getString("Username", null));
        As.setFirstName(myPrefs.getString("StudentFirstName", null));
        As.setLastName(myPrefs.getString("StudentLastName", null));
        As.setCourse(myPrefs.getString("StudentCourse", null));
        As.setYear(myPrefs.getString("StudentCourseYear", null));
        String strGetName = As.getFirstName() + " " + As.getLastName();
        As.setName(strGetName);
        //endregion

        //region INSTANTIATION
        strName = As.getName();
        strCourse = As.getCourse();
        strYear = As.getYear();
        strStudentNumber = As.getStudentNumber();
        //endregion
    }

    public Boolean CheckConnection(){

        //CREATE A NEW CONNECTION MANAGER
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        //GETS THE INFORMATION OF THE ACTIVE NETWORK
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //RETURNS TRUE IS THERE IS A CONNECTION OT A CONNECTING PROCESS
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

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

    public void postStatus(View view){

        strStatus = etStatus.getText().toString();

        //CHECKS THERE IS INPUT MADE
        if (strStatus.length() >= 1) {

            //region GETS CURRENT DATE
            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            strDate = currentDate.format(date);
            //endregion

            if (CheckConnection()) {
                new createPosts().execute();
            } else NoConnectionDialog();
        }else{
            AlertDialog alertNoActiveUser = new AlertDialog.Builder(this).create();
            alertNoActiveUser.setTitle("Invalid Content!");
            alertNoActiveUser.setMessage("You can not post a blank status");

            alertNoActiveUser.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();   //CLOSES THE DIALOG
                }
            });
            alertNoActiveUser.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                //region START DASHBOARD ACTIVITY AND CLEAR ACTIVITY STACK
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

        //region RETRIEVE USER DETAILS
        SharedPreferences myPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        As.setStudentNumber(myPrefs.getString("Username", null));
        As.setFirstName(myPrefs.getString("StudentFirstName", null));
        As.setLastName(myPrefs.getString("StudentLastName", null));
        As.setCourse(myPrefs.getString("StudentCourse", null));
        As.setYear(myPrefs.getString("StudentCourseYear", null));
        String strGetName = As.getFirstName() + " " + As.getLastName();
        As.setName(strGetName);

        strName = As.getName();
        strCourse = As.getCourse();
        strYear = As.getYear();
        strStudentNumber = As.getStudentNumber();
        //endregion
    }

    private class createPosts extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String strLoginURL = "http://82.14.95.59/uol_companion/poststatus.php";

            if (CheckConnection()) {
                try {

                    //region CREATE CONNECTION TO SERVER, SEND REQUEST, AND RETRIEVE RESPONSE
                    URL loginURL = new URL(strLoginURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    //region CREATE AND ENCODE DATA STRING TO SEND TO PHP
                    String data = URLEncoder.encode("poster_name", "UTF-8") + "=" + URLEncoder.encode(strName, "UTF-8") + "&" +
                            URLEncoder.encode("post", "UTF-8") + "=" + URLEncoder.encode(strStatus, "UTF-8") + "&" +
                            URLEncoder.encode("post_date", "UTF-8") + "=" + URLEncoder.encode(strDate, "UTF-8") + "&" +
                            URLEncoder.encode("post_course", "UTF-8") + "=" + URLEncoder.encode(strCourse, "UTF-8");
                    //endregion

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
                    //endregion

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

            //CHECK POSTS WAS SUCCESSFUL
            if (strResponse.equals("1")) {
                Toast.makeText(CreatePost.this, "Successfully Posted", Toast.LENGTH_SHORT).show();

                //TRANSITION TO POSTS ACTIVITY
                startActivity(new Intent(CreatePost.this, SocialPosts.class));
                ((Activity)CreatePost.this).finish();
            }else{
                Toast.makeText(CreatePost.this, "Unable to post", Toast.LENGTH_SHORT).show();
            }

            strStatus = null;
        }
    }
}
