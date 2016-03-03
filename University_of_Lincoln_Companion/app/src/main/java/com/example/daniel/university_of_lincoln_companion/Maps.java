package com.example.daniel.university_of_lincoln_companion;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText etFindRoom;
    private String strSearchlocation, strBuilding, strFloor, strLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setTitle("Find A Room");

        etFindRoom = (EditText)findViewById(R.id.etRoomSearch);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        etFindRoom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (etFindRoom.isFocused() == false){
                    HideKeyboard();
                }
            }
        });
    }

    public void HideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etFindRoom.getWindowToken(), 0);
    }

    public void findRoom(View view){

        HideKeyboard();
        mMap.clear();
        strSearchlocation = etFindRoom.getText().toString();

        if (!strSearchlocation.equals(" ") || strSearchlocation != null || !strSearchlocation.equals("")) {

            String[] arSplit = strSearchlocation.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

            strBuilding = arSplit[0].toUpperCase();

            if (arSplit.length > 1) {
                char[] temp = arSplit[1].toCharArray();
                strFloor = ("Floor: " + String.valueOf(temp[0]));
            }else strFloor = "Floor: Not Specified";

            LatLng latLng;

            switch (strBuilding) {
                case "MB":
                    strLocation = "Minerva Building";
                    latLng = new LatLng(53.228529, -0.547874);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "MC":
                    strLocation = "Media, Humanities & Technology";
                    latLng = new LatLng(53.228850, -0.549451);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "MT":
                    strLocation = "EMMTEC";
                    latLng = new LatLng(53.228850, -0.549451);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "SS":
                    strLocation = "Student Wellbeing Center";
                    latLng = new LatLng(53.229309, -0.549211);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "HP":
                    strLocation = "Sports Center";
                    latLng = new LatLng(53.229309, -0.549211);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "AAD":
                    strLocation = "Art, Architecture & Design";
                    latLng = new LatLng(53.227867, -0.548407);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "ENG":
                    strLocation = "Engineering Hub";
                    latLng = new LatLng(53.227186, -0.547270);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "PA":
                    strLocation = "Lincoln Performing Arts Centre";
                    latLng = new LatLng(53.227275, -0.546148);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "UL":
                    strLocation = "The Library";
                    latLng = new LatLng(53.226690, -0.545159);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "BL":
                    strLocation = "David Chiddick Building";
                    latLng = new LatLng(53.226645, -0.544065);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "MIH":
                    strLocation = "Minster House";
                    latLng = new LatLng(53.227909, -0.556101);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "JBL":
                    strLocation = "Joseph Banks Laboratories";
                    latLng = new LatLng(53.227271, -0.555746);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "CSB":
                    strLocation = "Charlotte Scott Building";
                    latLng = new LatLng(53.228802, -0.556225);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                case "JUN":
                    strLocation = "Junxion";
                    latLng = new LatLng(53.225935, -0.543630);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(strLocation).snippet(strFloor)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    break;

                default:
                    Toast.makeText(Maps.this, "Invalid Entry", Toast.LENGTH_SHORT).show();
                    etFindRoom.setText("");
                    break;
            }
        }
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMyLocationEnabled(true);
        List<Address> addressList = null;

        String strLocation = "University of Lincoln";

        Geocoder geocoder = new Geocoder(this);

        try {
            addressList = geocoder.getFromLocationName(strLocation, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Address address = addressList.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("University of Lincoln")).showInfoWindow();
        //mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }
}
