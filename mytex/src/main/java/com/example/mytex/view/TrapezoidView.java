package com.example.mytex.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.mytex.utils.AppUtil;

/**
 * Created by BoyLucky on 2018/5/30.
 */

public class TrapezoidView extends ViewGroup {
    public TrapezoidView(Context context) {
        this(context, null);
    }

    public TrapezoidView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrapezoidView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int totalHeight = 0;//此控件的高度
        int totalWidth = 0;//此控件的宽度
        int childCount = getChildCount();
        if (childCount>0){
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                totalHeight += childAt.getHeight();
                measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            }
        }
        totalWidth = AppUtil.screenWidth(getContext());
        setMeasuredDimension(totalWidth, totalHeight);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        left = 0;
        top = 0;
//        right = 0;
//        bottom = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.layout(left, top, left + childAt.getMeasuredWidth(), top + childAt.getMeasuredHeight());
            left+=childAt.getMeasuredWidth();
            top+= childAt.getMeasuredHeight();
            if (left+childAt.getMeasuredWidth() > AppUtil.screenWidth(getContext())){
                left = 0;
            }
        }
    }
}
