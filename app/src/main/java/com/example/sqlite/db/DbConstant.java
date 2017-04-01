package com.example.sqlite.db;

/**
 * Created by 徐欣 on 2017/3/22.
 * 数据库常量类
 */

public class DbConstant {
    //为了避免写sql语句时,拼接导致sql语句出错，所有的String前后都留一个空格
    //如果在使用字段作为游标查询index的时候要调用 String.trim();否者可能会报错

    //数据库名字
    static final String DB_NAME = "user.db";
    //创建数据库的执行语句
    private static final String CREATE_TAB = " CREATE TABLE IF NOT EXISTS ";
    private static final String INTEGER = " INTEGER ";
    private static final String TEXT = " TEXT ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String AUTOINCREMENT = " AUTOINCREMENT ";

    static final String CREATE_USER_TABLE =
            CREATE_TAB + UserTable.TABLE_NAME
                    + "("
                    + UserTable.ID_INT + INTEGER + PRIMARY_KEY + AUTOINCREMENT + ","
                    + UserTable.USER_ID + TEXT + ","
                    + UserTable.NAME + TEXT + ","
                    + UserTable.SEX + TEXT + ","
                    + UserTable.AGE + TEXT
                    + ")";

    /**
     * 用户表
     */
    class UserTable {
        //表的名字
        static final String TABLE_NAME = " user ";
        //主键
        static final String ID_INT = " _id ";
        //具体的参数
        static final String USER_ID = " userId ";
        static final String NAME = " name ";
        static final String SEX = " sex ";
        static final String AGE = " age ";
    }
}
