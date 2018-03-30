package com.cyq7on.flowchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyq7on.flowchart.R;
import com.cyq7on.flowchart.utils.PixTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert on 2017/6/21.
 */

/**
 * @Description: optimize it
 * @author: cyq7on
 * @date: 18-3-30 上午11:15
 * @version: V2.0
 */

public class DragViewGroup extends RelativeLayout implements MoveLayout.DeleteMoveLayout {

    private static final String TAG = "DragViewGroup";

    private int mSelfViewWidth = 0;
    private int mSelfViewHeight = 0;

    private Context mContext;

    /**
     * the identity of the moveLayout
     */
    private int mLocalIdentity = 0;

    public List<MoveLayout> mMoveLayoutList;

    /*
    * 拖拽框最小尺寸
    */
    private int mMinHeight = 120;
    private int mMinWidth = 180;

    private boolean mIsAddDeleteView = false;
    private TextView deleteArea;

    private int DELETE_AREA_WIDTH = 180;
    private int DELETE_AREA_HEIGHT = 90;


    public DragViewGroup(Context context) {
        super(context);
        init(context, this);
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, this);
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, this);
    }

    private void init(Context c, DragViewGroup thisp) {
        mContext = c;
        mMoveLayoutList = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //  Log.e(TAG, "onDraw: height=" + getHeight());
        mSelfViewWidth = getWidth();
        mSelfViewHeight = getHeight();

        if (mMoveLayoutList != null) {
            int count = mMoveLayoutList.size();
            for (int a = 0; a < count; a++) {
                mMoveLayoutList.get(a).setViewWidthHeight(mSelfViewWidth, mSelfViewHeight);
                mMoveLayoutList.get(a).setDeleteWidthHeight(DELETE_AREA_WIDTH, DELETE_AREA_HEIGHT);
            }
        }

    }

    /**
     * call set Min height before addDragView
     *
     * @param height
     */
    private void setMinHeight(int height) {
        mMinHeight = height;
    }

    /**
     * call set Min width before addDragView
     *
     * @param width
     */
    private void setMinWidth(int width) {
        mMinWidth = width;
    }

    public void addDragView(View selfView, int left, int top, int right, int bottom, boolean isFixedSize, boolean whitebg) {
        addDragView(selfView, left, top, right, bottom, isFixedSize, whitebg, mMinWidth, mMinHeight);
    }

    /**
     * 每个moveLayout都可以拥有自己的最小尺寸
     */
    public void addDragView(int resId, int left, int top, int right, int bottom, boolean isFixedSize, boolean whitebg, int minwidth, int minheight) {
        LayoutInflater inflater2 = LayoutInflater.from(mContext);
        View selfView = inflater2.inflate(resId, null);
        addDragView(selfView, left, top, right, bottom, isFixedSize, whitebg, minwidth, minheight);
    }

    /**
     * 每个moveLayout都可以拥有自己的最小尺寸
     */
    public void addDragView(View selfView, int left, int top, int right, int bottom, boolean isFixedSize, boolean whitebg, int minwidth, int minheight) {
        //    invalidate();
        //  Log.e(TAG, "addDragView: height="+getHeight() +"   width+"+ getWidth() );

        MoveLayout moveLayout = new MoveLayout(mContext);

        moveLayout.setClickable(true);
        moveLayout.setMinHeight(minheight);
        moveLayout.setMinWidth(minwidth);
        int tempWidth = right - left;
        int tempHeight = bottom - top;
        if (tempWidth < minwidth) tempWidth = minwidth;
        if (tempHeight < minheight) tempHeight = minheight;

        //set postion
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(tempWidth, tempHeight);
        lp.setMargins(left, top, 0, 0);
        moveLayout.setLayoutParams(lp);

        //add sub view (has click indicator)
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dragSubView = inflater.inflate(R.layout.drag_sub_view, null);
        LinearLayout addYourViewHere = dragSubView.findViewById(R.id.add_your_view_here);
        LinearLayout.LayoutParams lv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addYourViewHere.addView(selfView, lv);

        if (whitebg) {
            LinearLayout changeBg = dragSubView.findViewById(R.id.change_bg);
            changeBg.setBackgroundResource(R.drawable.corners_bg2);
        }

        moveLayout.addView(dragSubView);

        //set fixed size
        moveLayout.setFixedSize(isFixedSize);

        moveLayout.setOnDeleteMoveLayout(this);
        moveLayout.setIdentity(mLocalIdentity++);

        if (!mIsAddDeleteView) {
            //add delete area
            deleteArea = new TextView(mContext);
            deleteArea.setText("删除");
            deleteArea.setTextColor(Color.WHITE);
            int px = (int) PixTool.dip2px(3);
            deleteArea.setPadding(2 * px, px, px, 2 * px);
            deleteArea.setBackgroundColor(Color.RED);
            RelativeLayout.LayoutParams delLayout = new RelativeLayout.
                    LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            delLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            delLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            deleteArea.setLayoutParams(delLayout);
            deleteArea.setGravity(Gravity.CENTER);
            // moveLayout.setDeleteWidthHeight(180, 90);
            deleteArea.setVisibility(View.INVISIBLE);
            addView(deleteArea);
        }

        //set view to get control
        moveLayout.setDeleteView(deleteArea);

        addView(moveLayout);

        mMoveLayoutList.add(moveLayout);
    }

    public void addDragView(int resId, int left, int top, int right, int bottom, boolean isFixedSize, boolean whitebg) {
        LayoutInflater inflater2 = LayoutInflater.from(mContext);
        View selfView = inflater2.inflate(resId, null);
        addDragView(selfView, left, top, right, bottom, isFixedSize, whitebg);
    }

    @Override
    public void onDeleteMoveLayout(int identity) {
        int count = mMoveLayoutList.size();
        for (int a = 0; a < count; a++) {
            if (mMoveLayoutList.get(a).getIdentity() == identity) {
                //delete
                removeView(mMoveLayoutList.get(a));
            }
        }
    }

//
//    /**
//     * delete interface
//     */
//    private DeleteDragView mListener = null;
//    public interface DeleteDragView {
//        void onDeleteDragView();
//    }
//    public void setOnDeleteDragView(DeleteDragView l) {
//        mListener = l;
//    }


}