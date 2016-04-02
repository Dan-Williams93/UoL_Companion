package com.example.daniel.university_of_lincoln_companion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendEmail extends AppCompatActivity {

    //region GLOBAL VARIABLES
    private String strEmailAddress, strSubject, strMessage;
    private EditText etTo, etSubject, etMessage;
    private static final String PREFS_NAME = "MyPrefereces";
    private ActiveStudent As = ActiveStudent.getInstance();
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        //region CASTING
        etTo = (EditText)findViewById(R.id.etTo);
        etSubject = (EditText)findViewById(R.id.etSubject);
        etMessage = (EditText)findViewById(R.id.etMessage);
        //endregion

        strEmailAddress = getIntent().getExtras().getString("Email_Address");

        etTo.setText(strEmailAddress);

    }

    public void sendEmail(View view){

        strSubject = etSubject.getText().toString();
        strMessage = etMessage.getText().toString();

        //CHECK SUBJECT AND MESSAGE ARE FILLED
        if (strSubject.length() >= 1) {
            if (strMessage.length() >= 1) {

                //region START EMAIL IMPLICIT INTENT ALLOWING USER TO SELECT PROVIDER
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{strEmailAddress});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, strSubject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, strMessage);
                startActivity(Intent.createChooser(emailIntent, "Send email"));
                //endregion

            }else {
                AlertDialog alertNoActiveUser = new AlertDialog.Builder(this).create();
                alertNoActiveUser.setTitle("Warning!");
                alertNoActiveUser.setMessage("You must fill all fields before sending an email");

                alertNoActiveUser.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();   //CLOSES THE DIALOG
                    }
                });
                alertNoActiveUser.show();
            }
        }else{
            AlertDialog alertNoActiveUser = new AlertDialog.Builder(this).create();
            alertNoActiveUser.setTitle("Warning!");
            alertNoActiveUser.setMessage("You must fill all fields before sending an email");

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
        As.setStudentNumber(myPrefs.getString("Username", null));
        As.setFirstName(myPrefs.getString("StudentFirstName", null));
        As.setLastName(myPrefs.getString("StudentLastName", null));
        As.setCourse(myPrefs.getString("StudentCourse", null));
        As.setYear(myPrefs.getString("StudentCourseYear", null));
        String strGetName = As.getFirstName() + " " + As.getLastName();
        As.setName(strGetName);
        //endregion
    }
}
