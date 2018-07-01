package com.example.mytex.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mytex.R;
import com.example.mytex.utils.AppUtil;

public class TrapezoidActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * +
     */
    private Button mBut;
    private TrapezoidView mTView;
    private int count = 0;
    private int[] color = {Color.BLUE,Color.GRAY,Color.GREEN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trapezoid);
        initView();

    }

    private void initView() {
        mBut = (Button) findViewById(R.id.but);
        mBut.setOnClickListener(this);
        mTView = (TrapezoidView) findViewById(R.id.tView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.but:
                add(v);

                break;
        }
    }

    private void add(View view) {
        ++count;
        int width = AppUtil.screenWidth(this);
        final TextView textView = new TextView(this);
        textView.setText(count+"");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);

        if (count%3 == 0){
            textView.setBackgroundColor(color[0]);
        }else if (count%3 == 1){
            textView.setBackgroundColor(color[1]);
        }else if (count%3 == 2){
            textView.setBackgroundColor(color[2]);
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "alpha",  0f, 1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView,"translationX",(width-width/3),0);
        AnimatorSet set = new AnimatorSet();
    //同时沿X,Y轴放大，且改变透明度，然后移动
        set.play(animator).with(objectAnimator);
    //都设置3s，也可以为每个单独设置
        set.setDuration(3000);
        set.start();

        mTView.addView(textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = textView.getText().toString();
                Intent intent = new Intent(TrapezoidActivity.this, MainActivity.class);
                intent.putExtra("id",string);
                startActivity(intent);
            }
        });

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mTView.removeView(textView);
                return true;

            }
        });

        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        layoutParams.width = width/3;
        layoutParams.height = 70;
        textView.setLayoutParams(layoutParams);

    }
}
