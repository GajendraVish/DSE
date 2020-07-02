package com.bhaskar.utils;

import android.app.Activity;
import android.app.ProgressDialog;

public class DBProgressDialog extends ProgressDialog {

    protected Activity activityContext;

    public DBProgressDialog(Activity context) {
        super(context);
        this.activityContext = context;
    }

    /**
     * @param context
     * @param isCancelable default is false
     */
    public DBProgressDialog(Activity context, boolean isCancelable) {
        super(context);
        this.activityContext = context;
        setCancelable(isCancelable);
    }

    @Override
    public void show() {
        if (activityContext != null && !activityContext.isFinishing()) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (activityContext != null && !activityContext.isFinishing()) {
            super.dismiss();
        }
    }
}
