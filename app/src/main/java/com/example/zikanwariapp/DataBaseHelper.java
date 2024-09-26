package com.example.zikanwariapp;

//import static androidx.appcompat.graphics.drawable.DrawableContainerCompat.Api21Impl.getResources;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lectures.db";

    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //テーブルの作成
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE lectures (");
        sb.append("_id INTEGER PRIMARY KEY,");
        sb.append("name TEXT,");
        sb.append("room TEXT,");
        sb.append("notification INTEGER");
        sb.append(");");
        String sql = sb.toString();
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(db != null){
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_NAME);
            onCreate(db);
        }
    }






}
