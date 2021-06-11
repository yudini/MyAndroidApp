package com.hansung.android.myandroidapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

    private DBHelper mDbHelper;
    final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 0;
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int year = intent.getIntExtra("year",0);
        int month =intent.getIntExtra("month",0);
        int day = intent.getIntExtra("day", 0);
        today = Integer.toString(year) + "/" +Integer.toString(month)+"/" + Integer.toString(year)+"/";

        mDbHelper = new DBHelper(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        title=findViewById(R.id.title);
        findButton = findViewById(R.id.search);
        address = findViewById(R.id.address);
        startTimePicker = findViewById(R.id.start);
        endTimePicker =findViewById(R.id.end);
        Button save = (Button)findViewById(R.id.save);
        Button cancel = (Button)findViewById(R.id.cancel);
        Button delete = (Button)findViewById(R.id.delete);

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

            }
        });

        title.setText(year+"년"+month+"월"+day+"일");

        getLastLocation();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void insertRecord(){
        TextView memo = (TextView)findViewById(R.id.memo);
        Integer.toString(startTimePicker.getHour());
        String startHour = Integer.toString(startTimePicker.getHour());
        String startMin = Integer.toString(startTimePicker.getMinute());
        String endHour = Integer.toString(endTimePicker.getHour());
        String endMin = Integer.toString(endTimePicker.getHour());
        mDbHelper.insertUserBySQL(today,title.getText().toString(),startHour+":"+startMin,
                endHour+":"+endMin, address.getText().toString(),memo.getText().toString());
    }

    private void deleteRecord(){

    }


    private void getLastLocation() {
        // 1. 위치 접근에 필요한 권한 검사 및 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    DetailActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                    REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
            );
            return;
        }


        // 2. Task<Location> 객체 반환
        Task task = mFusedLocationClient.getLastLocation();

        // 3. Task가 성공적으로 완료 후 호출되는 OnSuccessListener 등록
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(DetailActivity.this::onMapReady);

                // 4. 마지막으로 알려진 위치(location 객체)를 얻음.
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

                LatLng point = new LatLng(latitude,longitude);   //좌표(위도,경도)생성
                MarkerOptions mOptions = new MarkerOptions(); //마커 생성
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