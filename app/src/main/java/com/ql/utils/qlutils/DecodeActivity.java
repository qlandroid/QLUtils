package com.ql.utils.qlutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.ql.view.base.QLActivity;
import com.ql.view.bind.BindView;
import com.ql.view.utils.ToastUtils;

public class DecodeActivity extends QLActivity {

    @BindView(R.id.activity_decode_code)
    TextView tvCode;
    @BindView(R.id.activity_decode_et)
    EditText et;

    private StringBuffer sb = new StringBuffer();

    @Override
    protected void createView() {
        setContentView(R.layout.activity_decode);
    }

    @Override
    public void initData() {
        super.initData();
        sb.append("初始化").append("\r\n");
        registerReceivers();
    }

    @Override
    public void initView() {
        super.initView();
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sb.append("onTextChanged").append("--->").append("s = ").append(s).append(",start = ").append(start).append(",before = ").append(before).append(",count = ").append(count).append("\r\n");
                tvCode.setText(sb);
            }

            @Override
            public void afterTextChanged(Editable s) {
                sb.append("afterTextChanged =").append(s).append("\r\n");
                tvCode.setText(sb);
            }
        });
    }

    void registerReceivers() {
        sb.append("绑定广播接收者").append("\r\n");
        tvCode.setText(sb.toString());
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.symbol.datawedge.api.CREATE_PROFILE");
        filter.addAction("com.symbol.datawedge.api.RESULT_ACTION");
        filter.addCategory("android.intent.category.DEFAULT");
        registerReceiver(myBroadcastReceiver, filter);
    }

//Receiving the result

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            sb.append("接收到广播").append("\r\n");
            Bundle extras = getIntent().getExtras();
            if (intent.hasExtra("com.symbol.datawedge.api.RESULT_GET_ACTIVE_PROFILE")) {
                String activeProfile = extras.getString("com.symbol.datawedge.api.RESULT_GET_ACTIVE_PROFILE");
                String command_identifier = extras.getString("COMMAND_IDENTIFIER");

                String DATA_STRING_TAG = "com.motorolasolutions.emdk.datawedge.data_string";
                String data = extras.getString(DATA_STRING_TAG
                );
                sb.append("接收到信息--->").append(activeProfile).append("\r\n")
                        .append(command_identifier).append("\r\n").append(data);
            }
            tvCode.setText(sb);
        }
    };

}
