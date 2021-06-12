package com.hansung.android.myandroidapp;
//import android.app.Fragment;
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
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.otto.Subscribe;

import java.time.Month;
import java.time.Year;
import java.util.Calendar;
public class MonthViewFragment extends Fragment {
    Calendar today;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    public static int day;
    private int year;
    private int month;
    private int DATE2;
    public MonthViewFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static MonthViewFragment newInstance(int param1, int param2, int param3) {
        MonthViewFragment fragment = new MonthViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle DATE = getArguments();
        today=Calendar.getInstance();
        year=today.get(Calendar.YEAR);
        month= today.get(Calendar.MONTH);
        View rootView = inflater.inflate(R.layout.fragment_month_view,container, false);
        ViewPager2 vpPager = rootView.findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new MonthAdapter(this);
        vpPager.setAdapter(adapter);
        vpPager.setCurrentItem(month,false);  // 첫 화면에 표시될 페이지 설정
        ActionBar actionBar =((MainActivity)getActivity()).getSupportActionBar();

        if (getArguments() != null)
        {
            System.out.println("aaa");
            day = getArguments().getInt("day"); // 프래그먼트1에서 받아온 값 넣기

        }
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {  //페이지 변화가 일어날때
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                year=today.get(Calendar.YEAR)+position/12;  //페이지에 따른 달력의 연도 설정
                month=position%12;                //페이지에 따른 달력의 월 설정
                actionBar.setTitle(year+"년"+(month+1)+"월");   //액션바 타이틀 변경
            }
        });
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("year",year);
                intent.putExtra("month",month+1);
                intent.putExtra("day", DATE2);

                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
    @Subscribe
    public void getPost(Integer day) {
        DATE2 = day;
    }
}