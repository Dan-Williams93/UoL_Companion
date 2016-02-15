package com.example.daniel.university_of_lincoln_companion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.jar.Manifest;

/**
 * Created by Daniel on 30/01/2016.
 */
public class custom_list_adapter_staff extends ArrayAdapter {

    private Activity context;
    private ArrayList<String> arName;
    private ArrayList<String> arDepartment;
    private ArrayList<String> arEmail;
    private ArrayList<String> arPhone;
    private String strPhoneNumber, strEmailAddress,  strPermission = "android.permission.CALL_PHONE";


    public custom_list_adapter_staff(Activity context, ArrayList<String> arNameF, ArrayList<String> arDepartmentF, ArrayList<String> arEmailF, ArrayList<String> arPhoneF) {
        super(context, R.layout.custom_listrow_staffsearch, arNameF);

        this.context = context;
        this.arName = arNameF;
        this.arDepartment = arDepartmentF;
        this.arEmail = arEmailF;
        this.arPhone = arPhoneF;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View viewRow = inflater.inflate(R.layout.custom_listrow_staffsearch, null, true);       //INFLATES THE LIST PLACING THE COMPONENTS OF THE STATED XML FILE IN EACH ROW

        //CASTS ROW COMPONENTS
        TextView tvStaffName = (TextView) viewRow.findViewById(R.id.tvStaffName);
        TextView tvDepartment = (TextView) viewRow.findViewById(R.id.tvDepartment);
        TextView tvEmail = (TextView) viewRow.findViewById(R.id.tvEmail);;
        TextView tvPhone = (TextView) viewRow.findViewById(R.id.tvPhone);

        ImageButton btnEmail = (ImageButton) viewRow.findViewById(R.id.btnEmail);
        ImageButton btnPhone = (ImageButton) viewRow.findViewById(R.id.btnRing);

        //SETS THE VALUES OF THE COMPONENTS TO THE VALUES OF THE ARRAY DATA PASSED IN AT THE POSITION OF THE CURRENT ROW
        tvStaffName.setText(arName.get(position));
        tvDepartment.setText(arDepartment.get(position));
        tvEmail.setText(arEmail.get(position));
        tvPhone.setText(arPhone.get(position));

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strPhoneNumber = arPhone.get(position);

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + strPhoneNumber));

                //int isPermission = getContext().checkCallingPermission("android.permission.CALL_PHONE");

                context.startActivity(callIntent);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmailAddress = arEmail.get(position);

                Intent emailIntent = new Intent(context, SendEmail.class );
                emailIntent.putExtra("Email_Address", strEmailAddress);
                context.startActivity(emailIntent);
            }
        });
        return viewRow;
    }
}
