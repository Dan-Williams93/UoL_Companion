package com.example.daniel.university_of_lincoln_companion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class SendEmail extends AppCompatActivity {

    private String strEmailAddress, strSubject, strMessage;
    private EditText etTo, etSubject, etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etTo = (EditText)findViewById(R.id.etTo);
        etSubject = (EditText)findViewById(R.id.etSubject);
        etMessage = (EditText)findViewById(R.id.etMessage);

        strEmailAddress = getIntent().getExtras().getString("Email_Address");

        etTo.setText(strEmailAddress);

    }

    public void sendEmail(View view){

        strSubject = etSubject.getText().toString();
        strMessage = etMessage.getText().toString();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{strEmailAddress});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, strSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, strMessage);
        startActivity(Intent.createChooser(emailIntent, "Send email"));
    }
}
