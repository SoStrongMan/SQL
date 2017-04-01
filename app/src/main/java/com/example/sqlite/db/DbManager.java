package com.example.sqlite.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 徐欣 on 2017/3/23.
 * 数据库管理
 */

public class DbManager {
    private static DbManager instance;
    private static DbHelper mDbHelper;
    private SQLiteDatabase mDatabase;
    //原子操作，线程安全
    private AtomicInteger mOpenCounter = new AtomicInteger();

    public static synchronized DbManager getInstance(DbHelper dbHelper) {
        if (instance == null) {
            newInstance(dbHelper);
        }
        return instance;
    }

    public static synchronized void newInstance(DbHelper dbHelper) {
        if (instance == null) {
            instance = new DbManager();
            mDbHelper = dbHelper;
        }
    }

    /**
     * 批量处理（很多数据一次性写入时,推荐使用）
     *
     * @param sql        sql语句
     * @param paramsList 参数
     */
    public void batchOperator(String sql, List<Object[]> paramsList) {
        try {
            mDatabase = getReadableDatabase();
            //数据库事件，保证数据完整性
            mDatabase.beginTransaction();
            for (Object[] params : paramsList) {
                mDatabase.execSQL(sql, params);
            }
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mDatabase != null) {
                mDatabase.endTransaction();
                closeDatabase();
            }
        }
    }

    /**
     * 该方法可以执行insert，delete，update操作
     *
     * @param sql    sql语句
     * @param params 参数
     */
    public void operator(String sql, Object[] params) {
        try {
            mDatabase = getReadableDatabase();
            if (params != null) {
                mDatabase.execSQL(sql, params);
            } else {
                mDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mDatabase != null) {
                closeDatabase();
            }
        }
    }

    /**
     * 查询操作
     *
     * @param sql     sql语句
     * @param params  参数
     * @param columns 列名
     * @return 列表数据
     */
    public List<Map> query(String sql, String[] params, String[] columns) {
        List<Map> resList = new ArrayList<>();
        Cursor c = null;
        try {
            mDatabase = getReadableDatabase();
            c = mDatabase.rawQuery(sql, params);
            Map<String, Object> map;
            while (c.moveToNext()) {
                map = new HashMap<>();
                for (String colName : columns) {
                    map.put(colName, c.getString(c.getColumnIndex(colName)));
                }
                resList.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
            if (mDatabase != null) {
                closeDatabase();
            }
        }
        return resList;
    }

    /**
     * 获得行数
     *
     * @param sql    sql语句
     * @param params 参数
     * @return 行数
     */
    public int getCount(String sql, String[] params) {
        int count = 0;
        Cursor c = null;
        try {
            mDatabase = getReadableDatabase();
            c = mDatabase.rawQuery(sql, params);
            count = c.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
            if (mDatabase != null) {
                closeDatabase();
            }
        }
        return count;
    }

    /**
     * 判断某个字段是否为空
     *
     * @param sql    sql语句
     * @param params 参数
     * @return 结果
     */
    public boolean isNull(String sql, String[] params) {
        Cursor c = null;
        try {
            mDatabase = getReadableDatabase();
            c = mDatabase.rawQuery(sql, params);
            if (c.moveToNext()) {
                return TextUtils.isEmpty(c.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
            if (mDatabase != null) {
                closeDatabase();
            }
        }
        return false;
    }

    /**
     * 不常用
     * 当磁盘空间满后，以该方式打开数据库会出错
     *
     * @return 数据库
     */
    private synchronized SQLiteDatabase getWritableDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            mDatabase = mDbHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    /**
     * 常用的读取数据库的方法
     * 当磁盘空间满后，以只读方式继续打开数据库
     *
     * @return 数据库
     */
    private synchronized SQLiteDatabase getReadableDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            mDatabase = mDbHelper.getReadableDatabase();
        }
        return mDatabase;
    }

    /**
     * 关闭数据库
     */
    private synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            mDatabase.close();
        }
    }
}
