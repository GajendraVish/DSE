package com.bhaskar.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.DialogFragment;

/**
 * Created by poonam on 5/24/2018.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static final String KEY_SELECTED_TIME = "select_time";

    private final String DATE_FORMAT_DEFAULT = "hh:mm";

    public interface OnTimePicked {
        void onTimeProvided(int hour, int minute);
    }

    public OnTimePicked onTimePicked;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        onTimePicked.onTimeProvided(hourOfDay, minute);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        if (null != bundle) {

            DateFormat format = new SimpleDateFormat(DATE_FORMAT_DEFAULT);


            String selectedTime = bundle.getString(KEY_SELECTED_TIME);
            Date date = new Date();

            if (!TextUtils.isEmpty(selectedTime)) {
                try {
                    date = format.parse(selectedTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            final Calendar mcurrentTime = Calendar.getInstance();
            mcurrentTime.setTime(date);
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,this, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
            return mTimePicker;

        } else {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,this, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
            return mTimePicker;
        }
    }

    public void setOnTimePicked(OnTimePicked onTimePicked) {
        this.onTimePicked = onTimePicked;
    }
}
