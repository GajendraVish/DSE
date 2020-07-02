package com.bhaskar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class AndWebView extends WebView {

    public AndWebView(Context context) {
        super(context);
    }

    public AndWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AndWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AndWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /*@Override
    public boolean onCheckIsTextEditor() {
        return true;
    }*/
}
