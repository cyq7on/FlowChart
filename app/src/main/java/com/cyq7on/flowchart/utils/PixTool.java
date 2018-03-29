package com.cyq7on.flowchart.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by cyq7on on 18-3-28.
 */

public class PixTool {
    public static float dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }

    public static float getStatusBarHeight(Context context) {
        float statusBarHeight = (float) Math.ceil(25 * context.getResources().getDisplayMetrics().density);
        return statusBarHeight;
    }
}
