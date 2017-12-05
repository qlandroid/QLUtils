package com.ql.utils.qlutils.function;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;

import com.ql.utils.qlutils.R;
import com.ql.view.Layout;
import com.ql.view.base.QLBindLayoutActivity;

import java.io.IOException;

@Layout(value = R.layout.activity_vibration, title = "震动调试")
public class VibrationActivity extends QLBindLayoutActivity {

    private Vibrator vibrator;
    MediaPlayer mp;

    @Override
    public void initData() {
        super.initData();
        createVibration();
        mp = MediaPlayer.create(this, R.raw.error);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

    }

    public void testOneVibration(View view) {
        vibrator.vibrate(500);
        play();
    }


    private void play() {
        if (mp.isPlaying()) {
            mp.seekTo(0);
        } else {
            mp.start();
        }
    }

    private void createVibration() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }
}
