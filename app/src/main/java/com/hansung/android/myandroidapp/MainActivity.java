package com.hansung.android.myandroidapp;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements MonthCalendarFragment.OnTitleSelectedListener_Month, WeekCalendarFragment.OnTitleSelectedListener_Week {
    // 데이터 원본 준비
    Calendar today;
    ArrayList<String> list = new ArrayList<>();



    //앱바
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.month:
                //월 달력으로 프래그먼트 전환
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new MonthViewFragment());
                fragmentTransaction.commit();
                return true;
            case R.id.week:
                //주간 달력으로 프래그먼트를 전환
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new WeekViewFragment());
                fragmentTransaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        today = Calendar.getInstance();   //현재 날짜를 가진 캘린더 객체 생성

        getSupportActionBar().setTitle("Month&Week Calendar");
    }
    public void onTitleSelected_month(int year,int month,int day) {

    }

    @Override
    public void onTitleSelected_week(int i, int j, int k) {

    }
}