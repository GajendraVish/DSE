package com.bhaskar.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.nfc.cardemulation.CardEmulation;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bhaskar.constants.AndroidConstants;
import com.bhaskar.utils.R;

import java.util.concurrent.TimeUnit;

public final class ApiLoadingDialog extends BaseDialog implements View.OnClickListener, DialogInterface.OnDismissListener {

    private final String TAG = "ApiLoadingDialog";

    private TextView messageTv, timerTv;

    private ProgressBar progressBar;
    private ViewGroup timerContainerView;

    private TextView retryBtn;
    private int apiCount = 0;
    private CountDownTimer countDownTimer;
    private long timerMilliSecond = 0;
    private ApiLoadingInterface apiLoadingInterface;

    /***
     *
     * @param activity
     * @param apiLoadingInterface
     */
    public ApiLoadingDialog(@NonNull Activity activity, ApiLoadingInterface apiLoadingInterface) {
        super(activity, R.style.TransLoader);
        this.apiLoadingInterface = apiLoadingInterface;
        setOnDismissListener(this);
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_api_loading);
        messageTv = findViewById(R.id.tv_alert_message);
        timerTv = findViewById(R.id.tv_request_timer);
        progressBar = findViewById(R.id.pbar_request);
        progressBar.setVisibility(View.VISIBLE);
        timerContainerView = findViewById(R.id.ll_timer_parent);

        retryBtn = findViewById(R.id.btn_retry);
        TextView cancelBtn = findViewById(R.id.btn_cancel);

        retryBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        retryBtn.setAlpha(0.5f);
        retryBtn.setEnabled(false);
        retryBtn.setVisibility(View.GONE);

        apiLoadingInterface.makeApiCall();
        apiCount = 1;
    }

    private void doOnPermanentFailure() {
        apiLoadingInterface.onPermanentFailure();
        dismiss();
    }

    public void handleApiFailure() {
        if (apiCount < AndroidConstants.MAX_API_COUNT) {
            progressBar.setVisibility(View.GONE);
            showCountDownTimer();
        } else {
            doOnPermanentFailure();
        }
    }

    private void showCountDownTimer() {

        if (apiCount == 1) {
            timerMilliSecond = AndroidConstants.TIMER_MILLISECONDS_FIRST_RETRY;
        } else {
            timerMilliSecond = AndroidConstants.TIMER_MILLISECONDS_SECOND_RETRY;
        }

        timerContainerView.setVisibility(View.VISIBLE);

        countDownTimer = new CountDownTimer(timerMilliSecond, 1000) {
            @Override
            public void onTick(long l) {
                timerMilliSecond = l;
                timerTv.setText(hmsTimeFormatter(l));
            }

            @Override
            public void onFinish() {
                if (apiCount < AndroidConstants.MAX_API_COUNT) {
                    retryBtn.setAlpha(1.0f);
                    retryBtn.setEnabled(true);
                    retryBtn.setVisibility(View.VISIBLE);
                    messageTv.setText(R.string.alert_request_retry);
                    timerContainerView.setVisibility(View.GONE);
                } else {
                    retryBtn.setAlpha(0.5f);
                    retryBtn.setEnabled(false);
                    messageTv.setText(R.string.alert_request_unable_to_process);
                    timerContainerView.setVisibility(View.GONE);
                }
            }

            private String hmsTimeFormatter(long milliSeconds) {
                String ms = String.format("%02d %02d",
                        TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                        TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
                return ms;
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_retry) {
            timerContainerView.setVisibility(View.GONE);
            apiLoadingInterface.makeApiCallAgain();
            retryBtn.setAlpha(0.5f);
            retryBtn.setEnabled(false);
            messageTv.setText(R.string.alert_request_processing);
            apiCount++;
        } else if (id == R.id.btn_cancel) {
            dismiss();
        }
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
