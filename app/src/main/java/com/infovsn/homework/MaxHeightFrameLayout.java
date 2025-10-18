package com.infovsn.homework;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class MaxHeightFrameLayout extends FrameLayout {
    private int maxHeightPx = 0;

    public MaxHeightFrameLayout(Context context) { super(context); }
    public MaxHeightFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public MaxHeightFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightFrameLayout);
        try {
            maxHeightPx = a.getDimensionPixelSize(R.styleable.MaxHeightFrameLayout_maxHeight, 0);
        } finally {
            a.recycle();
        }
    }

    public void setMaxHeightPx(int px) {
        this.maxHeightPx = px;
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (maxHeightPx > 0) {
            int mode = MeasureSpec.getMode(heightMeasureSpec);
            int size = MeasureSpec.getSize(heightMeasureSpec);
            int capped = Math.min(size, maxHeightPx);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(capped, mode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

