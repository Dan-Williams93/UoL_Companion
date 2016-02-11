package com.example.daniel.university_of_lincoln_companion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

    private EditText etSearch;
    private Spinner spSearchField;
    private ListView lstStaff;

    private ArrayList<String> arStaffName = new ArrayList<String>();
    private ArrayList<String> arDescription = new ArrayList<String>();
    private ArrayList<String> arDepartment = new ArrayList<String>();
    private ArrayList<String> arPhoneNumber = new ArrayList<String>();
    private ArrayList<String> arEmail = new ArrayList<String>();
    private String strPostResponse, strSearchField, strSerachCriteria, strQueryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_staff);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etSearch = (EditText)findViewById(R.id.etSearch);
        spSearchField = (Spinner)findViewById(R.id.staffSpinner);
        lstStaff = (ListView)findViewById(R.id.lstStaff);

    }

    public void searchStaff(View view){

        arStaffName.clear();
        arDepartment.clear();
        arEmail.clear();
        arPhoneNumber.clear();
        arDescription.clear();

        strSerachCriteria = etSearch.getText().toString();
        strSearchField = spSearchField.getSelectedItem().toString();

        if (strSerachCriteria.length() >= 1) {
            new getStaffDetails().execute();
        }else{
            Toast.makeText(SearchStaff.this, "Invalid Entry", Toast.LENGTH_SHORT).show();
        }

    }

    private class getStaffDetails extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            String strLoginURL = "http://82.14.95.59/uol_companion/searchstaff.php";

            try {
                URL loginURL = new URL(strLoginURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("search_input", "UTF-8") + "=" + URLEncoder.encode(strSerachCriteria, "UTF-8") + "&" +
                        URLEncoder.encode("search_field", "UTF-8") + "=" + URLEncoder.encode(strSearchField, "UTF-8");

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

                            arStaffName.add(json_message.getString("name"));
                            arDescription.add(json_message.getString("description"));
                            arDepartment.add(json_message.getString("department"));
                            arPhoneNumber.add(json_message.getString("phone_number"));
                            arEmail.add(json_message.getString("email"));
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

            custom_list_adapter_staff custom_adapter = new custom_list_adapter_staff(SearchStaff.this, arStaffName, arDepartment, arEmail, arPhoneNumber);
            lstStaff.setAdapter(custom_adapter);
        }

    }

}
