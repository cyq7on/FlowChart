package com.cyq7on.flowchart;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cyq7on.flowchart.view.DashArrow;
import com.cyq7on.flowchart.view.DragViewGroup;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private DragViewGroup dragViewGroup;

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
                int childCount = dragViewGroup.mMoveLayoutList.size();
                DashArrow dashArrow;
                for (int i = 0; i < childCount - 1; i++) {
                    View childAt1 = dragViewGroup.mMoveLayoutList.get(i);
                    View childAt2 = dragViewGroup.mMoveLayoutList.get(i + 1);

                    dashArrow = new DashArrow(getApplicationContext(),
                            childAt1.getLeft() + childAt1.getWidth() / 2, childAt1.getBottom(),
                            childAt2.getLeft() + childAt1.getWidth() / 2,childAt2.getTop());
                    dashArrow.setTag(true);
                    dragViewGroup.addView(dashArrow);
                }

            }
        });

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
