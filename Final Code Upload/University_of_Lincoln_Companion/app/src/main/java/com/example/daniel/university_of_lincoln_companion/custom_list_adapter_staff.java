package com.example.daniel.university_of_lincoln_companion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    //region GLOBAL VARIABLES
    private Activity context;
    private ArrayList<String> arName;
    private ArrayList<String> arDepartment;
    private ArrayList<String> arEmail;
    private ArrayList<String> arPhone;
    private String strPhoneNumber, strEmailAddress,  strPermission = "android.permission.CALL_PHONE";
    //endregion


    //ADAPTER CONSTRUCTOR RECEIVING THE CALLING CONTEXT PLUS ARRAY LISTS CONTAINING THE REQUIRED DATA
    public custom_list_adapter_staff(Activity context, ArrayList<String> arNameF, ArrayList<String> arDepartmentF, ArrayList<String> arEmailF, ArrayList<String> arPhoneF) {
        super(context, R.layout.custom_listrow_staffsearch, arNameF);

        //region SETTING GLOBAL VARIABLES VALUES FROM PASSED DATA
        this.context = context;
        this.arName = arNameF;
        this.arDepartment = arDepartmentF;
        this.arEmail = arEmailF;
        this.arPhone = arPhoneF;
        //endregion
    }

    //CREATES AND RETURNS LIST ROW BY ROW
    public View getView(final int position, View view, ViewGroup parent) {

        //INFLATES THE LIST PLACING THE COMPONENTS OF THE STATED XML FILE IN EACH ROW
        LayoutInflater inflater = context.getLayoutInflater();
        View viewRow = inflater.inflate(R.layout.custom_listrow_staffsearch, null, true);

        //region CASTS ROW COMPONENTS
        TextView tvStaffName = (TextView) viewRow.findViewById(R.id.tvStaffName);
        TextView tvDepartment = (TextView) viewRow.findViewById(R.id.tvDepartment);
        TextView tvEmail = (TextView) viewRow.findViewById(R.id.tvEmail);;
        TextView tvPhone = (TextView) viewRow.findViewById(R.id.tvPhone);
        ImageButton btnEmail = (ImageButton) viewRow.findViewById(R.id.btnEmail);
        ImageButton btnPhone = (ImageButton) viewRow.findViewById(R.id.btnRing);
        //endregion

        //region SETS THE VALUES OF THE COMPONENTS TO THE VALUES OF THE ARRAY DATA PASSED IN, AT THE POSITION OF THE CURRENT ROW
        tvStaffName.setText(arName.get(position));
        tvDepartment.setText(arDepartment.get(position));
        tvEmail.setText(arEmail.get(position));
        tvPhone.setText(arPhone.get(position));
        //endregion

        //region CLICK LISTENER FOR LISTVIEW PHONE BUTTONS
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strPhoneNumber = arPhone.get(position);

                //CHECK IT IS A NUMBER
                if (!strPhoneNumber.equals("Not Specified")) {

                    //region START CALL EVENT TO THE SET NUMBER
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + strPhoneNumber));
                    context.startActivity(callIntent);
                    //endregion
                }else{
                    //region ALERT DIALOG
                    AlertDialog alertNoActiveUser = new AlertDialog.Builder(context).create();
                    alertNoActiveUser.setTitle("Warning!");
                    alertNoActiveUser.setMessage("There is not contact number associated with this member of staff\n\nYou will not be able to call");

                    alertNoActiveUser.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();   //CLOSES THE DIALOG
                        }
                    });
                    alertNoActiveUser.show();
                    //endregion
                }
            }
        });
        //endregion

        //region CLICK LISTENER FOR LISTVIEW EMAIL BUTTONS
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmailAddress = arEmail.get(position);

                //region STARTS EMAIL ACTIVITY
                Intent emailIntent = new Intent(context, SendEmail.class);
                emailIntent.putExtra("Email_Address", strEmailAddress);
                context.startActivity(emailIntent);
                //endregion
            }
        });
        //endregion

        return viewRow;
    }

}
