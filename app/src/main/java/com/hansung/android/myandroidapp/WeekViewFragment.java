package com.hansung.android.myandroidapp;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.time.Year;
import java.util.Calendar;

public class WeekViewFragment extends Fragment {

    Calendar today;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;
    private int mparam3;

    public WeekViewFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WeekViewFragment newInstance(int param1, int param2, int param3) {
        WeekViewFragment fragment = new WeekViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mparam3 = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        today=Calendar.getInstance();
       // int month= today.get(Calendar.MONTH);
        int week= today.get(Calendar.WEEK_OF_MONTH);
        View rootView = inflater.inflate(R.layout.fragment_week_view,container, false);
        ViewPager2 vpPager = rootView.findViewById(R.id.vpPager_week);
        FragmentStateAdapter adapter = new WeekAdapter(this);

        vpPager.setAdapter(adapter);
        vpPager.setCurrentItem(week-1,false);  // SecondFragment를 첫 화면에 표시되도록 설정
        ActionBar actionBar =((MainActivity)getActivity()).getSupportActionBar();


        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {  //페이지 변화가 일어날때
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                int week = position;
                int month=today.get(Calendar.MONTH)+week/6; //페이지에 따른 달력의 연도 설정
                int year=today.get(Calendar.YEAR)+month/12;  //페이지에 따른 달력의 연도 설정

                actionBar.setTitle(year+"년"+(month+1)+"월");   //액션바 타이틀 변경
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}