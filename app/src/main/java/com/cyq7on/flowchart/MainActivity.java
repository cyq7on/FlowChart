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

    private DragViewGroup linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.addLogAdapter(new AndroidLogAdapter());
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        linearLayout = findViewById(R.id.ll);
        final ImageView rhombus = findViewById(R.id.ivRhombus);
        final ImageView rectangle = findViewById(R.id.ivRectangle);
        final ImageView circle = findViewById(R.id.ivCircle);
        Button btnConnect = findViewById(R.id.btnConnect);
        rhombus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createView(view,R.drawable.rhombus);
            }
        });
        rectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createView(view,R.drawable.rectangle);
            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createView(view,R.drawable.circle);
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int childCount = linearLayout.getChildCount();
//                for (int i = 0; i < childCount - 1; i++) {
//                for (int i = 0; i < 1; i++) {
//                    View childAt1 = linearLayout.getChildAt(i);
//                    View childAt2 = linearLayout.getChildAt(i + 1);
//                    DashArrow dashArrow = new DashArrow(getApplicationContext(), childAt1, childAt2);
//                    linearLayout.addView(dashArrow);
//                }

                linearLayout.addView(new DashArrow(getApplicationContext(), rhombus, rectangle));
                linearLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayout.addView(new DashArrow(getApplicationContext(), rectangle, circle));
                    }
                },1000);

            }
        });

    }

    private void createView(View view,int ResId) {
        ImageView dragView = new ImageView(view.getContext());
        dragView.setImageResource(ResId);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(view.getWidth(),view.getHeight());
        dragView.setLayoutParams(p);
        linearLayout.addDragView(dragView,view.getLeft(),view.getTop(),view.getRight(),view.getBottom()
        ,true,false);
//        linearLayout.addView(dragView,p);
//        linearLayout.addView(dragView);
//        linearLayout.addDragView(dragView.getId());
    }

    private void showShortToast(CharSequence text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

}
