package com.cyq7on.flowchart;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cyq7on.flowchart.utils.PixTool;
import com.cyq7on.flowchart.view.DashArrow;
import com.cyq7on.flowchart.view.DragViewGroup;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private DragViewGroup dragViewGroup;
    private EditText etCmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.addLogAdapter(new AndroidLogAdapter());
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        dragViewGroup = findViewById(R.id.dragViewGroup);
        final ImageView rhombus = findViewById(R.id.ivRhombus);
        final ImageView rectangle = findViewById(R.id.ivRectangle);
        final ImageView circle = findViewById(R.id.ivCircle);
        Button btnConnect = findViewById(R.id.btnConnect);
        Button btnClear = findViewById(R.id.btnClear);
        etCmd = findViewById(R.id.etCmd);
        rhombus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createView(view, R.drawable.rhombus);
            }
        });
        rectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createView(view, R.drawable.rectangle);
            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createView(view, R.drawable.circle);
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawLine();
                /*int childCount = dragViewGroup.mMoveLayoutList.size();
                DashArrow dashArrow;
                for (int i = 0; i < childCount - 1; i++) {
                    View childAt1 = dragViewGroup.mMoveLayoutList.get(i);
                    View childAt2 = dragViewGroup.mMoveLayoutList.get(i + 1);
                    Context context = getApplicationContext();
                    float statusBarHeight = PixTool.getStatusBarHeight(context);
                    dashArrow = new DashArrow(context,
                            childAt1.getLeft() + childAt1.getWidth() / 2,
                            childAt1.getBottom() - statusBarHeight,
                            childAt2.getLeft() + childAt1.getWidth() / 2,childAt2.getTop());
                    dragViewGroup.addView(dashArrow);
                    //bug
//                    dragViewGroup.addDragView(dashArrow, dashArrow.getLeft(), dashArrow.getTop(),
//                            dashArrow.getRight(), dashArrow.getBottom(), false, false);
                }
*/
            }
        });


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragViewGroup.removeAllViews();
                dragViewGroup.mMoveLayoutList.clear();
            }
        });

    }

    private void drawLine() {
        int size = dragViewGroup.mMoveLayoutList.size();
        if (size < 2) {
            showShortToast("请至少选择两个对象");
            return;
        }
        String cmd = etCmd.getText().toString();
        if (cmd.length() < 2 || cmd.length() > 5) {
            showShortToast("指令长度非法");
            return;
        }
        char[] chars = cmd.toCharArray();
        DashArrow dashArrow;
        Context context = getApplicationContext();
        float statusBarHeight = PixTool.getStatusBarHeight(context);
        for (int i = 0; i < Math.min(chars.length, size) - 1; i++) {
            int index1 = chars[i] - 'a';
            int index2 = chars[i + 1] - 'a';
//            if(index2 > size - 1){
//                return;
//            }
            if (index1 < 0 || index1 > 2 || index2 < 0 || index2 > 2) {
                showShortToast("指令格式非法");
                return;
            }

            View childAt1 = dragViewGroup.mMoveLayoutList.get(index1);
            View childAt2 = dragViewGroup.mMoveLayoutList.get(index2);

            dashArrow = new DashArrow(context,
                    childAt1.getLeft() + childAt1.getWidth() / 2,
                    childAt1.getBottom() - statusBarHeight,
                    childAt2.getLeft() + childAt1.getWidth() / 2, childAt2.getTop());
            dragViewGroup.addView(dashArrow);
        }

        //处理闭环
        if (chars.length > size && chars[chars.length - 1] == chars[0]) {
            View childAt1 = dragViewGroup.mMoveLayoutList.get(chars[chars.length - 2] - 'a');
            View childAt2 = dragViewGroup.mMoveLayoutList.get(chars[0] - 'a');
            float y1 = childAt1.getBottom() - childAt1.getHeight() / 2 ;
            float y2 = childAt2.getBottom() - childAt1.getHeight() / 2 ;
            float margin = getResources().getDimension(R.dimen.line_margin);
            dashArrow = new DashArrow(context, childAt1.getRight() - margin,
                    y1, childAt2.getRight() - margin, y2);
            dashArrow.setNeedBezier(true);
            dragViewGroup.addView(dashArrow);
        }
    }

    private void createView(View view, int ResId) {
        ImageView imageView = new ImageView(view.getContext());
        imageView.setImageResource(ResId);
        //DragView
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(view.getWidth(), view.getWidth());
        imageView.setLayoutParams(p);
        dragViewGroup.addDragView(imageView, view.getLeft(), view.getTop(), view.getRight(), view.getBottom()
                , false, false);
    }

    private void showShortToast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
