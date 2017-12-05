package com.ql.utils.qlutils.function;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ql.utils.qlutils.R;
import com.ql.utils.qlutils.widget.HeartSurfaceView;
import com.ql.view.base.QLActivity;
import com.ql.view.bind.BindView;

public class HartSurfaceActivity extends QLActivity {
    @BindView(R.id.heart_surface)
    HeartSurfaceView surfaceView;
    @BindView(R.id.tv_width)
    TextView tvWidth;
    @BindView(R.id.seekBar_x)
    SeekBar seekBarX;
    @BindView(R.id.seekBar_y)
    SeekBar seekBarY;
    @BindView(R.id.tv_seekbar_x)
    TextView tvSeekBarX;
    @BindView(R.id.tv_seekbar_y)
    TextView tvSeekBarY;

    @Override
    protected void createView() {
        setContentView(R.layout.activity_hart_surface);
    }

    @Override
    public void initView() {
        super.initView();
        seekBarX.setMax(1000);
        seekBarX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float scale = progress * 0.1f;
                surfaceView.setxScale(scale);
                tvSeekBarX.setText(String.valueOf(scale));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarY.setMax(1000);
        seekBarY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float scale = progress * 0.1f;
                surfaceView.setyScale(scale);
                tvSeekBarY.setText(String.valueOf(scale));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void open(View view) {
        surfaceView.setDrawing(true);
    }

    public void reset(View view) {
        surfaceView.reset();
    }
}
