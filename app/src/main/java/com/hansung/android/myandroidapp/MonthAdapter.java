package com.hansung.android.myandroidapp;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;


public class MonthAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=12;
    Calendar today;

    public MonthAdapter(Fragment fragment) {
        super(fragment);
    }


    // 각 페이지를 나타내는 프래그먼트 반환
    @NonNull
    @Override
    public Fragment createFragment(int position) {

        today=Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month =position;  //수정

        return MonthCalendarFragment.newInstance(year,month);   //프래그먼트 객체 생성

    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return 1000;
    }

}