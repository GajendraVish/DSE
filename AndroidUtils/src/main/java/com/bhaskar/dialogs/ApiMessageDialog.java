package com.bhaskar.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bhaskar.utils.R;

import androidx.annotation.NonNull;


public final class ApiMessageDialog extends BaseDialog {

    private final String TAG = "ApiMessageDialog";

    private String title, message;


    /***
     *
     * @param activity
     * @param title
     * @param message
     */
    public ApiMessageDialog(@NonNull Activity activity, String title, String message) {
        super(activity, R.style.TransLoader);
        this.title = title;
        this.message = message;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_api_message);
        TextView messageTv = findViewById(R.id.tv_alert_message);
        TextView titleTv = findViewById(R.id.tv_alert_title);
        titleTv.setText(title);
        messageTv.setText(message);
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (activityContext != null && !activityContext.isFinishing())
                    activityContext.finish();
            }
        });
    }
}
