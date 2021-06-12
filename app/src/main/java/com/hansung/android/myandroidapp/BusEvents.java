package com.hansung.android.myandroidapp;

public class BusEvents {
    public static class DATE1{
        private static int day; //버스에 태워 보낼 데이터
        public DATE1(int day){//598번버스 클래스 생성자
            this.day = day;
        }
        public static int getday(){ //데이터가 필요한 지점에서 사용할 get 함수
            return day;
        }
    }


}
