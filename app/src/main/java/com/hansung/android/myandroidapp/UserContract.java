package com.hansung.android.myandroidapp;

import android.provider.BaseColumns;

public final class UserContract {
    public static final String DB_NAME="user.db";
    public static final int DATABASE_VERSION=3;
    public static final String TEXT_TYPE=" TEXT";
    public static final String COMMA_SEP =",";

    private UserContract(){

    }

    public static class Users implements BaseColumns{
        public static final String TABLE_NAME="Users";
        public static final String KEY_DATE="date";
        public static final String KEY_TITLE="title";
        public static final String KEY_STARTHOUR="startHour";
        public static final String KEY_STARTMIN="startMin";
        public static final String KEY_ENDHOUR="endHour";
        public static final String KEY_ENDMIN="endMin";
        public static final String KEY_ADDRESS="address";
        public static final String KEY_MEMO="memo";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_DATE + TEXT_TYPE + COMMA_SEP +
                KEY_TITLE + TEXT_TYPE + COMMA_SEP +
                KEY_STARTHOUR + TEXT_TYPE + COMMA_SEP +
                KEY_STARTMIN + TEXT_TYPE + COMMA_SEP +
                KEY_ENDHOUR + TEXT_TYPE + COMMA_SEP +
                KEY_ENDMIN + TEXT_TYPE + COMMA_SEP +
                KEY_ADDRESS + TEXT_TYPE + COMMA_SEP +
                KEY_MEMO + TEXT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
