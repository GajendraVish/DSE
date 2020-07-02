package com.bhaskar.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

/**
 * Class to keep static utility methods for various utility tasks Please Define
 * DIALOG_ICON before using the class
 *
 * @Deprecated . Use EasyDialogUtils instead
 */
@Deprecated
public final class DialogUtils {

    private static ProgressDialog pd;
    public static int DIALOG_ICON = 0;
    public static int ICON_DEFAULT = -1; // default to DIALOG_ICON
    public static int ICON_REMOVE = 0;   // for no icon

    /**
     * To show Dialog with message
     *
     * @param activity
     * @param message
     */
    public static void showInfoDialog(final Activity activity,
                                      final String message) {

        try {
            if (activity != null && !activity.isFinishing()) {

                AlertDialog.Builder builder = null;
                if (activity.getParent() != null)
                    builder = new AlertDialog.Builder(activity.getParent());
                else
                    builder = new AlertDialog.Builder(activity);

                // builder.setTitle("Alert!");
                builder.setIcon(DIALOG_ICON);
                if (message != null)
                    builder.setMessage(message);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!activity.isFinishing()) {
                            dialog.dismiss();
                        }
                        Runtime.getRuntime().gc();
                    }
                });
                AlertDialog msg = builder.create();
                msg.setCancelable(false);
                msg.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To show Dialog with message
     *
     * @param activity
     * @param title    can be null if title is not allowed
     * @param message
     */
    public static void showInfoDialog(final Activity activity,
                                      final String title, final String message) {

        try {
            if (activity != null && !activity.isFinishing()) {

                AlertDialog.Builder builder = null;
                if (activity.getParent() != null)
                    builder = new AlertDialog.Builder(activity.getParent());
                else
                    builder = new AlertDialog.Builder(activity);

                if (title != null) {
                    builder.setTitle(title);

                    if (title.equals(activity.getString(R.string.alert_title_exception))) {
                        builder.setIcon(R.drawable.ic_alert);
                    } else if (title.equals(activity.getString(R.string.alert_title_error))) {
                        builder.setIcon(R.drawable.ic_fail);
                    } else if (title.equals(activity.getString(R.string.alert_title_success))) {
                        builder.setIcon(R.drawable.ic_success);
                    } else {
                        builder.setIcon(DIALOG_ICON);
                    }
                } else {
                    builder.setIcon(DIALOG_ICON);
                }
                if (message != null)
                    builder.setMessage(message);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!activity.isFinishing()) {
                            dialog.dismiss();
                        }
                        Runtime.getRuntime().gc();
                    }
                });
                AlertDialog msg = builder.create();
                msg.setCancelable(false);
                msg.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param activity
     * @param title
     * @param message
     * @param dialogOkInterface
     */
    public static void showInfoDialog(final Activity activity,
                                      final String title, final String message, final DialogOkInterface dialogOkInterface) {
        try {
            if (activity != null && !activity.isFinishing()) {
                AlertDialog.Builder builder = null;
                if (activity.getParent() != null)
                    builder = new AlertDialog.Builder(activity.getParent());
                else
                    builder = new AlertDialog.Builder(activity);

                if (title != null) {
                    builder.setTitle(title);

                    if (title.equals(activity.getString(R.string.alert_title_exception))) {
                        builder.setIcon(R.drawable.ic_alert);
                    } else if (title.equals(activity.getString(R.string.alert_title_error))) {
                        builder.setIcon(R.drawable.ic_fail);
                    } else if (title.equals(activity.getString(R.string.alert_title_success))) {
                        builder.setIcon(R.drawable.ic_success);
                    } else {
                        builder.setIcon(DIALOG_ICON);
                    }
                } else {
                    builder.setIcon(DIALOG_ICON);
                }
                if (message != null)
                    builder.setMessage(message);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!activity.isFinishing()) {
                            dialog.dismiss();
                        }
                        Runtime.getRuntime().gc();
                        if (null != dialogOkInterface) {
                            dialogOkInterface.doOnOkBtnClick(activity);
                        }
                    }
                });
                AlertDialog msg = builder.create();
                msg.setCancelable(false);
                msg.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param activity
     * @param title
     * @param message
     * @param buttonOK
     * @param buttonCancel
     */
    public static void showInfoDialogWithTwoButtons(final Activity activity,
                                                    final String title, final String message, final String buttonOK,
                                                    final String buttonCancel) {
        try {
            if (activity != null && !activity.isFinishing()) {
                AlertDialog.Builder builder = null;
                if (activity.getParent() != null)
                    builder = new AlertDialog.Builder(activity.getParent());
                else
                    builder = new AlertDialog.Builder(activity);


                if (title != null) {
                    builder.setTitle(title);

                    if (title.equals(activity.getString(R.string.alert_title_exception))) {
                        builder.setIcon(R.drawable.ic_alert);
                    } else if (title.equals(activity.getString(R.string.alert_title_error))) {
                        builder.setIcon(R.drawable.ic_fail);
                    } else if (title.equals(activity.getString(R.string.alert_title_success))) {
                        builder.setIcon(R.drawable.ic_success);
                    } else {
                        builder.setIcon(DIALOG_ICON);
                    }
                } else {
                    builder.setIcon(DIALOG_ICON);
                }

                if (message != null)
                    builder.setMessage(message);

                builder.setPositiveButton(buttonOK,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!activity.isFinishing()) {
                                    dialog.dismiss();
                                }
                                Runtime.getRuntime().gc();
                            }
                        });

                builder.setNegativeButton(buttonCancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!activity.isFinishing()) {
                                    dialog.dismiss();
                                }
                                Runtime.getRuntime().gc();
                            }
                        });
                AlertDialog msg = builder.create();
                msg.setCancelable(false);
                msg.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To show Dialog with message and finish an activity when tap on Ok
     *
     * @param activity
     * @param message
     */
    public static void showFinishDialog(final Activity activity,
                                        final String message) {
        try {
            if (activity != null && !activity.isFinishing()) {
                AlertDialog.Builder builder = null;
                if (activity.getParent() != null)
                    builder = new AlertDialog.Builder(activity.getParent());
                else
                    builder = new AlertDialog.Builder(activity);

                builder.setCancelable(false);
                builder.setIcon(DIALOG_ICON);
                if (message != null)
                    builder.setMessage(message);

                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!activity.isFinishing()) {
                                    dialog.dismiss();
                                }
                                Runtime.getRuntime().gc();
                                activity.finish();
                            }
                        });
                AlertDialog msg = builder.create();
                msg.setCancelable(false);
                msg.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To show Dialog with message and finish an activity when tap on Ok
     *
     * @param activity
     * @param message
     */
    public static void showFinishTambolaDialog(final Activity activity,
                                               final String message) {
        try {
            if (activity != null && !activity.isFinishing()) {
                AlertDialog.Builder builder = null;
                if (activity.getParent() != null)
                    builder = new AlertDialog.Builder(activity.getParent());
                else
                    builder = new AlertDialog.Builder(activity);

                builder.setCancelable(false);
                builder.setIcon(DIALOG_ICON);
                builder.setTitle(activity.getString(R.string.alert_title_alert));
                if (message != null)
                    builder.setMessage(message);

                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!activity.isFinishing()) {
                                    dialog.dismiss();
                                }
                                Runtime.getRuntime().gc();
                                activity.finish();
                            }
                        });
                AlertDialog msg = builder.create();
                msg.setCancelable(false);
                msg.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * *
     * To show Dialog with message and finish an activity when tap on Ok
     *
     * @param activity
     * @param title
     * @param message
     */
    public static void showFinishDialog(final Activity activity,
                                        final String title, final String message) {
        try {
            if (activity != null && !activity.isFinishing()) {
                AlertDialog.Builder builder = null;
                if (activity.getParent() != null)
                    builder = new AlertDialog.Builder(activity.getParent());
                else
                    builder = new AlertDialog.Builder(activity);

                if (title != null)
                    builder.setTitle(title);
                builder.setCancelable(false);
                builder.setIcon(DIALOG_ICON);
                if (message != null)
                    builder.setMessage(message);

                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!activity.isFinishing()) {
                                    dialog.dismiss();
                                }
                                Runtime.getRuntime().gc();
                                activity.finish();
                            }
                        });
                AlertDialog msg = builder.create();
                msg.setCancelable(false);
                msg.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public static void showConfirmationDialog(final Activity activity,
                                              final String title, final String message,
                                              final boolean isCancelable, final int iconResId,
                                              final String positiveBtnName, final String negativeBtnName,
                                              final DialogResponseInterface dialogResponseInterface) {

        try {
            if (activity != null && !activity.isFinishing()) {
                AlertDialog.Builder builder = null;
                if (activity.getParent() != null)
                    builder = new AlertDialog.Builder(activity.getParent());
                else
                    builder = new AlertDialog.Builder(activity);

                if (title != null)
                    builder.setTitle(title);
                builder.setCancelable(isCancelable);

                if (iconResId == ICON_DEFAULT) {
                    builder.setIcon(DIALOG_ICON);
                } else {
                    builder.setIcon(iconResId);
                }
                if (message != null)
                    builder.setMessage(message);

                if (negativeBtnName != null) {
                    builder.setPositiveButton(negativeBtnName,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    if (!activity.isFinishing()) {
                                        dialog.dismiss();
                                    }
                                    dialogResponseInterface.doOnNegativeBtnClick(activity);
                                }
                            });
                }
                if (positiveBtnName != null) {
                    builder.setNegativeButton(positiveBtnName,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    if (!activity.isFinishing()) {
                                        dialog.dismiss();
                                    }
                                    dialogResponseInterface.doOnPositiveBtnClick(activity);
                                }
                            });
                }

                AlertDialog msg = builder.create();
                msg.setCancelable(false);
                msg.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
     * @param iconResId
     * @param positiveBtnName
     * @param negativeBtnName
     * @param neturalBtnName
     * @param dialogResponseInterface
     */
    public static void showConfirmationDialog(final Activity activity,
                                              final String title, final String message,
                                              final boolean isCancelable, final int iconResId,
                                              final String positiveBtnName, final String negativeBtnName,
                                              final String neturalBtnName,
                                              final DialogResponsesInterface dialogResponseInterface) {

        try {
            if (activity != null && !activity.isFinishing()) {

                AlertDialog.Builder builder = null;
                if (activity.getParent() != null)
                    builder = new AlertDialog.Builder(activity.getParent());
                else
                    builder = new AlertDialog.Builder(activity);

                if (title != null)
                    builder.setTitle(title);
                builder.setCancelable(isCancelable);
                builder.setIcon(iconResId);
                if (message != null)
                    builder.setMessage(message);

                if (neturalBtnName != null) {
                    builder.setNeutralButton(neturalBtnName,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    if (!activity.isFinishing()) {
                                        dialog.dismiss();
                                    }
                                    dialogResponseInterface.doOnNeutralBtnClick(activity);
                                }
                            });
                }

                if (negativeBtnName != null) {
                    builder.setPositiveButton(negativeBtnName,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    if (!activity.isFinishing()) {
                                        dialog.dismiss();
                                    }
                                    dialogResponseInterface.doOnNegativeBtnClick(activity);
                                }
                            });
                }
                if (positiveBtnName != null) {
                    builder.setNegativeButton(positiveBtnName,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    if (!activity.isFinishing()) {
                                        dialog.dismiss();
                                    }
                                    dialogResponseInterface.doOnPositiveBtnClick(activity);
                                }
                            });
                }

                AlertDialog msg = builder.create();
                msg.setCancelable(false);
                msg.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To show ProgressDialog while fetching in progress
     *
     * @param activity
     * @param title
     * @param message
     * @param isCancelable
     */
    public static void showProgressDialog(final Activity activity,
                                          final String title, final String message, final boolean isCancelable) {
        try {
            if (pd == null) {
                Activity parent = activity.getParent();
                if (parent != null)
                    pd = new ProgressDialog(parent);
                else
                    pd = new ProgressDialog(activity);
            } else
                pd.dismiss();

            //////////////////////////////////pd.setTitle(title);
            pd.setMessage(message);
            pd.setIcon(DIALOG_ICON);
            pd.setCancelable(isCancelable);
            pd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To dismiss progressDialog
     */
    public static void dismissProgressDialog() {
        try {
            if (pd != null && pd.isShowing() && pd.getOwnerActivity() != null && !pd.getOwnerActivity().isFinishing()) {
                pd.dismiss();
                pd = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd = null;
        } catch (Error e) {
            e.printStackTrace();
            pd = null;
        }
    }
}
