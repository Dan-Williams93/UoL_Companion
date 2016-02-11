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

import java.util.ArrayList;

/**
 * Created by Daniel on 06/02/2016.
 */
public class custom_list_adapter_importantdates extends ArrayAdapter {

    //region GLOBAL VARIABLES
    private Activity context;   //GETS CONTEXT OF CALLING ACTIVITY
    private ArrayList<String> arDates;
    private ArrayList<String> arType;
    private ArrayList<String> arModule;
    private ArrayList<String> arModuleCode;
    private ArrayList<String> arDescription;

    private String strDate, strStartTime, strFinishTime, strDescription, strType, strModule, strModuleCode;
    //endregion

    //ADAPTER CONSTRUCTOR RECEIVING THE CALLING CONTEXT PLUS ARRAY LISTS CONTAINING THE RECIPE NAMES AND RECIPE IMAGES
    public custom_list_adapter_importantdates(Activity context, ArrayList<String> strDates, ArrayList<String> strType,
                                              ArrayList<String> strModule, ArrayList<String> strModuleCode, ArrayList<String> strDescription) {
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

    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View viewRow = inflater.inflate(R.layout.custom_listrow_imporantdates, null, true);       //INFLATES THE LIST PLACING THE COMPONENTS OF THE STATED XML FILE IN EACH ROW

        //CASTS ROW COMPONENTS
        TextView tvDates = (TextView) viewRow.findViewById(R.id.tvDate);
        TextView tvType = (TextView) viewRow.findViewById(R.id.tvType);
        TextView tvModule = (TextView) viewRow.findViewById(R.id.tvModule);
        TextView tvModuleCode = (TextView) viewRow.findViewById(R.id.tvMCode);
        TextView tvDescription = (TextView) viewRow.findViewById(R.id.tvDescription);
        Button btnAddtoCalendar = (Button) viewRow.findViewById(R.id.btnAddtoCalendar);

        //SETS THE VALUES OF THE COMPONENTS TO THE VALUES OF THE ARRAY DATA PASSED IN AT THE POSITION OF THE CURRENT ROW
        tvDates.setText(arDates.get(position));
        tvType.setText(arType.get(position));
        tvModule.setText(arModule.get(position));
        tvModuleCode.setText(arModuleCode.get(position));
        tvDescription.setText(arDescription.get(position));

        btnAddtoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strDate = arDates.get(position);
                strDescription = arDescription.get(position);
                strType = arType.get(position);
                strModule = arModule.get(position);
                strModuleCode = arModuleCode.get(position);

                String strTitle = strModule + " - " + strModuleCode + " " + strType;

                //add to calendar
                Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
                calendarIntent.setData(CalendarContract.Events.CONTENT_URI);
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, strStartTime);
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, strFinishTime);
                calendarIntent.putExtra(CalendarContract.Events.TITLE, strTitle);
                calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, strDescription);
                context.startActivity(calendarIntent);
            }
        });

        return viewRow;
    }
}
