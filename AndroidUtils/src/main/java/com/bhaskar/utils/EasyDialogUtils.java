package com.bhaskar.utils;

import android.app.Activity;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.bhaskar.ezdialog.EZDialog;
import com.bhaskar.ezdialog.EZDialogListener;

public final class EasyDialogUtils {

    public static int DIALOG_APP_ICON = 0;

    /**
     * To show Dialog with message
     *
     * @param activity
     * @param message
     */
    public static void showInfoDialog(@NonNull final Activity activity,
                                      @NonNull final String message) {

        showInfoDialog(activity, activity.getString(R.string.app_name), message);
    }

    /**
     * To show Dialog with message
     *
     * @param activity
     * @param title
     * @param message
     */
    public static void showInfoDialog(@NonNull final Activity activity,
                                      @NonNull final String title, @NonNull final String message) {
        showInfoDialog(activity, title, message, null);
    }

    /**
     * @param activity
     * @param title
     * @param message
     * @param dialogOkInterface
     */
    public static void showInfoDialog(@NonNull final Activity activity,
                                      @NonNull final String title, @NonNull final String message, final DialogOkInterface dialogOkInterface) {
        if (!activity.isFinishing()) {
            EZDialog.Builder builder = new EZDialog.Builder(activity)
                    .setTitle(title)
                    .setTitleImgResId(DIALOG_APP_ICON)
                    .setMessage(message)
                    .setPositiveBtnText(activity.getString(R.string.label_okey))
                    .OnPositiveClicked(new EZDialogListener() {
                        @Override
                        public void OnClick() {
                            if (dialogOkInterface != null) {
                                dialogOkInterface.doOnOkBtnClick(activity);
                            }
                            Runtime.getRuntime().gc();
                        }
                    });
            builder.build();
        }
    }

    /***
     *
     * @param activity
     * @param message
     */
    public static void showFinishDialog(@NonNull final Activity activity,
                                        @NonNull final String message) {
        showFinishDialog(activity, activity.getString(R.string.app_name), message);
    }

    /***
     *
     * @param activity
     * @param title
     * @param message
     */
    public static void showFinishDialog(@NonNull final Activity activity, @NonNull String title,
                                        @NonNull final String message) {
        if (!activity.isFinishing()) {
            EZDialog.Builder builder = new EZDialog.Builder(activity)
                    .setTitle(title)
                    .setTitleImgResId(DIALOG_APP_ICON)
                    .setMessage(message)
                    .setPositiveBtnText(activity.getString(R.string.label_okey))
                    .OnPositiveClicked(new EZDialogListener() {
                        @Override
                        public void OnClick() {
                            activity.finish();
                            Runtime.getRuntime().gc();
                        }
                    });
            builder.build();
        }
    }

    /***
     *
     * @param activity
     * @param title
     * @param message
     * @param titleImgResId
     */
    public static void showFinishDialog(@NonNull final Activity activity, @NonNull String title,
                                        @NonNull final String message, @DrawableRes int titleImgResId) {
        if (!activity.isFinishing()) {
            EZDialog.Builder builder = new EZDialog.Builder(activity)
                    .setTitle(title)
                    .setTitleImgResId(titleImgResId)
                    .setMessage(message)
                    .setPositiveBtnText(activity.getString(R.string.label_okey))
                    .OnPositiveClicked(new EZDialogListener() {
                        @Override
                        public void OnClick() {
                            activity.finish();
                            Runtime.getRuntime().gc();
                        }
                    });
            builder.build();
        }
    }


    /**
     * *
     * To show confirmation dialog with custom title,message,icon and buttons
     * name. With an interface to respond with tapping on dialog' buttons.
     *
     * @param activity
     * @param title
     * @param message
     * @param isCancelable
     * @param iconResId               -1 to make it default to DIALOG_ICON, 0 to remove icon
     * @param positiveBtnName
     * @param negativeBtnName
     * @param dialogResponseInterface
     */
    public static void showConfirmationDialog(@NonNull final Activity activity,
                                              @NonNull final String title, @NonNull final String message,
                                              final boolean isCancelable, final int iconResId,
                                              @NonNull final String positiveBtnName, @NonNull final String negativeBtnName,
                                              final DialogResponseInterface dialogResponseInterface) {

        if (!activity.isFinishing()) {
            EZDialog.Builder builder = new EZDialog.Builder(activity)
                    .setTitle(title)
                    .setTitleImgResId((iconResId == 0) ? DIALOG_APP_ICON : iconResId)
                    .setMessage(message)
                    .setPositiveBtnText(positiveBtnName)
                    .setCancelable(isCancelable)
                    .setNegativeBtnText(negativeBtnName)
                    .OnPositiveClicked(new EZDialogListener() {
                        @Override
                        public void OnClick() {
                            dialogResponseInterface.doOnPositiveBtnClick(activity);
                            Runtime.getRuntime().gc();
                        }
                    })
                    .OnNegativeClicked(new EZDialogListener() {
                        @Override
                        public void OnClick() {
                            dialogResponseInterface.doOnNegativeBtnClick(activity);
                            Runtime.getRuntime().gc();
                        }
                    });

            builder.build();
        }
    }
    /**
     * @param activity
     * @param title
     * @param message
     * @param dialogOkInterface
     */
    public static void showOkDialog(@NonNull final Activity activity,
                                      @NonNull final String title, @NonNull final String message, final DialogOkInterface dialogOkInterface) {
        if (!activity.isFinishing()) {
            EZDialog.Builder builder = new EZDialog.Builder(activity)
                    .setTitle(title)
                    .setTitleImgResId(DIALOG_APP_ICON)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveBtnText(activity.getString(R.string.label_okey))
                    .OnPositiveClicked(new EZDialogListener() {
                        @Override
                        public void OnClick() {
                            if (dialogOkInterface != null) {
                                dialogOkInterface.doOnOkBtnClick(activity);
                            }
                            Runtime.getRuntime().gc();
                        }
                    });
            builder.build();
        }
    }
}
