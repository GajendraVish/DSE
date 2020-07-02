package com.bhaskar.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;

import androidx.annotation.NonNull;

public abstract class BaseDialog extends Dialog {

    protected Activity activityContext;
    private static final String TAG = BaseDialog.class.getSimpleName();

    /**
     * @param activityContext
     */
    public BaseDialog(@NonNull Activity activityContext) {
        super(activityContext);
        this.activityContext = activityContext;
    }

    /**
     * @param activityContext
     * @param themeResId
     */
    public BaseDialog(@NonNull Activity activityContext, int themeResId) {
        super(activityContext, themeResId);
        this.activityContext = activityContext;
    }

    @Override
    public void show() {
        try {
            if (activityContext != null && !activityContext.isFinishing()) {
                super.show();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "");
        }
    }

    @Override
    public void dismiss() {
        try {
            if (activityContext != null && !activityContext.isFinishing()) {
                super.dismiss();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "");
        }
    }
//7566845855
}
