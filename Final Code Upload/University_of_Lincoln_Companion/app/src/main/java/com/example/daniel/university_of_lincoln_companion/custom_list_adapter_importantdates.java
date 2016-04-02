package com.example.daniel.university_of_lincoln_companion;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Daniel on 06/02/2016.
 * CUSTOM LIST ADAPTER FOR IMPORTANT DATES, CONTAINS CLICK LISTENER TO DEAL WITH LIST BUTTON CLICKS
 */
public class custom_list_adapter_importantdates extends ArrayAdapter {

    //region GLOBAL VARIABLES
    private Activity context;   //GETS CONTEXT OF CALLING ACTIVITY
    private ArrayList<String> arDates;
    private ArrayList<String> arType;
    private ArrayList<String> arModule;
    private ArrayList<String> arModuleCode;
    private ArrayList<String> arDescription;

    private String strDate, strDescription, strType, strModule, strModuleCode;
    //endregion

    //ADAPTER CONSTRUCTOR RECEIVING THE CALLING CONTEXT PLUS ARRAY LISTS CONTAINING THE REQUIRED DATA
    public custom_list_adapter_importantdates(Activity context, ArrayList<String> strDates, ArrayList<String> strType, ArrayList<String> strModule, ArrayList<String> strModuleCode, ArrayList<String> strDescription) {
        super(context, R.layout.custom_listrow_imporantdates, strDates);

        //region SETTING GLOBAL VARIABLES VALUES FROM PASSED DATA
        this.context = context;
        this.arDates = strDates;
        this.arType = strType;
        this.arModule = strModule;
        this.arModuleCode = strModuleCode;
        this.arDescription = strDescription;
        //endregion
    }

    //CREATES AND RETURNS LIST ROW BY ROW
    public View getView(final int position, View view, ViewGroup parent) {

        //INFLATES THE LIST PLACING THE COMPONENTS OF THE STATED XML FILE IN EACH ROW
        LayoutInflater inflater = context.getLayoutInflater();
        View viewRow = inflater.inflate(R.layout.custom_listrow_imporantdates, null, true);

        //region CASTS ROW COMPONENTS
        TextView tvDates = (TextView) viewRow.findViewById(R.id.tvDate);
        TextView tvType = (TextView) viewRow.findViewById(R.id.tvType);
        TextView tvModule = (TextView) viewRow.findViewById(R.id.tvModule);
        TextView tvModuleCode = (TextView) viewRow.findViewById(R.id.tvMCode);
        TextView tvDescription = (TextView) viewRow.findViewById(R.id.tvDescription);
        Button btnAddtoCalendar = (Button) viewRow.findViewById(R.id.btnAddtoCalendar);
        //endregion

        //region SETS THE VALUES OF THE COMPONENTS TO THE VALUES OF THE ARRAY DATA PASSED IN, AT THE POSITION OF THE CURRENT ROW
        tvDates.setText(arDates.get(position));
        tvType.setText(arType.get(position));
        tvModule.setText("Module: " + arModule.get(position));
        tvModuleCode.setText("Module Code: " + arModuleCode.get(position));
        tvDescription.setText("Description: " + arDescription.get(position));
        //endregion

        //region CLICK LISTENER FOR LISTVIEW BUTTONS
        btnAddtoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //region SET DATA FOR CALENDAR EVENT
                strDate = arDates.get(position);
                strDescription = arDescription.get(position);
                strType = arType.get(position);
                strModule = arModule.get(position);
                strModuleCode = arModuleCode.get(position);

                String strTitle = strModule + " - " + strModuleCode + " " + strType;

                //REFORMAT THE DATE FROM AMERICAN TO ENGLISH
                String[] date = strDate.split("-");
                GregorianCalendar calDate = new GregorianCalendar(Integer.parseInt(date[0]), (Integer.parseInt(date[1])-1), Integer.parseInt(date[2]));
                //endregion

                //region IMPLICIT INTENT TO DEVICE CALENDAR PASSING THE DETAILS OF THE DATE
                Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
                calendarIntent.setData(CalendarContract.Events.CONTENT_URI);
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.Events.TITLE, strTitle);
                calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, strDescription);
                context.startActivity(calendarIntent);
                //endregion


            }
        });
        //endregion

        return viewRow;
    }
}
