package com.example.daniel.university_of_lincoln_companion;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View;
import android.widget.TextView;

public class Dashboard extends Activity {

    private TextView tvUsername;
    private String strUsername, strTimetableURL, strBlackboardURL, strEmailURL;
    private boolean prefsLoggedIn = true;
    private ActiveStudent as = ActiveStudent.getInstance();

    public static final String PREFS_NAME = "MyPrefereces"; //TITLE OF SHARED PREFERENCES FILE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        tvUsername = (TextView)findViewById(R.id.tvUsername);

        strUsername = getIntent().getExtras().getString("username");
        tvUsername.setText(strUsername);


        strTimetableURL = "http://timetables.lincoln.ac.uk/mytimetable/" + strUsername + ".htm";
        strBlackboardURL = "https://blackboard.lincoln.ac.uk/webapps/login/?action=relogin";
        strEmailURL = "https://adfs.lincoln.ac.uk/adfs/ls/?username=&wa=wsignin1.0&wtrealm=urn%3afederation%3aMicrosoftOnline&wctx=estsredirect%3d2%26estsrequest%3drQIIAbNSzygpKSi20tfPLy3Jyc_P1stPS8tMTjU2M9VLzs_Vyy9Kz0wBsaKYgQqKhLgE6g_VrdkaUunZ1s_6n7WG79ksRt6czLzk_Jw8vcRkvdLsVYxKeI3Uzy9P1L_AyLiJid3XyTM-ONjnBNPlz_y3mAT9i9I9U8KL3VJTUosSSzLz8x4x8YYWpxb55-VUhuRnp-ZNYubLyU_PzIsvLkqLT8vJLwcKAE0sSEwuiS_JTM5OLdnFrJJsmpZmaGFqrGuYapGia5KanKibZJxmrGtplmpskJRiapxoaXmBRWAXpxlhZ9oXpSbm5Nqi-A8A0";
    }

    public Boolean CheckConnection(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); //CREATE A NEW CONNECTION MANAGER
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();  //GETS THE INFORMATION OF THE ACTIVE NETWORK

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting(); //RETURNS TRUE IS THERE IS A CONNECTION OT A CONNECTING PROCESS
    }

    //SHOWS NO CONNECTION DIALOG
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

    public void get_Timetable(View view){

        if (CheckConnection()) {
            Intent timetable_intent = new Intent(Dashboard.this, WebViewing.class);
            //timetable_intent.putExtra("Sender", "TimeTable");
            timetable_intent.putExtra("URL", strTimetableURL);
            startActivity(timetable_intent);
        }else NoConnectionDialog();
    }

    public void goToBlackboard(View view){

        if (CheckConnection()) {
            Intent blackboard_intent = new Intent(this, WebViewing.class);
            //blackboard_intent.putExtra("Sender", "Blackboard");
            blackboard_intent.putExtra("URL", strBlackboardURL);
            startActivity(blackboard_intent);
        }else NoConnectionDialog();
    }

    public void goToEmail(View view){

        if (CheckConnection()) {
            Intent email_intent = new Intent(this, WebViewing.class);
            //email_intent.putExtra("Sender", "Email");
            email_intent.putExtra("URL", strEmailURL);
            startActivity(email_intent);
        }else NoConnectionDialog();
    }

    public void goToStaff(View view){

        if (CheckConnection()) {
            startActivity(new Intent(this, SearchStaff.class));
        }else NoConnectionDialog();
    }

    public void goToContact(View view){
        startActivity(new Intent(this, ImportantNumbers.class));
    }

    public void goToSocial(View view){
        if (CheckConnection()) {
            startActivity(new Intent(this, SocialPosts.class));
        }else NoConnectionDialog();
    }

    public void getDates(View view){
        if(CheckConnection()) {
            startActivity(new Intent(this, ImportantDates.class));
        }else NoConnectionDialog();
    }

    public void userLog_Out(View view) {

        strUsername = "";
        prefsLoggedIn = false;

        SharedPreferences MyPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = MyPreferences.edit();

        //ADD TO SHAREDPREFERENCES WITH
        editor.putBoolean("LoggedIn", prefsLoggedIn);
        editor.putString("Username", null);
        editor.putString("StudentFirstName", null);
        editor.putString("StudentLastName", null);
        editor.putString("StudentCourse", null);
        editor.putString("StudentCourseYear", null);
        editor.commit();

        as.logOut();

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
