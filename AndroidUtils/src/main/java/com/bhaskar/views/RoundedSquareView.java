package com.bhaskar.views;

import android.content.Context;
import android.util.AttributeSet;

import com.makeramen.roundedimageview.RoundedImageView;

public class RoundedSquareView extends RoundedImageView {
    public RoundedSquareView(Context context) {
        super(context);
    }

    public RoundedSquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedSquareView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
