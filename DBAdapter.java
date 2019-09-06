package com.example.myhealth1;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
    // データベース関連の定数
    public static final String DATABASE_NAME = "myhealth.db";
    public static final int DATABASE_VERSION = 1;
    // テーブルの定数
    public static final String TABLE_NAME = "health_statuses";// テーブル名
    // カラム名を定数として定義（ココを記述）
    public static final String COL_ID = "_id";
    public static final String COL_HEIGHT = "height";
    public static final String COL_WEIGHT = "weight";
    public static final String COL_LASTUPDATE = "lastupdate";

    protected final Context context;
    protected DatabaseHelper dbHelper;
    protected SQLiteDatabase db;

    public DBAdapter(Context context){
        this.context = context;
        dbHelper = new DatabaseHelper(this.context);
    }

    //
    // SQLiteOpenHelper
    //

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // テーブル作成（ココを記述）
            db.execSQL(
                    "CREATE TABLE " + TABLE_NAME + " ("
                            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + COL_HEIGHT + " REAL NOT NULL,"
                            + COL_WEIGHT + " REAL NOT NULL,"
                            + COL_LASTUPDATE + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    //
    // Adapter Methods
    //

    public DBAdapter open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }


    public void close(){
        dbHelper.close();
    }


    //
    // App Methods
    //


    public Cursor getAllHealthStatuses(){
        // テーブルの全データを取得（ココを記述）
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public void saveData(double height, double weight){
        // テーブルにheight, weight, lastupdate（現日時）を追加（ココを記述）
        Date dateNow = new Date ();
        ContentValues values = new ContentValues();
        values.put(COL_HEIGHT, height);
        values.put(COL_WEIGHT, weight);
        values.put(COL_LASTUPDATE, dateNow.toLocaleString());
        db.insertOrThrow(TABLE_NAME, null, values);
    }
}
