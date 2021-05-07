package com.hansung.android.myandroidapp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class Week_GridAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> list;
    private int mResource;
    Calendar today;
    int height;

    public Week_GridAdapter(Context context)
    {
        this.context = context;
    }

    public Week_GridAdapter(Context context, ArrayList<String> list){
        this.context =context;
        this.list=list;
    }

    public Week_GridAdapter(Context context,int resource,ArrayList<String>list){
        this.context=context;
        this.mResource=resource;
        this.list=list;
    }
    public Week_GridAdapter(Context context,int resource,ArrayList<String>list,int height){
        this.context=context;
        this.mResource=resource;
        this.list=list;
        this.height=height;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        // TODO Auto-generated method stub

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if (convertView == null) { // 해당 항목 뷰가 이전에 생성된 적이 없는 경우
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 항목 뷰를 정의한 xml 리소스(여기서는 mResource 값)으로부터 항목 뷰 객체를 메모리로 로드
            convertView = inflater.inflate(mResource, parent,false);
        }
        TextView text = (TextView)convertView;

        text.setText(list.get(position));

        text.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);  //텍스트 위치

        text.setBackground(ContextCompat.getDrawable(
                context,R.drawable.grideview_selector    //item 항목 배경을 gridview_selector로 설정(state_pressed 등)
        ));


        return text;

    }


}

