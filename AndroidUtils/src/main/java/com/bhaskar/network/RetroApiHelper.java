package com.bhaskar.network;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Response;

public final class RetroApiHelper {
    /***
     *
     * @param call
     * @param retryCount
     * @param retryInterval
     * @param callback
     * @param <T>
     */
    public static <T> void enqueueWithRetry(Call<T> call, final int retryCount, final int retryInterval,@NonNull final RetroApiListener<T> callback) {
        call.enqueue(new RetroRetryCallback<T>(call, retryCount, retryInterval) {

            @Override
            public void onFinalResponse(Call<T> call,@NonNull T response) {
                callback.onApiSuccess(response);
            }

            @Override
            public void onFinalFailure(Call<T> call, Throwable t) {
                callback.onApiFailed(t.getMessage());
            }
        });
    }

    /***
     *
     * @param call
     * @param retryCount
     * @param callback
     * @param <T>
     */
    public static <T> void enqueueWithRetry(Call<T> call, final int retryCount, final RetroApiListener<T> callback) {
        enqueueWithRetry(call, retryCount, 0, callback);
    }


    /****
     *
     * @param response
     * @return
     */
    static boolean isCallSuccess(@NonNull Response response) {
        int code = response.code();
        return (code >= 200 && code < 400);
    }
}