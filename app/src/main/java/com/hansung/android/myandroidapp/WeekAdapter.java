package com.hansung.android.myandroidapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class WeekAdapter extends FragmentStateAdapter {
    Calendar today;

    public WeekAdapter(Fragment fragment) {
        super(fragment);
    }

    // 각 페이지를 나타내는 프래그먼트 반환
    @NonNull
    @Override
    public Fragment createFragment(int position) {

        today=Calendar.getInstance();
        int year = today.get(Calendar.YEAR);   //수정
        int month = today.get(Calendar.MONTH);  //수정
        int week = position;

        return WeekCalendarFragment.newInstance(year,month,week);

    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return 1000;
    }

}