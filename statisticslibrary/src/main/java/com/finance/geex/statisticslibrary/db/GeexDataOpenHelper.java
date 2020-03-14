package com.finance.geex.statisticslibrary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 2019/8/14 14:09.
 *
 * @author Geex302
 */
public class GeexDataOpenHelper extends SQLiteOpenHelper{

    private static final String CREATE_EVENTS_TABLE =
            "CREATE TABLE " + GeexDbParams.TABLE_EVENTS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GeexDbParams.KEY_DATA + " TEXT NOT NULL);";

    private static final String CREATE_NETWORK_REQUEST_TABLE =
            "CREATE TABLE " + GeexDbParams.TABLE_NETWORK_REQUEST + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GeexDbParams.KEY_DATA + " TEXT NOT NULL);";

    private static final String CREATE_APP_CRASH_TABLE =
            "CREATE TABLE " + GeexDbParams.TABLE_APP_CRASH + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GeexDbParams.KEY_DATA + " TEXT NOT NULL);";




    GeexDataOpenHelper(Context context) {
        super(context, GeexDbParams.DATABASE_NAME, null, GeexDbParams.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //建表
        //埋点表
        db.execSQL(CREATE_EVENTS_TABLE);
        //网络请求表
        db.execSQL(CREATE_NETWORK_REQUEST_TABLE);
        //app崩溃表
        db.execSQL(CREATE_APP_CRASH_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //更新表(先删表，后建表)
        db.execSQL("DROP TABLE IF EXISTS " + GeexDbParams.TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + GeexDbParams.TABLE_NETWORK_REQUEST);
        db.execSQL("DROP TABLE IF EXISTS " + GeexDbParams.TABLE_APP_CRASH);

        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(CREATE_NETWORK_REQUEST_TABLE);
        db.execSQL(CREATE_APP_CRASH_TABLE);

    }


}
