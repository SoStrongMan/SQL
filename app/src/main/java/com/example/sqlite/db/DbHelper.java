package com.example.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 徐欣 on 2017/3/22.
 * 创建或者更新数据库
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DbConstant.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConstant.CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
