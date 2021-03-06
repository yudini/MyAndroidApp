package com.hansung.android.myandroidapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthCalendarFragment extends Fragment {

    //데이터 원본 준비
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int year;
    private int month;
    public static int day;
    Calendar today;
    ArrayList<String> list = new ArrayList<>();
    private DBHelper dbHelper;

    public MonthCalendarFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MonthCalendarFragment newInstance(int year , int month) {  //프래그먼트 객체 생성 함수
        MonthCalendarFragment fragment = new MonthCalendarFragment();
        Bundle args = new Bundle();   //인자 값을 저장한 번들 객체 생성
        args.putInt(ARG_PARAM1, year);     //인자 값을 페어로 번들 객체에 설정
        args.putInt(ARG_PARAM2, month);
        fragment.setArguments(args); //인자값을 저장한 번들 객체를 프래그먼트로 전달

        return fragment;
    }



    // 인터페이스 추가 정의
    public interface OnTitleSelectedListener_Month {
        public void onTitleSelected_month(int i, int j, int k);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year= getArguments().getInt(ARG_PARAM1);
            month = getArguments().getInt(ARG_PARAM2);
        }
        dbHelper= new DBHelper(getActivity());

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_month_calendar,container, false);


        today = Calendar.getInstance();   //현재 날짜를 가진 캘린더 객체 생성

        // id를 바탕으로 화면 레이아웃에 정의된 GridView 객체 로딩
        GridView gridview = (GridView)rootView.findViewById(R.id.gridview);

        getCalendar();

        GridAdapter adapt2;

        //가로모드일 때
        if(getActivity().getWindowManager().getDefaultDisplay().getRotation()
                == Surface.ROTATION_90||getActivity().getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_270){
            //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용
            adapt2 = new GridAdapter(getActivity(),android.R.layout.simple_list_item_1,list,130);
        }else{  //세로모드일 때
            adapt2=new GridAdapter(getActivity(),android.R.layout.simple_list_item_1,list,250);
        }
        // 어댑터를 GridView 객체에 연결
        gridview.setAdapter(adapt2);


        //현재 날짜 토스트 메세지 띄우기
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                view.setSelected(true);
                day=position-today.get(Calendar.DAY_OF_WEEK)+2;      //현재 일 구하기
                // 현재 프래그먼트와 연결된 액티비티를 반환
                Cursor cursor = dbHelper.getUserByDateOfSQL(year,(month+1),day);
                ArrayList<String> dlgList = new ArrayList<>();

                ListView dlgView = (ListView)inflater.inflate(R.layout.dialog, null).findViewById(R.id.dialogView);

                while(cursor.moveToNext()){
                    String s=cursor.getString(2);
                    dlgList.add(s);
                }
                ArrayAdapter<String> adp = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dlgList);

                if(cursor.moveToFirst()){
                    dlgView.setAdapter(adp);
                    dlgView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String title = dlgList.get(position);
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra("title",title);
                            startActivity(intent);
                        }
                    });
                    dlg.setTitle(year +  "." + (month+1) + "." + day);
                    ((ViewGroup)dlgView.getParent()).removeView(dlgView);
                    dlg.setView(dlgView);
                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            //토스트 메시지
                        }
                    });
                    dlg.show();
                }


                Activity activity = getActivity();
                if(day>=1){
                    BusProvider.getInstance().post(day);
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

        for(int i=0;i<startDate-1;i++){
            list.add("");                //공백으로 채우기
        }
        for(int i=1;i<=lastDate;i++){
            list.add(Integer.toString(i));     //일 채우기
        }
        int k=42-list.size();
        for(int i=0;i<k;i++){
            list.add("");
        }

    }



}