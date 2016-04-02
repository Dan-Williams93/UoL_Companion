package com.example.daniel.university_of_lincoln_companion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Splash extends Activity {

    //region GLOBAL VARIABLES
    private static final String PREFS_NAME = "MyPrefereces";    //HOLDS PREFERENCES FILE NAME  MyPrefereces
    private boolean bLoggedIn = false;                          //CREATE VARIABLE TO HOLD RETRIEVED PREFERENCE VALUE
    private String strUsername, strFirstName, strLastName, strCourse, strYear, strName;   //HOLDS RETRIEVED USERNAME FROM PREFERENCES
    private TextView tv;
    SharedPreferences MyPreferences;
    ActiveStudent activeStudent = ActiveStudent.getInstance();
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = (TextView)findViewById(R.id.tvSP);

        //region RETRIEVE DATA FROM SHAREDPREFERENCES
        MyPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        bLoggedIn = MyPreferences.getBoolean("LoggedIn", false);       //RETRIEVES VALUE FOR LOGGED IN USER
        strUsername = MyPreferences.getString("Username", null);
        //endregion

        String Display = String.valueOf(bLoggedIn) + " Username: " + strUsername;   //TESTING PURPOSES

        tv.setText(Display);
        tv.setVisibility(View.INVISIBLE);


        //TIMER THAT EXECUTED RUNNABLE WHEN SET TIME REACHED
        Handler delay = new Handler();
        delay.postDelayed(new Runnable() {
            @Override
            public void run() {

                //EXECUTED IF TRUE IS RETURNED FROM SHAREDPREFERENCES
                if (bLoggedIn == true) {

                    //region RETRIEVE ACTIVE USER DATA FROM DEVICE MEMORY AND SET SINGLETON CLASS
                    strUsername = MyPreferences.getString("Username", null);
                    strFirstName = MyPreferences.getString("StudentFirstName", null);
                    strLastName = MyPreferences.getString("StudentLastName", null);
                    strCourse = MyPreferences.getString("StudentCourse", null);
                    strYear = MyPreferences.getString("StudentCourseYear", null);
                    strName = strFirstName + " " + strLastName;

                    activeStudent.setStudentNumber(strUsername);
                    activeStudent.setFirstName(strFirstName);
                    activeStudent.setLastName(strLastName);
                    activeStudent.setCourse(strCourse);
                    activeStudent.setYear(strYear);
                    activeStudent.setName(strName);
                    //endregion

                    //region STARTS DASHBOARD ACTIVITY PASSING THE USERNAME
                    Intent dashboard_intent = new Intent(Splash.this, Dashboard.class);
                    dashboard_intent.putExtra("username", strUsername);
                    startActivity(dashboard_intent);
                    finish();
                    //endregion
                }
                else {

                    //region STARTS LOGIN ACTIVITY IF NOT ACTIVE USER IS FOUND
                    Intent login_intent = new Intent(Splash.this, Login.class);
                    startActivity(login_intent);
                    finish();
                    //endregion
                }
            }
        },1000); //SETS TIMER DELAY
    }
}
