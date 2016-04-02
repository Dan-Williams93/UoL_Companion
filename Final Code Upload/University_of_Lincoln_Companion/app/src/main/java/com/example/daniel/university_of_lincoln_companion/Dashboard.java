package com.example.daniel.university_of_lincoln_companion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {


    //region GLOBAL VARIABLES
    private TextView tvUsername;
    private String strUsername, strTimetableURL, strBlackboardURL, strEmailURL, strPCURL;
    private boolean prefsLoggedIn = true;
    private ActiveStudent as = ActiveStudent.getInstance();
    //endregion

    public static final String PREFS_NAME = "MyPrefereces"; //TITLE OF SHARED PREFERENCES FILE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //region CASTING
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        //endregion

        //region SET ACTIVE USER DETAILS
        SharedPreferences myPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        as.setStudentNumber(myPrefs.getString("Username", null));
        as.setFirstName(myPrefs.getString("StudentFirstName", null));
        as.setLastName(myPrefs.getString("StudentLastName", null));
        as.setCourse(myPrefs.getString("StudentCourse", null));
        as.setYear(myPrefs.getString("StudentCourseYear", null));
        String strName = as.getFirstName() + " " + as.getLastName();
        as.setName(strName);
        //endregion

        //region INSTANTIATION
        strUsername = as.getStudentNumber();
        tvUsername.setText(strUsername);
        strTimetableURL = "http://timetables.lincoln.ac.uk/mytimetable/" + strUsername + ".htm";
        strPCURL = "http://findapc.lincoln.ac.uk/";
        strEmailURL = "https://adfs.lincoln.ac.uk/adfs/ls/?username=&wa=wsignin1.0&wtrealm=urn%3afederation%3aMicrosoftOnline&wctx=estsredirect%3d2%26estsrequest%3drQIIAbNSzygpKSi20tfPLy3Jyc_P1stPS8tMTjU2M9VLzs_Vyy9Kz0wBsaKYgQqKhLgE6g_VrdkaUunZ1s_6n7WG79ksRt6czLzk_Jw8vcRkvdLsVYxKeI3Uzy9P1L_AyLiJid3XyTM-ONjnBNPlz_y3mAT9i9I9U8KL3VJTUosSSzLz8x4x8YYWpxb55-VUhuRnp-ZNYubLyU_PzIsvLkqLT8vJLwcKAE0sSEwuiS_JTM5OLdnFrJJsmpZmaGFqrGuYapGia5KanKibZJxmrGtplmpskJRiapxoaXmBRWAXpxlhZ9oXpSbm5Nqi-A8A0";
        //endregion
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //region RETRIEVE USER DETAILS
        SharedPreferences myPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        as.setStudentNumber(myPrefs.getString("Username", null));
        as.setFirstName(myPrefs.getString("StudentFirstName", null));
        as.setLastName(myPrefs.getString("StudentLastName", null));
        as.setCourse(myPrefs.getString("StudentCourse", null));
        as.setYear(myPrefs.getString("StudentCourseYear", null));
        String strName = as.getFirstName() + " " + as.getLastName();
        as.setName(strName);

        strUsername = as.getStudentNumber();
        tvUsername.setText(strUsername);
        //endregion
    }

    public Boolean CheckConnection() {
        //CREATE A NEW CONNECTION MANAGER
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //GETS THE INFORMATION OF THE ACTIVE NETWORK
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //RETURNS TRUE IS THERE IS A CONNECTION OT A CONNECTING PROCESS
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void NoConnectionDialog() {
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

    public void get_Timetable(View view) {

        if (CheckConnection()) {
            //region START TIMETABLE ACTIVITY
            Intent timetable_intent = new Intent(Dashboard.this, WebViewing.class);
            timetable_intent.putExtra("URL", strTimetableURL);
            timetable_intent.putExtra("From", "Timetable");
            startActivity(timetable_intent);
            //endregion
        } else NoConnectionDialog();
    }

    public void goToBlackboard(View view) {

        if (CheckConnection()) {
            //region START BLACKBOARD ACTIVITY
            strBlackboardURL = "https://blackboard.lincoln.ac.uk";
            Intent blackboard_intent = new Intent(this, WebViewing.class);
            blackboard_intent.putExtra("URL", strBlackboardURL);
            blackboard_intent.putExtra("From", "Blackboard");
            startActivity(blackboard_intent);
            //endregion
        } else NoConnectionDialog();
    }

    public void goToEmail(View view) {

        if (CheckConnection()) {
            //region START EMAIL ACTIVITY
            Intent email_intent = new Intent(this, WebViewing.class);
            email_intent.putExtra("URL", strEmailURL);
            email_intent.putExtra("From", "Email");
            startActivity(email_intent);
            //endregion
        } else NoConnectionDialog();
    }

    public void goToStaff(View view) {

        if (CheckConnection()) {
            startActivity(new Intent(this, SearchStaff.class));
        } else NoConnectionDialog();
    }

    public void goToContact(View view) {
        startActivity(new Intent(this, ImportantNumbers.class));
    }

    public void goToSocial(View view) {
        if (CheckConnection()) {
            startActivity(new Intent(this, SocialPosts.class));
        } else NoConnectionDialog();
    }

    public void goToFindPC(View view){
        if (CheckConnection()) {
            //region START PC FINDER ACTIVITY
            Intent pc_intent = new Intent(this, WebViewing.class);
            pc_intent.putExtra("URL", strPCURL);
            pc_intent.putExtra("From", "PC");
            startActivity(pc_intent);
            //endregion
        } else NoConnectionDialog();
    }

    public void getDates(View view) {
        if (CheckConnection()) {
            startActivity(new Intent(this, ImportantDates.class));
        } else NoConnectionDialog();
    }

    public void goToMap(View view) {
        try {
            startActivity(new Intent(this, Maps.class));
        }catch (Exception e){
            Toast.makeText(Dashboard.this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void userLog_Out() {

        //region RESET USER DETAILS WITHIN DEVICE MEMORY
        strUsername = "";
        prefsLoggedIn = false;

        SharedPreferences MyPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = MyPreferences.edit();

        editor.putBoolean("LoggedIn", prefsLoggedIn);
        editor.putString("Username", null);
        editor.putString("StudentFirstName", null);
        editor.putString("StudentLastName", null);
        editor.putString("StudentCourse", null);
        editor.putString("StudentCourseYear", null);
        editor.commit();
        //endregion

        as.logOut();

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // INFLATES THE ACTION BAR MENU; ADDING THE ITEMS FROM THE XML IF PRESENT
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {

            userLog_Out();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
