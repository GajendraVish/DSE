package com.bhaskar.network;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract class RetroRetryCallback<T> implements Callback<T> {

    private int totalRetries;
    private static final String TAG = RetroRetryCallback.class.getSimpleName();
    private final Call<T> call;
    private int retryCount = 0, retryInterval = 0;
    private Handler retryIntervalHandler;

    public abstract void onFinalResponse(Call<T> call, T response);

    abstract void onFinalFailure(Call<T> call, Throwable t);

    /***
     *
     * @param call
     * @param totalRetries
     * @param retryInterval in MiliSeconds
     */
    RetroRetryCallback(Call<T> call, int totalRetries, int retryInterval) {
        this.call = call;
        this.totalRetries = totalRetries;

        if (retryInterval > 0) {
            this.retryInterval = retryInterval;
            retryIntervalHandler = new Handler();
        }
    }

    @Override
    public void onResponse(Call<T> call, @NonNull Response<T> response) {
        if (!RetroApiHelper.isCallSuccess(response))
            if (retryCount++ < totalRetries) {
                retry();
            } else {
                onFinalFailure(call, new Exception("Retry Failed!"));
            }
        else {
            onFinalResponse(call, response.body());
        }
    }

    @Override
    public void onFailure(Call<T> call, @NonNull Throwable t) {
        Log.e(TAG, t.getMessage()+"");
        if (retryCount++ < totalRetries) {
            retry();
        } else {
            onFinalFailure(call, t);
        }
    }

    private void retry() {
        if (retryIntervalHandler != null) {
            retryIntervalHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    applyRetryCall();
                }
            }, (retryCount * retryInterval));
        } else {
            applyRetryCall();
        }
    }

    private void applyRetryCall() {
        Log.d(TAG, "Retrying API Call -  (" + retryCount + " / " + totalRetries + ")");
        call.clone().enqueue(this);
    }
}