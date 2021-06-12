package com.hansung.android.myandroidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest";

    public DBHelper(Context context){
        super(context, UserContract.DB_NAME,null, UserContract.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(UserContract.Users.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,getClass().getName()+"onUpgrade()");
        db.execSQL(UserContract.Users.DELETE_TABLE);
        onCreate(db);
    }

    public void insertUserBySQL(String date, String title,String startHour,String startMin,String endHour,String endMin,String address,String memo) {
        try {
            String sql = String.format (
                    "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users._ID,
                    UserContract.Users.KEY_DATE,
                    UserContract.Users.KEY_TITLE,
                    UserContract.Users.KEY_STARTHOUR,
                    UserContract.Users.KEY_STARTMIN,
                    UserContract.Users.KEY_ENDHOUR,
                    UserContract.Users.KEY_ENDMIN,
                    UserContract.Users.KEY_ADDRESS,
                    UserContract.Users.KEY_MEMO,
                    date,
                    title,
                    startHour,
                    startMin,
                    endHour,
                    endMin,
                    address,
                    memo);

            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }

    public Cursor getUserByDateOfSQL(int year,int month,int day) {
        String date= year + "/" +month+"/" + day;
        String sql= String.format(
                "SELECT * FROM %s WHERE %s = '%s'",
                UserContract.Users.TABLE_NAME,
                UserContract.Users.KEY_DATE,
                date);
        return getReadableDatabase().rawQuery(sql,null);


    }

    public Cursor getUserByTitleOfSQL(String title) {
        String sql= String.format(
                "SELECT * FROM %s WHERE %s = '%s'",
                UserContract.Users.TABLE_NAME,
                UserContract.Users.KEY_TITLE,
                title);
        return getReadableDatabase().rawQuery(sql,null);


    }

    public void deleteUserBySQL(String title) {
        try {
            String sql = String.format (
                    "DELETE FROM %s WHERE %s = '%s'",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users.KEY_TITLE,
                    title);
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in deleting recodes");
        }
    }

    public void updateUserBySQL(String date, String title,String startHour,String startMin,String endHour,String endMin,String address,String memo) {
        try {
            String sql = String.format (
                    "UPDATE  %s SET %s = '%s', %s = '%s' WHERE %s = '%s'",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users._ID,
                    UserContract.Users.KEY_DATE,
                    UserContract.Users.KEY_TITLE,
                    UserContract.Users.KEY_STARTHOUR,
                    UserContract.Users.KEY_STARTMIN,
                    UserContract.Users.KEY_ENDHOUR,
                    UserContract.Users.KEY_ENDMIN,
                    UserContract.Users.KEY_ADDRESS,
                    UserContract.Users.KEY_MEMO,
                    date,
                    title,
                    startHour,
                    startMin,
                    endHour,
                    endMin,
                    address,
                    memo) ;
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in updating recodes");
        }
    }



}
