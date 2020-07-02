package com.bhaskar.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.bhaskar.dialogs.BaseDialog;
import com.wang.avi.AVLoadingIndicatorView;

public final class TransAviLoader extends BaseDialog {

    private boolean isCancelOnOutsideTouch = false;
    private AviLoaderIndicator aviLoaderIndicator = AviLoaderIndicator.BallPulseIndicator;
    private AVLoadingIndicatorView loadingIndicatorView;

    private @ColorInt
    int color = 0;

    public TransAviLoader(@NonNull Activity context) {
        super(context, R.style.TransLoader);
        setCancelable(false);
    }

    /**
     * @param context
     * @param isCancelable default is false
     */
    public TransAviLoader(@NonNull Activity context, boolean isCancelable) {
        super(context, R.style.TransLoader);
        setCancelable(isCancelable);
    }

    /***
     *
     * @param colorCode
     */
    public void setLoaderColor(@ColorInt int colorCode) {
        this.color = colorCode;
    }

    /**
     * @param aviLoaderIndicator
     */
    public void setAviLoaderIndicator(@NonNull AviLoaderIndicator aviLoaderIndicator) {
        this.aviLoaderIndicator = aviLoaderIndicator;
    }

    public void setCancelableOnOutSideTouch() {
        isCancelOnOutsideTouch = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans_avi_loader);

        loadingIndicatorView = findViewById(R.id.avi_loading);
        loadingIndicatorView.smoothToShow();

        if (color != 0) {
            loadingIndicatorView.setIndicatorColor(color);
        }

        if (aviLoaderIndicator == null) {
            aviLoaderIndicator = AviLoaderIndicator.BallPulseIndicator;
        }
        loadingIndicatorView.setIndicator(AviLoaderIndicator.getAviLoader(aviLoaderIndicator));

        if (isCancelOnOutsideTouch) {
            findViewById(R.id.fl_loader_frame).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (loadingIndicatorView != null) {
                    loadingIndicatorView.smoothToHide();
                }
            }
        });
    }

    @Override
    public void dismiss() {
        if (loadingIndicatorView != null) {
            loadingIndicatorView.smoothToHide();
        }
        super.dismiss();
    }
}