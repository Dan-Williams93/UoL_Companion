package com.example.daniel.university_of_lincoln_companion;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ImportantNumbers extends AppCompatActivity {

    //region GLOBAL VARIABLES
    private TextView tvGeneralEnquiresNum, tvSecurityNum;
    private TextView tvAccommodationServicesNum, tvLibraryNum, tvStudentSupportNum, tvStudentsUnionNum;
    private TextView tvAccommodationServicesAdd, tvLibraryAdd, tvStudentSupportAdd, tvStudentsUnionAdd;
    private TextView tvEstatesNum, tvFinanceNum, tvHRNum, tvICtServicesNum, tvPostRoomNum;
    private TextView tvEstatesAdd, tvFinanceAdd, tvHRAdd, tvICtServicesAdd;
    private String strPhoneNumber;
    private String strEmailAddress;
    private ActiveStudent As = ActiveStudent.getInstance();
    private static final String PREFS_NAME = "MyPrefereces";
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_numbers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        //region CASTING TELEPHONE NUMBER TEXT VIEWS
        tvGeneralEnquiresNum = (TextView) findViewById(R.id.tvGeneralEnquiriesNum);
        tvSecurityNum = (TextView) findViewById(R.id.tvSecurityNum);

        tvAccommodationServicesNum = (TextView) findViewById(R.id.tvAccommodationNum);
        tvLibraryNum = (TextView) findViewById(R.id.tvLibraryNum);
        tvStudentSupportNum = (TextView) findViewById(R.id.tvStudentSupportNum);
        tvStudentsUnionNum = (TextView) findViewById(R.id.tvStudentsUnionNum);

        tvEstatesNum = (TextView) findViewById(R.id.tvEstatesNum);
        tvFinanceNum = (TextView) findViewById(R.id.tvFinanceNum);
        tvHRNum = (TextView) findViewById(R.id.tvHRNum);
        tvICtServicesNum = (TextView) findViewById(R.id.tvICTServicesNum);
        tvPostRoomNum = (TextView) findViewById(R.id.tvPostRoomNum);
        //endregion

        //region CASTING EMAIL ADDRESS TEXT VIEWS
        tvAccommodationServicesAdd = (TextView)findViewById(R.id.tvAccommodationAdd);
        tvLibraryAdd = (TextView)findViewById(R.id.tvLibraryAdd);
        tvStudentSupportAdd = (TextView)findViewById(R.id.tvStudentSupportAdd);
        tvStudentsUnionAdd = (TextView)findViewById(R.id.tvStudentsUnionAdd);

        tvEstatesAdd = (TextView)findViewById(R.id.tvEstatesAdd);
        tvFinanceAdd = (TextView)findViewById(R.id.tvFinanceAdd);
        tvHRAdd = (TextView)findViewById(R.id.tvHRAdd);
        tvICtServicesAdd = (TextView)findViewById(R.id.tvICTServicesAdd);
        //endregion
    }

    //region CALL METHODS
    public void callGeneralEnquires(View view) {
        strPhoneNumber = tvGeneralEnquiresNum.getText().toString();
        callContact(strPhoneNumber);
    }

    public void callSecurity(View view) {
        strPhoneNumber = tvSecurityNum.getText().toString();
        callContact(strPhoneNumber);
    }

    public void callAccommodationServices(View view) {
        strPhoneNumber = tvAccommodationServicesNum.getText().toString();
        callContact(strPhoneNumber);
    }

    public void callTheLibrary(View view) {
        strPhoneNumber = tvLibraryNum.getText().toString();
        callContact(strPhoneNumber);
    }

    public void callStudentSupport(View view) {
        strPhoneNumber = tvStudentSupportNum.getText().toString();
        callContact(strPhoneNumber);
    }

    public void callStudentsUnion(View view) {
        strPhoneNumber = tvStudentsUnionNum.getText().toString();
        callContact(strPhoneNumber);
    }

    public void callEstates(View view) {
        strPhoneNumber = tvEstatesNum.getText().toString();
        callContact(strPhoneNumber);
    }

    public void callFinance(View view) {
        strPhoneNumber = tvFinanceNum.getText().toString();
        callContact(strPhoneNumber);
    }

    public void callHR(View view) {
        strPhoneNumber = tvHRNum.getText().toString();
        callContact(strPhoneNumber);
    }

    public void callICTServces(View view) {
        strPhoneNumber = tvICtServicesNum.getText().toString();
        callContact(strPhoneNumber);
    }

    public void callPostRoom(View view) {
        strPhoneNumber = tvPostRoomNum.getText().toString();
        callContact(strPhoneNumber);
    }

    public void callContact(String strNumber) {
        strNumber = strNumber.replaceAll(" ", "");

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + strNumber));

        //int isPermission = getContext().checkCallingPermission("android.permission.CALL_PHONE");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);

    }
    //endregion

    //region EMAIL METHODS
    public void mailAccommodationServices(View view){
        strEmailAddress = tvAccommodationServicesAdd.getText().toString();
        mailAddress(strEmailAddress);
    }

    public void mailLibrary(View view){
        strEmailAddress = tvLibraryAdd.getText().toString();
        mailAddress(strEmailAddress);
    }

    public void mailStudentSupport(View view){
        strEmailAddress = tvStudentSupportAdd.getText().toString();
        mailAddress(strEmailAddress);
    }

    public void mailSU(View view){
        strEmailAddress = tvStudentsUnionAdd.getText().toString();
        mailAddress(strEmailAddress);
    }

    public void mailEstates(View view){
        strEmailAddress = tvEstatesAdd.getText().toString();
        mailAddress(strEmailAddress);
    }

    public void mailFinance(View view){
        strEmailAddress = tvFinanceAdd.getText().toString();
        mailAddress(strEmailAddress);
    }

    public void mailHR(View view){
        strEmailAddress = tvHRAdd.getText().toString();
        mailAddress(strEmailAddress);
    }

    public void mailHelpDesk(View view){
        strEmailAddress = tvICtServicesAdd.getText().toString();
        mailAddress(strEmailAddress);
    }

    public void mailAddress(String strEmail){

        Intent emailIntent = new Intent(this, SendEmail.class);
        emailIntent.putExtra("Email_Address", strEmail);
        startActivity(emailIntent);
    }
    //endregion

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

        //region RETRIEVE USER DETAILS
        SharedPreferences myPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        As.setStudentNumber(myPrefs.getString("Username", null));
        As.setFirstName(myPrefs.getString("StudentFirstName", null));
        As.setLastName(myPrefs.getString("StudentLastName", null));
        As.setCourse(myPrefs.getString("StudentCourse", null));
        As.setYear(myPrefs.getString("StudentCourseYear", null));
        String strName = As.getFirstName() + " " + As.getLastName();
        As.setName(strName);
        //endregion
    }
}
