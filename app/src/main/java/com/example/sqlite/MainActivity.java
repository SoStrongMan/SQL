package com.example.sqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.sqlite.bean.UserBean;
import com.example.sqlite.db.UserDB;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private UserDB mUserDB;
    private UserBean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mUserDB = new UserDB();
        mBean = new UserBean();

        mBean.setId("1");
        mBean.setName("张三");
        mBean.setSex("男");
        mBean.setAge("20");
        mUserDB.initUser(mBean);
    }

    @OnClick({R.id.btn_add, R.id.btn_delete, R.id.btn_update, R.id.btn_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                mBean.setId("2");
                mBean.setName("李四");
                mBean.setSex("女");
                mBean.setAge("19");
                mUserDB.addUser(mBean);
                break;
            case R.id.btn_delete:
                mUserDB.delUser("2");
                break;
            case R.id.btn_update:
                mUserDB.updateUserName("2", "王五");
                break;
            case R.id.btn_query:
                Toast.makeText(this, mUserDB.getUserName("2"), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
