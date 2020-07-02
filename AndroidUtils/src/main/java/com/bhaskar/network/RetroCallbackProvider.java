package com.bhaskar.network;

import android.app.Activity;
import android.util.Log;

import com.bhaskar.utils.BuildConfig;
import com.bhaskar.utils.EasyDialogUtils;
import com.bhaskar.utils.R;
import com.bhaskar.utils.TransAviLoader;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RetroCallbackProvider<T> implements Callback<T> {

    public abstract void onSuccess(Call<T> call, @NonNull T response);

    public abstract void onFailed(Call<T> call, Throwable t);

    private TransAviLoader mLoader;
    private Activity activity;
    private final String TAG = "RetroCallbackProvider";

    private Object tag;

    /***
     * to not show ProgressLoader
     */
    public RetroCallbackProvider() {
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Object getTag() {
        return tag;
    }

    /***
     * activity = null if no need to show ProgressLoader
     *
     * @param activity
     */
    public RetroCallbackProvider(Activity activity) {
        this.activity = activity;
        init();
    }

    private void init() {
        if (null != activity) {
            mLoader = new TransAviLoader(activity);
            mLoader.show();
        }
    }

    @Override
    public final void onResponse(Call<T> call, @NonNull Response<T> response) {
        try {
            T body = response.body();
            if (body != null) {
                ////Logger.t("onResponse").d(body + "");
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, response+ "");
                }
                onSuccess(call, body);
            } else {
                onFailed(call, new NullPointerException());
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (null != activity && !activity.isFinishing()) {
                EasyDialogUtils.showInfoDialog(activity, activity.getString(R.string.alert_title_alert),
                        activity.getString(R.string.alert_api_failed));
            }
        } finally {
            dismissLoader();
        }
    }

    @Override
    public final void onFailure(Call<T> call, Throwable t) {
        try {

                Log.e(TAG, "onFailure: " + call.request().url());

            t.printStackTrace();
            ////Logger.t("onFailure").d(call);
            onFailed(call, t);
        } finally {
            dismissLoader();
        }
    }

    private void dismissLoader() {
        try {
            if (mLoader != null) {
                mLoader.dismiss();
                mLoader = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
