package com.ql.utils.qlutils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ql.view.Layout;
import com.ql.view.base.QLBindLayoutActivity;
import com.ql.view.bind.BindView;
import com.ql.view.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author android
 */
@Layout(R.layout.activity_test)
public class TestaActivity extends QLBindLayoutActivity {

    @BindView(R.id.activity_test_btn)
    Button btn;
    @BindView(R.id.activity_test_et_account)
    EditText etAccount;
    @BindView(R.id.activity_test_et_pw)
    EditText etPw;
    @BindView(R.id.activity_test_lv)
    ListView lv;


    @Override
    public void initView() {
        super.initView();
        btn.setOnClickListener(this);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("item" + i);
        }
        BaseAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog ad = new AlertDialog.Builder(TestaActivity.this).setMessage(String.format("被点击了%d", position)).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        });
    }

    @Override
    public void clickWidget(View v) {
        super.clickWidget(v);
        switch (v.getId()) {
            case R.id.activity_test_btn:
                String pw = etPw.getText().toString();
                String account = etAccount.getText().toString();

                AlertDialog ad = new AlertDialog.Builder(this).setMessage(String.format("账号:%s,密码:%s", account, pw)).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
                break;
            default:
        }
    }
}
