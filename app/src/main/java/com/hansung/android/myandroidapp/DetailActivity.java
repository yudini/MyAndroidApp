package com.hansung.android.myandroidapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FusedLocationProviderClient mFusedLocationClient;
    private EditText title;
    private Button findButton;
    private EditText address ;
    private String today;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private TextView memo;

    private DBHelper mDbHelper;
    final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 0;
    Location mLastLocation;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title=findViewById(R.id.title);
        findButton = findViewById(R.id.search);
        address = findViewById(R.id.address);
        startTimePicker = findViewById(R.id.start);
        endTimePicker =findViewById(R.id.end);
        memo =findViewById(R.id.memo);
        Button save = (Button)findViewById(R.id.save);
        Button cancel = (Button)findViewById(R.id.cancel);
        Button delete = (Button)findViewById(R.id.delete);

        Intent intent = getIntent();
        int year = intent.getIntExtra("year",0);
        int month =intent.getIntExtra("month",0);
        int day = intent.getIntExtra("day",0);
        int select_startHour = intent.getIntExtra("startTime", 0);
        String s = intent.getStringExtra("title");

        mDbHelper = new DBHelper(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if(s!=null) {
            Cursor cursor = mDbHelper.getUserByTitleOfSQL(s);
            cursor.moveToFirst();
            title.setText(cursor.getString(2));
            int startHour = Integer.parseInt(cursor.getString(3));
            int startMin = Integer.parseInt(cursor.getString(4));
            int endHour = Integer.parseInt(cursor.getString(5));
            int endMin = Integer.parseInt(cursor.getString(6));
            address.setText(cursor.getString(7));
            memo.setText(cursor.getString(8));

            startTimePicker.setHour(startHour);
            startTimePicker.setMinute(startMin);
            endTimePicker.setHour(endHour);
            endTimePicker.setMinute(endMin);

            save.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(DetailActivity.this);
                    dlg.setTitle("??????");
                    dlg.setMessage("?????? ?????????????????????????");
                    dlg.setPositiveButton("???",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            deleteRecord();
                            finish();
                        }
                    });
                    dlg.setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(DetailActivity.this,"?????? ??????", Toast.LENGTH_LONG).show();
                        }
                    });
                    dlg.show();
                }
            });

            getLastLocation();


        }else{

            save.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    insertRecord();
                    finish();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            today = year + "/" +month+"/" + day;
            title.setText(year+"???"+month+"???"+day+"???");

            startTimePicker.setHour(select_startHour);
            startTimePicker.setMinute(0);
            endTimePicker.setHour(select_startHour+1);
            endTimePicker.setMinute(0);

            getLastLocation();
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void insertRecord(){
        Integer.toString(startTimePicker.getHour());
        String startHour = Integer.toString(startTimePicker.getHour());
        String startMin = Integer.toString(startTimePicker.getMinute());
        String endHour = Integer.toString(endTimePicker.getHour());
        String endMin = Integer.toString(endTimePicker.getMinute());
        mDbHelper.insertUserBySQL(today,title.getText().toString(),startHour,
                startMin,endHour,endMin,address.getText().toString(),memo.getText().toString());
    }

    private void deleteRecord(){
        mDbHelper.deleteUserBySQL(title.getText().toString());
    }


    private void updateRecord() {

        // mDbHelper.updateUserBySQL(_id.getText().toString(),name.getText().toString(),phone.getText().toString());
//        long nOfRows = mDbHelper.updateUserByMethod(_id.getText().toString(),
//                name.getText().toString(),
//                phone.getText().toString());
//        if (nOfRows >0)
//            Toast.makeText(this,"Record Updated", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this,"No Record Updated", Toast.LENGTH_SHORT).show();
    }


    private void getLastLocation() {
        // 1. ?????? ????????? ????????? ?????? ?????? ??? ??????
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    DetailActivity.this,            // MainActivity ??????????????? ?????? ??????????????? ?????????
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // ????????? ?????? ????????? ????????? String ??????
                    REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION    // ????????? ?????? int ??????. ?????? ?????? ????????? ?????? ???
            );
            return;
        }


        // 2. Task<Location> ?????? ??????
        Task task = mFusedLocationClient.getLastLocation();

        // 3. Task??? ??????????????? ?????? ??? ???????????? OnSuccessListener ??????
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(DetailActivity.this::onMapReady);

                // 4. ??????????????? ????????? ??????(location ??????)??? ??????.
                if (location != null) {
                    mLastLocation = location;
                } else
                    Toast.makeText(getApplicationContext(),
                            "No location detected",
                            Toast.LENGTH_SHORT)
                            .show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(mLastLocation ==null)
            return;

        findButton.setOnClickListener(new Button.OnClickListener(){
            Address ad;

            @Override
            public void onClick(View v) {

                try {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREA);
                    String str=address.getText().toString();
                    List<Address> addresses = geocoder.getFromLocationName(str,1);
                    if (addresses.size() >0) {
                        ad = (Address) addresses.get(0);
                    }
                } catch (IOException e) {
                    Log.e(getClass().toString(),"Failed in using Geocoder.", e);
                    return;
                }
                Double latitude = ad.getLatitude();
                Double longitude = ad.getLongitude();

                LatLng point = new LatLng(latitude,longitude);   //??????(??????,??????)??????
                MarkerOptions mOptions = new MarkerOptions(); //?????? ??????
                mOptions.title("search result");
                googleMap.addMarker(
                        new MarkerOptions().
                                position(point).
                                title(".."));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));

            }

        });

    }

}