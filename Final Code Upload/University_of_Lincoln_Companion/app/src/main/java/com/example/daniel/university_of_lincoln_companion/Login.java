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
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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
import java.net.URL;
import java.net.URLEncoder;

public class Login extends Activity {

    //region GLOBAL VARIABLES
    private CheckBox autoLog;
    private Button btnLogin;
    private EditText etUsername, etPassword;
    private boolean prefsLoggedIn = true;   //HOLDS STATE OF CHECKBOX
    private String strUsername, strPassword, strQueryCode, strFirstName, strLastName, strCourse, strYearofStudy;
    private String intUserFound, response;
    private ActiveStudent StudentDetails = ActiveStudent.getInstance();
    public static final String PREFS_NAME = "MyPrefereces"; //TITLE OF SHARED PREFERENCES FILE
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //region CASTING
        autoLog = (CheckBox)findViewById(R.id.cbAutoLogin);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        //endregion

        //region FOCUS LISTENERS TO HIDE KEYBOARD
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!etPassword.isFocused()){
                    HideKeyboard();
                }
            }
        });

        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!etUsername.isFocused()){
                    HideKeyboard();
                }
            }
        });
        //endregion
    }

    public void HideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
    }

    //CALLED WHEN CHECK BOX CLICKED
    public void autoLogin_Checked(View view) {
        //EXECUTED WHEN CHECKBOX IS CHECKED
        if (autoLog.isChecked())
        {
            //SETS CHECKBOX STATE
            autoLog.setChecked(false);
            autoLog.toggle();
            prefsLoggedIn = false;  //SETS PREFERENCES VALUE
        }
        else
        {
            autoLog.setChecked(true);
            autoLog.toggle();
            prefsLoggedIn = true;
        }
    }

    public void user_Login(View view) {

        //region INSTANTIATION
        strUsername = etUsername.getText().toString();
        strPassword = etPassword.getText().toString();
        //endregion

        //CHECKS THERE IS A USERNAME AND PASSWORD ENTERED
        if (strUsername.length() >= 1 && strPassword.length() >= 1){
            if (CheckConnection()) {
                new loginProcess().execute();
            }else NoConnectionDialog();
        }else Toast.makeText(Login.this, "Invalid details!", Toast.LENGTH_SHORT).show();

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

    private class loginProcess extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String strLoginURL = "http://82.14.95.59/uol_companion/login.php";

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

                    String data = URLEncoder.encode("login_id", "UTF-8") + "=" + URLEncoder.encode(strUsername, "UTF-8") + "&" +
                            URLEncoder.encode("login_password", "UTF-8") + "=" + URLEncoder.encode(strPassword, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    response = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
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

                    //region PARSE RETURNED JSON
                    JSONObject jsonObject = new JSONObject(response);

                    strQueryCode = jsonObject.getString("result");

                    if (strQueryCode.equals("1")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("response");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            //CREATES A JSON OBJECT FOR THE ITEM AT THE CURRENT POSITION
                            JSONObject json_message = jsonArray.getJSONObject(i);

                            if (json_message != null) {
                                //region RETRIEVE DATA BY TAGS
                                strFirstName = json_message.getString("student_firstname");
                                strLastName = json_message.getString("student_lastname");
                                strCourse = json_message.getString("course");
                                strYearofStudy = json_message.getString("year");
                                //endregion
                            }
                        }
                    }
                    //endregion

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (strQueryCode.equals("0")){
                Toast.makeText(Login.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                etUsername.setText("");
                etPassword.setText("");
            }else if (strQueryCode.equals("1")){
                //Toast.makeText(Login.this, "Login Successful " + response, Toast.LENGTH_SHORT).show();

                if (autoLog.isChecked())
                {
                    //region RETRIEVE OBJECT OF SHARED PREFERENCES AND CREATE AN EDITOR
                    SharedPreferences MyPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor;
                    editor = MyPreferences.edit();

                    editor.putBoolean("LoggedIn", prefsLoggedIn);
                    editor.putString("Username", etUsername.getText().toString());
                    editor.putString("StudentFirstName", strFirstName);
                    editor.putString("StudentLastName", strLastName);
                    editor.putString("StudentCourse", strCourse);
                    editor.putString("StudentCourseYear", strYearofStudy);
                    editor.commit();
                    //endregion
                }

                //region SET USER DETAILS
                StudentDetails.setStudentNumber(strUsername);
                StudentDetails.setCourse(strCourse);
                StudentDetails.setYear(strYearofStudy);
                StudentDetails.setFirstName(strFirstName);
                StudentDetails.setLastName(strLastName);
                StudentDetails.setName(strFirstName + " " + strLastName);
                //endregion

                //region START DASHBOARD ACTIVITY PASSING USERNAME
                Intent login_Intent = new Intent(Login.this, Dashboard.class);
                login_Intent.putExtra("username", etUsername.getText().toString());
                startActivity(login_Intent);
                //endregion

                finish();
            }
        }
    }
}
