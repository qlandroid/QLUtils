package com.ql.utils.qlutils.function;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ql.utils.qlutils.R;
import com.ql.utils.qlutils.move.CanMoveSurfaceView;
import com.ql.utils.qlutils.move.Module;
import com.ql.view.Layout;
import com.ql.view.base.QLBindLayoutActivity;
import com.ql.view.bind.BindView;

import java.util.ArrayList;

@Layout(R.layout.activity_move_module)
public class MoveModuleActivity extends QLBindLayoutActivity {
    @BindView(R.id.can_move_surface_view)
    CanMoveSurfaceView canMoveSurfaceView;
    int[] colors = {Color.YELLOW, Color.BLACK, Color.BLUE};
    int colorIndex = 0;

    @Override
    public void initView() {
        super.initView();

        addModule();

    }

    private void addModule() {

        canMoveSurfaceView.addModules(createModule(12, 12, 100, 100));
        canMoveSurfaceView.addModules(createModule(90, 90, 300, 300));
        canMoveSurfaceView.addModules(createModule(102, 130, 500, 500));


    }

    private Module createModule(int x, int y, int endx, int endy) {
        Module module = new Module();
        module.setX(x);
        module.setEndX(endx);
        module.setY(y);
        module.setEndY(endy);
        if (colorIndex == colors.length) {
            colorIndex = 0;
        }
        module.setColor(colors[colorIndex]);
        colorIndex++;
        return module;
    }
}
