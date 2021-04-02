package com.hansung.android.myandroidapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Calendar today;
    // 데이터 원본 준비
    ArrayList<String> list = new ArrayList<>();
    Intent getIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_gridview);

        TextView todayDate=(TextView)findViewById(R.id.date); // id를 바탕으로 화면 레이아웃에 정의된 TextView 객체 로딩

        Button pre = findViewById(R.id.button);
        Button next = findViewById(R.id.button2);

        today = Calendar.getInstance();   //현재 날짜를 가진 캘린더 객체 생성

        getIn = getIntent();   //인텐트 입력받기
        if (getIn.getIntExtra("year",0)==0){  // 인텐트 여부 확인
            init();               //초기달력정보 받아오기
        }

        else{    //인텐트가 있을 때, 즉 새로운 액티비티가 생성되었을 때
            getCalendar();   //캘린더 정보 받아오기
        }

        int month=today.get(Calendar.MONTH)+1;      //캘린더 클래스의 월은 0~11, +1을 해주어서 1~12로 설정.
        todayDate.setText(today.get(Calendar.YEAR) + "년" + month + "월");    //텍스트뷰에 현재 연도 및 월 띄우기

        // id를 바탕으로 화면 레이아웃에 정의된 GridView 객체 로딩
        GridView gridview = (GridView) findViewById(R.id.gridview);

        //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용
        ArrayAdapter<String> adapt
                = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list);

        // 어댑터를 GridView 객체에 연결
        gridview.setAdapter(adapt);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int day=position-today.get(Calendar.DAY_OF_WEEK)+2;
                if(day>=1)
                    Toast.makeText(getApplicationContext(),""+today.get(Calendar.YEAR)+"."+month+"."+day,Toast.LENGTH_SHORT).show();
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("year",today.get(Calendar.YEAR));
                intent.putExtra("month",today.get(Calendar.MONTH)-1);
                intent.putExtra("date",1);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("year",today.get(Calendar.YEAR));
                intent.putExtra("month",today.get(Calendar.MONTH)+1);
                intent.putExtra("date",1);
                startActivity(intent);
            }
        });

    }

    //현재 날짜의 달력 정보를 가져오는 함수. 초기 액티비티 시작시
    private void init(){
        today.set(Calendar.DAY_OF_MONTH,1);     //현재 달의 첫번째날로 날짜 설정
        int lastDate = today.getActualMaximum(Calendar.DATE);  //이번달의 마지막 날 얻어서 저장
        int startDate = today.get(Calendar.DAY_OF_WEEK);   //이번달의 시작요일 얻어서 저장
        for(int i=0;i<startDate-1;i++){
            list.add("");                //시작 요일 이전 요일 공백으로 채우기
        }
        for(int i=1;i<=lastDate;i++){
            list.add(Integer.toString(i));     //일 채우기
        }

    }

    //달력 정보를 가져오는 함수,인텐트로 새로운 달력 정보를 가져올 경우
    private void getCalendar(){
        int year=getIn.getIntExtra("year",0);
        int month=getIn.getIntExtra("month",0);
        today.set(year,month,1);
        //Calendar thisMonth = Calendar.getInstance();
        //thisMonth.set(year,month,1);     //현재 달의 첫번째날로 날짜 설정
        int lastDate = today.getActualMaximum(Calendar.DATE);  //이번달의 마지막 날 얻어서 저장
        int startDate = today.get(Calendar.DAY_OF_WEEK);   //이번달의 시작요일 얻어서 저장
        for(int i=0;i<startDate-1;i++){
            list.add("");                //공백으로 채우기
        }
        for(int i=1;i<=lastDate;i++){
            list.add(Integer.toString(i));     //일 채우기
        }
    }


}