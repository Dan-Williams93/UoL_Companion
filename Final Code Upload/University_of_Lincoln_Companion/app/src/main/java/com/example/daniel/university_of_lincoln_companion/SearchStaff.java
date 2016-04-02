package com.example.daniel.university_of_lincoln_companion;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchStaff extends AppCompatActivity {

    //region GLOBAL VARIABLE
    private EditText etSearch;
    private Spinner spSearchField;
    private ListView lstStaff;
    private TextView tvMessage;
    private ProgressBar progStaff;
    private ArrayList<String> arStaffName = new ArrayList<String>();
    private ArrayList<String> arDescription = new ArrayList<String>();
    private ArrayList<String> arDepartment = new ArrayList<String>();
    private ArrayList<String> arPhoneNumber = new ArrayList<String>();
    private ArrayList<String> arEmail = new ArrayList<String>();
    private String strPostResponse, strSearchField, strSerachCriteria, strQueryCode;
    private static final String PREFS_NAME = "MyPrefereces";
    private ActiveStudent As = ActiveStudent.getInstance();
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_staff);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        //region CASTING
        etSearch = (EditText)findViewById(R.id.etSearch);
        spSearchField = (Spinner)findViewById(R.id.staffSpinner);
        lstStaff = (ListView)findViewById(R.id.lstStaff);
        tvMessage = (TextView)findViewById(R.id.tvMessageStaff);
        progStaff = (ProgressBar)findViewById(R.id.progStaff);
        //endregion

        progStaff.setVisibility(View.GONE);

        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!etSearch.hasFocus()){
                    HideKeyboard();
                }
            }
        });
    }

    public void HideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
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

    public void searchStaff(View view){

        HideKeyboard();

        //region CLEAR CURRENT DATA
        arStaffName.clear();
        arDepartment.clear();
        arEmail.clear();
        arPhoneNumber.clear();
        arDescription.clear();
        //endregion

        strSerachCriteria = etSearch.getText().toString();
        strSearchField = spSearchField.getSelectedItem().toString();

        //MAKES SURE THERE IS AN ENTRY AND THAT IT I LETTERS
        if (strSerachCriteria.length() >= 1 && !strSerachCriteria.matches(".*\\d.*")) {
            if (CheckConnection()) {
                new getStaffDetails().execute();
            }else NoConnectionDialog();
        }else{
            Toast.makeText(SearchStaff.this, "Invalid Entry", Toast.LENGTH_SHORT).show();
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

        //region SET CURRENT USER DATA
        SharedPreferences myPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        As.setStudentNumber(myPrefs.getString("Username", null));
        As.setFirstName(myPrefs.getString("StudentFirstName", null));
        As.setLastName(myPrefs.getString("StudentLastName", null));
        As.setCourse(myPrefs.getString("StudentCourse", null));
        As.setYear(myPrefs.getString("StudentCourseYear", null));
        String strGetName = As.getFirstName() + " " + As.getLastName();
        As.setName(strGetName);
        //endregion
    }

    private class getStaffDetails extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //SHOW PROGRESS BAR
            progStaff.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            String strLoginURL = "http://82.14.95.59/uol_companion/searchstaff.php";

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

                    //region CREATE AND ENCODE VARIABLES TO SEND TO PHP
                    String data = URLEncoder.encode("search_input", "UTF-8") + "=" + URLEncoder.encode(strSerachCriteria, "UTF-8") + "&" +
                            URLEncoder.encode("search_field", "UTF-8") + "=" + URLEncoder.encode(strSearchField, "UTF-8");
                    //endregion

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
                    //endregion

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    //region PARSE JSON RESPONSE
                    JSONObject jsonObject = new JSONObject(strPostResponse);

                    strQueryCode = jsonObject.getString("status");

                    if (strQueryCode.equals("1")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            //CREATES A JSON OBJECT FOR THE ITEM AT THE CURRENT POSITION
                            JSONObject json_message = jsonArray.getJSONObject(i);

                            if (json_message != null) {
                                //region RETRIEVE DATA BY TAGS
                                arStaffName.add(json_message.getString("name"));
                                arDescription.add(json_message.getString("description"));
                                arDepartment.add(json_message.getString("department"));
                                arPhoneNumber.add(json_message.getString("phone_number"));
                                arEmail.add(json_message.getString("email"));
                                //endregion
                            }
                        }
                    }
                    //endregion

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else NoConnectionDialog();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progStaff.setVisibility(View.GONE);

            if (strQueryCode.equals("1")) {

                tvMessage.setVisibility(View.GONE);

                //region SET CUSTOM LIST ADAPTER
                custom_list_adapter_staff custom_adapter = new custom_list_adapter_staff(SearchStaff.this, arStaffName, arDepartment, arEmail, arPhoneNumber);
                lstStaff.setAdapter(custom_adapter);
                //endregion

                lstStaff.setVisibility(View.VISIBLE);

            }else{
                lstStaff.setVisibility(View.GONE);

                tvMessage.setText("No Resuts Found");
                tvMessage.setVisibility(View.VISIBLE);
            }
        }

    }

}
