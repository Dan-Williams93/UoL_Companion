package com.example.daniel.university_of_lincoln_companion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;

public class ImportantDates extends AppCompatActivity {

    private TextView tvDates;
    private ListView lstDates;
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

        tvDates = (TextView)findViewById(R.id.tvDates);
        lstDates = (ListView)findViewById(R.id.lstDates);

        strCourse = As.getCourse();
        strCourseYear = As.getYear();

        tvDates.setText("Important Dates for: " + strCourse + " - Year " + strCourseYear);

        new getDates().execute();
    }

    private class getDates extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String strLoginURL = "http://82.14.95.59/uol_companion/getdates.php";

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

                while ((line = bufferedReader.readLine()) != null){
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

                if (strQueryCode.equals("1")){

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

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            custom_list_adapter_importantdates custom_adapter = new custom_list_adapter_importantdates(ImportantDates.this, arDates, arType, arModule, arModuleCode, arDescription );
            lstDates.setAdapter(custom_adapter);
        }

    }
}
