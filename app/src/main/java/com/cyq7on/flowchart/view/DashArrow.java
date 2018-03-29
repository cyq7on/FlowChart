package com.cyq7on.flowchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.cyq7on.flowchart.R;
import com.cyq7on.flowchart.utils.PixTool;
import com.orhanobut.logger.Logger;


/**
 * Created by cyq7on on 18-3-28.
 */

public class DashArrow extends View {

    private Context context;

    private float x1 = 0;
    private float y1 = 0;

    private float x2 = 0;
    private float y2 = 0;
    private Paint paint;
    private Path path;

    private void init() {
        // 创建画笔
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimary));  // 设置颜色
        paint.setStrokeWidth(PixTool.dip2px(1));   // 设置宽度
        paint.setAntiAlias(true);   // 抗锯齿
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
    }

    public DashArrow(Context context, float x1, float y1, float x2, float y2) {
        super(context);
        this.context = context;

        this.x1 = x1;
        this.y1 = y1;

        this.x2 = x2;
        this.y2 = y2;
        init();
    }

    public DashArrow(Context context, View startView, View endView) {
        super(context);
        this.context = context;

        int[] location = new int[2];
        startView.getLocationInWindow(location);

        x1 = location[0];
        y1 = location[1] - PixTool.getStatusBarHeight(context) + startView.getHeight() / 2;

        endView.getLocationInWindow(location);

        x2 = location[0] + endView.getWidth() / 2;
//        y2 = location[1] - PixTool.getStatusBarHeight(context) - 53;
        y2 = location[1] - PixTool.getStatusBarHeight(context) + endView.getHeight() / 2;
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.d(x1 + "\n" + y1 + "\n" + x2 + "\n" + y2);

        path.moveTo(x1, y1);

        float quaX = x1 / 4;
        float quaY = (y1 + y2) / 2;
        if (y2 - y1 < 0) {
            quaX = (x1 + x2) / 2;
            quaY = y2 - 100;
        } else if (y2 - y1 < 50) {
            quaX = (x1 + x2) / 2;
            quaY = y1 - 50;
        }
        path.quadTo(quaX, quaY, x2, y2);

        canvas.drawPath(path, paint);

        float length = 32;  // 三角形的边长
        float x = x2 - length / 2;
        paint.setStyle(Paint.Style.FILL); //设置填满
        path.reset();

        // 画三角形
        path.moveTo(x, y2);
        path.lineTo(x + length, y2);
        path.lineTo((x + x + length) / 2, y2 + 23);
        path.close();

        canvas.drawPath(path, paint);
    }

}

