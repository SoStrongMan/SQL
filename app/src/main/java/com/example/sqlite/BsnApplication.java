package com.example.sqlite;

import android.content.Context;

import com.example.sqlite.db.DbHelper;
import com.example.sqlite.db.DbManager;

import java.lang.ref.SoftReference;

/**
 * Created by 徐欣 on 2017/3/24.
 */

public class BsnApplication extends android.app.Application {
    private static SoftReference<Context> fbContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initSoftReference();
        initDBManager();
    }

    /**
     * 获得引用
     *
     * @return 上下文
     */
    public static SoftReference<Context> getAppContext() {
        return fbContext;
    }

    /**
     * 软引用配置
     */
    private void initSoftReference() {
        fbContext = new SoftReference<Context>(this);
    }

    /**
     * 初始化数据库管理
     */
    private void initDBManager() {
        DbManager.newInstance(new DbHelper(this));
    }
}
