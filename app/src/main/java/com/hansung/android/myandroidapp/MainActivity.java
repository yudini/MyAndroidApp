package com.hansung.android.myandroidapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Calendar today;
    // 데이터 원본 준비
    ArrayList<String> list = new ArrayList<>();
    int year,month,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_gridview);
        TextView todayDate=(TextView)findViewById(R.id.date); // id를 바탕으로 화면 레이아웃에 정의된 TextView 객체 로딩

        Button pre = findViewById(R.id.button);
        Button next = findViewById(R.id.button2);
        int count1;

         do{
            init();
            todayDate.setText(year + "년" + month + "월");
        }while(false);
         


        Intent getIn = getIntent();
        if(getIn==null){
            //init();
            //todayDate.setText(year + "년" + month + "월");    //텍스트뷰에 현재년도 및 월 띄우기
        }

        else {
            year=getIn.getIntExtra("year",0);
            month=getIn.getIntExtra("month",0);
            date=getIn.getIntExtra("date",1);
            today.set(year,month,date);

            Calendar thisMonth = Calendar.getInstance();
            thisMonth.set(year,month,1);     //현재 달의 첫번째날로 날짜 설정
            int lastDate = today.getActualMaximum(Calendar.DATE);  //이번달의 마지막 날 얻어서 저장
            int startDate = thisMonth.get(Calendar.DAY_OF_WEEK);   //이번달의 시작요일 얻어서 저장
            for(int i=0;i<startDate-1;i++){
                list.add("");                //공백으로 채우기
            }
            for(int i=1;i<=lastDate;i++){
                list.add(Integer.toString(i));     //일 채우기
            }
        }

        // id를 바탕으로 화면 레이아웃에 정의된 GridView 객체 로딩
        GridView gridview = (GridView) findViewById(R.id.gridview);

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("year",year);
                intent.putExtra("month",month-1);
                intent.putExtra("date",date);
                startActivity(intent);




                todayDate.setText(year + "년" + month + "월");
            }
        });




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("year",year);
                intent.putExtra("month",month+1);
                intent.putExtra("date",date);
                startActivity(intent);


                todayDate.setText(year + "년" + month + "월");
            }
        });



        //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용
        ArrayAdapter<String> adapt
                = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list);

        // 어댑터를 GridView 객체에 연결
        gridview.setAdapter(adapt);

    }

    private void init(){
        today = Calendar.getInstance();
        Calendar thisMonth = Calendar.getInstance();
        thisMonth.set(Calendar.DAY_OF_MONTH,1);     //현재 달의 첫번째날로 날짜 설정
        year = today.get(Calendar.YEAR);         //현재년도 정보 얻어서 저장
        month = today.get(Calendar.MONTH) +1;    //현재 월의 정보 얻어서 저장
        date = today.get(Calendar.DATE);
        int lastDate = today.getActualMaximum(Calendar.DATE);  //이번달의 마지막 날 얻어서 저장
        int startDate = thisMonth.get(Calendar.DAY_OF_WEEK);   //이번달의 시작요일 얻어서 저장
        for(int i=0;i<startDate-1;i++){
            list.add("");                //공백으로 채우기
        }
        for(int i=1;i<=lastDate;i++){
            list.add(Integer.toString(i));     //일 채우기
        }

    }

    private static final String TAG = "Lifecycle";

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, getLocalClassName() + ".onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, getLocalClassName() + ".onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, getLocalClassName() + ".onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, getLocalClassName() + ".onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, getLocalClassName() + ".onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getLocalClassName() + ".onDestroy");
    }


}