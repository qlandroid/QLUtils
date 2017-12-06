package com.ql.utils.qlutils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ql.utils.qlutils.function.AutoTextActivity;
import com.ql.utils.qlutils.function.DataPickerActivity;
import com.ql.utils.qlutils.function.ExpandableTextViewActivity;
import com.ql.utils.qlutils.function.HartSurfaceActivity;
import com.ql.utils.qlutils.function.HartViewActivity;
import com.ql.utils.qlutils.function.HelloChartsActivity;
import com.ql.utils.qlutils.function.MoveModuleActivity;
import com.ql.utils.qlutils.function.NavActivity;
import com.ql.utils.qlutils.function.RecyclerViewActivity;
import com.ql.utils.qlutils.function.SystemBarActivity;
import com.ql.utils.qlutils.function.TagGroupLayoutActivity;
import com.ql.utils.qlutils.function.VibrationActivity;
import com.ql.view.adapter.CommonAdapter;
import com.ql.view.base.QLActivity;
import com.ql.view.bind.BindView;

import java.util.ArrayList;

public class MainActivity extends QLActivity {


    @BindView(R.id.lv)
    ListView lv;

    private ArrayList<AtyBean> atyList;
    private CommonAdapter<AtyBean> mAdapter;

    @Override
    protected void createView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initData() {
        super.initData();
        atyList = new ArrayList<>();
        addActivity();
        mAdapter = new CommonAdapter<AtyBean>(this, atyList, android.R.layout.simple_list_item_1) {
            @Override
            public void setItemContent(ViewHolder holder, AtyBean s, int position) {
                holder.setText(android.R.id.text1, s.getName());
            }
        };
    }

    @Override
    public void initView() {
        super.initView();
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(atyList.get(position).getClazzName());
            }
        });
    }

    private void addActivity() {
        atyList.add(new AtyBean(SystemBarActivity.class));
        atyList.add(new AtyBean(NavActivity.class));
        atyList.add(new AtyBean(TagGroupLayoutActivity.class));
        atyList.add(new AtyBean(AutoTextActivity.class));
        atyList.add(new AtyBean(RecyclerViewActivity.class));
        atyList.add(new AtyBean(DataPickerActivity.class));
        atyList.add(new AtyBean(HartViewActivity.class));
        atyList.add(new AtyBean(VibrationActivity.class));
        atyList.add(new AtyBean(DecodeActivity.class));
        atyList.add(new AtyBean(HelloChartsActivity.class));
        atyList.add(new AtyBean(ExpandableTextViewActivity.class));
        atyList.add(new AtyBean(MoveModuleActivity.class));


    }

    public static class AtyBean {
        private String name;
        private String clazzName;

        public AtyBean(Class<? extends QLActivity> aty) {
            name = aty.getSimpleName();
            clazzName = aty.getCanonicalName();
        }

        public AtyBean(String name, String clazzName) {
            this.name = name;
            this.clazzName = clazzName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClazzName() {
            return clazzName;
        }

        public void setClazzName(String clazzName) {
            this.clazzName = clazzName;
        }
    }
}
