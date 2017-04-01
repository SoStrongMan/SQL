package com.example.sqlite.db;

import com.example.sqlite.BsnApplication;
import com.example.sqlite.bean.UserBean;

import java.util.List;
import java.util.Map;

/**
 * Created by 徐欣 on 2017/3/27.
 * 用户
 */

public class UserDB {
    private DbManager mDbManager;

    public UserDB() {
        mDbManager = DbManager.getInstance(new DbHelper(BsnApplication.getAppContext().get()));
    }

    /**
     * 初始化数据
     *
     * @param bean 实体类
     */
    public void initUser(UserBean bean) {
        String delSql = "delete from" + DbConstant.UserTable.TABLE_NAME;
        mDbManager.operator(delSql, null);
        String instSql = "insert into" + DbConstant.UserTable.TABLE_NAME
                + "("
                + DbConstant.UserTable.USER_ID + ","
                + DbConstant.UserTable.NAME + ","
                + DbConstant.UserTable.SEX + ","
                + DbConstant.UserTable.AGE
                + ") values(?,?,?,?)";
        String[] params = new String[]{
                bean.getId(),
                bean.getName(),
                bean.getSex(),
                bean.getAge()
        };
        mDbManager.operator(instSql, params);
    }

    /**
     * 添加用户信息
     *
     * @param bean 实体类
     */
    public void addUser(UserBean bean) {
        String instSql = "insert into" + DbConstant.UserTable.TABLE_NAME
                + "("
                + DbConstant.UserTable.USER_ID + ","
                + DbConstant.UserTable.NAME + ","
                + DbConstant.UserTable.SEX + ","
                + DbConstant.UserTable.AGE
                + ") values(?,?,?,?)";
        String[] params = new String[]{
                bean.getId(),
                bean.getName(),
                bean.getSex(),
                bean.getAge()
        };
        mDbManager.operator(instSql, params);
    }

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     */
    public void delUser(String userId) {
        String delSql = "delete from" + DbConstant.UserTable.TABLE_NAME
                + "where" + DbConstant.UserTable.USER_ID + "=?";
        mDbManager.operator(delSql, new String[]{userId});
    }

    /**
     * 修改用户信息
     *
     * @param userId 用户ID
     * @param name   姓名
     */
    public void updateUserName(String userId, String name) {
        String updateSql = "update" + DbConstant.UserTable.TABLE_NAME
                + "set" + DbConstant.UserTable.NAME + "=?"
                + "where" + DbConstant.UserTable.USER_ID + "=?";
        mDbManager.operator(updateSql, new String[]{name, userId});
    }

    /**
     * 获取用户的姓名
     *
     * @param userId 用户ID
     * @return 姓名
     */
    public String getUserName(String userId) {
        String sql = "select" + DbConstant.UserTable.NAME + "from" + DbConstant.UserTable.TABLE_NAME
                + "where" + DbConstant.UserTable.USER_ID + "=?";
        List<Map> list = mDbManager.query(sql, new String[]{userId}, new String[]{DbConstant.UserTable.NAME.trim()});
        String userName = "";
        if (list != null && list.size() > 0) {
            userName = String.valueOf(list.get(0).get(DbConstant.UserTable.NAME.trim()));
        }
        return userName;
    }
}
