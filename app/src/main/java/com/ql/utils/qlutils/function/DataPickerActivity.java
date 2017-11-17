package com.ql.utils.qlutils.function;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.DatePicker;

import com.ql.utils.qlutils.R;
import com.ql.view.base.QLActivity;
import com.ql.view.bind.BindView;

import java.util.Calendar;
import java.util.Date;

public class DataPickerActivity extends QLActivity {

    @BindView(R.id.date_picker)
    DatePicker datePicker;
    @Override
    protected void createView() {
        setContentView(R.layout.activity_data_picker);
    }


    @Override
    public void initView() {
        super.initView();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,3);
        datePicker.setMaxDate(calendar.getTimeInMillis());
        datePicker.setMinDate(System.currentTimeMillis());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickDateSelect(View view) {
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        },2013,07,20);

        mDatePickerDialog.show();
    }
}
