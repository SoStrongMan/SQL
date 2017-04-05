# SQL（数据库的增删改查操作）
## 一、创建DbHelper类，用来创建或者更新数据库
    public DbHelper(Context context) {
        super(context, DbConstant.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库，具体的创建语句在DbConstant类中
        db.execSQL(DbConstant.CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新数据库
    }
    
## 二、DbManager类用来管理数据库
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
     * 数据库操作完毕后，记得关闭数据库
     */
    private synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            mDatabase.close();
        }
    }
    
 ## 数据库操作（以删除为例）
 先在UserDB类中创建删除的方法
    public void delUser(String userId) {
        String delSql = "delete from" + DbConstant.UserTable.TABLE_NAME
                + "where" + DbConstant.UserTable.USER_ID + "=?";
        mDbManager.operator(delSql, new String[]{userId});
    }
 之后在Activity中调用：
    mUserDB.delUser("2");
