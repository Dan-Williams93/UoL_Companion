package com.example.daniel.university_of_lincoln_companion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ImportantDates extends AppCompatActivity {

    private TextView tvDates, tvMessage;
    private ListView lstDates;
    private ProgressBar progDates;
    private String strPostResponse, strQueryCode, strCourse, strCourseYear;
    private ActiveStudent As = ActiveStudent.getInstance();

    private ArrayList<String> arDates = new ArrayList<String>();
    private ArrayList<String> arDescription = new ArrayList<String>();
    private ArrayList<String> arType = new ArrayList<String>();
    private ArrayList<String> arModule = new ArrayList<String>();
    private ArrayList<String> arModuleCode = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_dates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        tvDates = (TextView)findViewById(R.id.tvDates);
        tvMessage = (TextView)findViewById(R.id.tvMessageDates);
        progDates = (ProgressBar)findViewById(R.id.progDates);
        lstDates = (ListView)findViewById(R.id.lstDates);

        strCourse = As.getCourse();
        strCourseYear = As.getYear();

        tvDates.setText("Important Dates for: " + strCourse + " - Year " + strCourseYear);

        tvMessage.setVisibility(View.INVISIBLE);
        progDates.setVisibility(View.INVISIBLE);

        new getDates().execute();
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

    @Override
    protected void onRestart() {
        super.onRestart();

        arDates.clear();
        arDescription .clear();
        arType.clear();
        arModule.clear();
        arModuleCode.clear();

        strCourse = As.getCourse();
        strCourseYear = As.getYear();

        tvDates.setText("Important Dates for: " + strCourse + " - Year " + strCourseYear);

        tvMessage.setVisibility(View.INVISIBLE);
        progDates.setVisibility(View.INVISIBLE);

        new getDates().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent i = new Intent(this, Dashboard.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class getDates extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progDates.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            String strLoginURL = "http://82.14.95.59/uol_companion/getdates.php";

            if (CheckConnection()) {
                try {
                    URL loginURL = new URL(strLoginURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data = URLEncoder.encode("course", "UTF-8") + "=" + URLEncoder.encode(strCourse, "UTF-8") + "&" +
                            URLEncoder.encode("course_year", "UTF-8") + "=" + URLEncoder.encode(strCourseYear, "UTF-8");

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

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = new JSONObject(strPostResponse);

                    strQueryCode = jsonObject.getString("status");

                    if (strQueryCode.equals("1")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            //CREATES A JSON OBJECT FOR THE ITEM AT THE CURRENT POSITION
                            JSONObject json_message = jsonArray.getJSONObject(i);

                            if (json_message != null) {

                                arDates.add(json_message.getString("date"));
                                arDescription.add(json_message.getString("description"));
                                arType.add(json_message.getString("type"));
                                arModule.add(json_message.getString("module"));
                                arModuleCode.add(json_message.getString("module_code"));
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else NoConnectionDialog();


            //CHECK DATES TO SEE IF THEY HAVE ALREADY PASSED
            for(int i = 0; i < arDates.size(); i++){

                if (isCheckDate(arDates.get(i))){
                    arDates.remove(i);
                    arDescription.remove(i);
                    arType.remove(i);
                    arModule.remove(i);
                    arModuleCode.remove(i);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (strQueryCode.equals("1")) {
                custom_list_adapter_importantdates custom_adapter = new custom_list_adapter_importantdates(ImportantDates.this, arDates, arType, arModule, arModuleCode, arDescription);
                lstDates.setAdapter(custom_adapter);
            }else{
                tvMessage.setVisibility(View.VISIBLE);
            }

            progDates.setVisibility(View.GONE);
        }

        protected boolean isCheckDate(String strDate){

            Boolean isChecked = false;

            Calendar c = Calendar.getInstance();
            String strCurrentYear = String.valueOf(c.get(Calendar.YEAR)),
                    strCurrentMonth = String.valueOf(c.get(Calendar.MONTH) + 1),
                    strCurrentDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));

            String[] arDate = strDate.split("-");
            String strYear = arDate[0], strMonth = arDate[1], strDay = arDate[2];

            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateCurrentDate = simpleDateFormat.parse(strCurrentYear + "-" + strCurrentMonth + "-" + strCurrentDay);
                Date dateParsedDate = simpleDateFormat.parse(strYear + "-" + strMonth + "-" + strDay);


                if (dateParsedDate.before(dateCurrentDate)){
                    isChecked = true;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            //check date if before current return true
            return isChecked;
        }

    }
}
