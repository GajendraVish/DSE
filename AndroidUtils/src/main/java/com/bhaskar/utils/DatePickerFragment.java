package com.bhaskar.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lenovo on 5/24/2018.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = DatePickerFragment.class.getSimpleName();
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String KEY_DATE_FORMAT = "key_date_format";
    public static final String KEY_SELECTED_DATE = "select_date";
    private Activity mActivity;
    private OnDatePicked onDatePicked;

    public interface OnDatePicked {
        void onDateProvided(String day, String month, String year);

    }

    public DatePickerFragment(Activity activity) {
        mActivity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        try {
            if (bundle != null) {
                String dateFormat = bundle.getString(KEY_DATE_FORMAT);
                if (TextUtils.isEmpty(dateFormat)) {
                    dateFormat = DATE_FORMAT;
                }
                @SuppressLint("SimpleDateFormat")
                DateFormat format = new SimpleDateFormat(dateFormat);
                String selectedDate = bundle.getString(KEY_SELECTED_DATE);
                Date date = new Date();
                if (!TextUtils.isEmpty(selectedDate)) {
                    try {
                        date = format.parse(selectedDate);
                    } catch (Exception e) {
                        Log.e(TAG, "onCreateDialog: " + e.getMessage());
                    }
                }
                final Calendar c = Calendar.getInstance();
                c.setTime(date);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                //DatePickerDialog dialog = new DatePickerDialog(mActivity,  this, year, month, day);
                DatePickerDialog dialog = new DatePickerDialog(mActivity, AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
                dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                return dialog;
            } else {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                //DatePickerDialog dialog = new DatePickerDialog(mActivity, this, year, month, day);
                DatePickerDialog dialog = new DatePickerDialog(mActivity, AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
                dialog.updateDate(2000, 0, 1);
                dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                return dialog;
            }
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
        return new Dialog(mActivity);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (null != onDatePicked) {
            String _day = "" + day;
            if (day < 10) {
                _day = "0" + day;
            }
            String _month = "" + (month + 1);
            if ((month + 1) < 10) {
                _month = "0" + (month + 1);
            }
            onDatePicked.onDateProvided(_day, _month, year + "");
        }
    }

    /**
     * @param onDatePicked
     */
    public void setOnDatePicked(OnDatePicked onDatePicked) {
        this.onDatePicked = onDatePicked;
    }
}
