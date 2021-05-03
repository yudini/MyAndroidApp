package com.hansung.android.myandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class WeekCalendarFragment extends Fragment {



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private int year;
    private int month;
    private int week;

    // 데이터 원본 준비
    Calendar today;
    ArrayList<String> list1 = new ArrayList<>();
    ArrayList<String> list2 = new ArrayList<>();
    Intent getIn;

    public WeekCalendarFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WeekCalendarFragment newInstance(int year , int month, int week) {
        WeekCalendarFragment fragment = new WeekCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year+month/12);
        args.putInt(ARG_PARAM2, month+week/6);
        args.putInt(ARG_PARAM3, week%6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("프래그먼트");
        if (getArguments() != null) {
            year = getArguments().getInt(ARG_PARAM1);
            month = getArguments().getInt(ARG_PARAM2);
            week = getArguments().getInt(ARG_PARAM3);

        }

    }

    // 인터페이스 추가 정의
    public interface OnTitleSelectedListener_Week {
        public void onTitleSelected_week(int i,int j,int k);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_week_calendar,container, false);


        today = Calendar.getInstance();   //현재 날짜를 가진 캘린더 객체 생성

        // id를 바탕으로 화면 레이아웃에 정의된 GridView 객체 로딩
        GridView gridview = (GridView)rootView.findViewById(R.id.gridview);

        getCalendar();
        // init();



        //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용
        ArrayAdapter<String> adapt_grid
                = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                list2);

        // 어댑터를 GridView 객체에 연결
        gridview.setAdapter(adapt_grid);


        ActionBar actionBar =((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(today.get(Calendar.YEAR)+"년"+(today.get(Calendar.MONTH)+1)+"월");


        //현재 날짜 토스트 메세지 띄우기
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //gridview.setBackgroundColor(Color.BLACK);
                //view.setBackgroundColor(Color.CYAN);
                int day=position-today.get(Calendar.DAY_OF_WEEK)+2;      //현재 일 구하기
                // 현재 프래그먼트와 연결된 액티비티를 반환


                Activity activity = getActivity();
                // 선택된 항목 위치(position)을 OnTitleSelectedListener 인터페이스를 구현한 액티비티로 전달
                if (activity instanceof OnTitleSelectedListener_Week){
                    ((OnTitleSelectedListener_Week)activity).onTitleSelected_week(today.get(Calendar.YEAR),(today.get(Calendar.MONTH)),day);
                }
            }
        });

        return rootView;

    }

    //달력 정보를 가져오는 함수,인텐트로 새로운 달력 정보를 가져올 경우
    private void getCalendar(){


        today.set(year,month,1);                             //현재 년도 및 월 설정.
        ActionBar actionBar =((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(today.get(Calendar.YEAR)+"년"+(today.get(Calendar.MONTH)+1)+"월");
        int lastDate = today.getActualMaximum(Calendar.DATE);  //이번달의 마지막 날 얻어서 저장
        int startDate = today.get(Calendar.DAY_OF_WEEK);   //이번달의 시작요일 얻어서 저장


        System.out.println("getCalendar 호출");
        System.out.println(week);
        System.out.println("year:"+today.get(Calendar.YEAR)+"month:"+(today.get(Calendar.MONTH)+1)+lastDate);

        for(int i=0;i<startDate-1;i++){
            list1.add("");                //공백으로 채우기
        }
        for(int i=1;i<=lastDate;i++){
            list1.add(Integer.toString(i));     //일 채우기
        }
        list1.add("");   //오류방지용 뒤의 여백
        list1.add("");
        list1.add("");
        list1.add("");
        list1.add("");

        switch (week) {
            case 0:
                for (int i = 0; i <= 6; i++) {
                    list2.add(list1.get(i));                //공백으로 채우기
                }
                break;
            case 1:

                for (int i = 7; i <= 13; i++) {
                    list2.add(list1.get(i));     //일 채우기
                }
                break;
            case 2:

                for (int i = 14; i <= 20; i++) {
                    list2.add(list1.get(i));     //일 채우기
                }
                break;
            case 3:

                for (int i = 21; i <= 27; i++) {
                    list2.add(list1.get(i));     //일 채우기
                }
                break;
            case 4:


                for (int i = 28; i <= 34; i++) {
                    list2.add(list1.get(i));     //일 채우기
                }
                break;
            case 5:

                for (int i = 35; i < startDate-1+lastDate; i++) {
                    list2.add(list1.get(i));     //일 채우기
                }
                break;
        }

        System.out.println(list1);
        System.out.println(list2);
        System.out.println(startDate-1+lastDate);

    }




}