package com.cyq7on.flowchart;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cyq7on.flowchart.view.DragView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

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
        ImageView rhombus = findViewById(R.id.ivRhombus);
        ImageView rectangle = findViewById(R.id.ivRectangle);
        ImageView circle = findViewById(R.id.ivCircle);
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

    }

    private void createView(View view,int ResId) {
        ImageView imageView = new DragView(view.getContext());
        imageView.setImageResource(ResId);
        ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(view.getWidth(),view.getHeight());
        linearLayout.addView(imageView,p);
    }

    private void showShortToast(CharSequence text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

}
