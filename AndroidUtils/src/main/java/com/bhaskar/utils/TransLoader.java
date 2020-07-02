package com.bhaskar.utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.bhaskar.dialogs.BaseDialog;

public final class TransLoader extends BaseDialog {

    private boolean isCancelOnOutsideTouch = false;

    public TransLoader(Activity context) {
        super(context, R.style.TransLoader);
        setCancelable(false);
    }

    /**
     * @param context
     * @param isCancelable default is false
     */
    public TransLoader(Activity context, boolean isCancelable) {
        super(context, R.style.TransLoader);
        setCancelable(isCancelable);
    }

    public void setCancelableOnOutSideTouch() {
        isCancelOnOutsideTouch = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans_loader);
        findViewById(R.id.fl_loader_frame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCancelOnOutsideTouch) {
                    dismiss();
                }
            }
        });
    }
}