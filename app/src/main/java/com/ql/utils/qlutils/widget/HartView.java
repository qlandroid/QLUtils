package com.ql.utils.qlutils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by android on 2017/11/16.
 */

public class HartView extends View {
    private Path path;
    private Paint paint;

    public HartView(Context context) {
        this(context, null);
    }

    public HartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        path = new Path();
        paint = new Paint();

        paint.setColor(Color.RED);
        // 使用 path 对图形进行描述（这段描述代码不必看懂）
        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成

        Shader shader = new LinearGradient(100, 100, 500, 500, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawCircle(0, 300, 100, paint);
    }


}
