package com.cyq7on.flowchart.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;


/**
 *自定义的view，能够随意拖动。
 *
 */

@Deprecated

public class DragView extends ImageView {
    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private float downX;
    private float downY;
    private int width,height;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (this.isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    width = getWidth();
                    height = getHeight();
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float xDistance = event.getX() - downX;
                    final float yDistance = event.getY() - downY;
                    if (xDistance != 0 && yDistance != 0) {
                        int l = (int) (getLeft() + xDistance);
                        int r = (int) (getRight() + xDistance);
                        int t = (int) (getTop() + yDistance);
                        int b = (int) (getBottom() + yDistance);
                        /**
                         * 不能使用layout()方法，来改变位置。
                         * layout()虽然可以改变控件的位置， 但不会将位置信息保存到LayoutParams中。
                         * 而调用addView往布局添加新的控件时，是根据LayoutParams来重新布局控件位置的。
                         * 这里需要用另一种方法：先获取控件的LayoutParams，改变其中相关的值后，再设置回去。
                         */
//                        this.layout(l, t, r, b);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                        params.setMargins(l,t,r,b);
                        params.width = width;
                        params.height = height;
                        Logger.d(width +"\n" +height);
                        setLayoutParams(params);
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    setPressed(false);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    setPressed(false);
                    break;
            }
            return true;
        }
        return false;
    }

}
