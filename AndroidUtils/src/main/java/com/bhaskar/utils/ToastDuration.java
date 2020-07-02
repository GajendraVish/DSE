package com.bhaskar.utils;

import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Created by saurabh_jain on 16/1/17.
 */

/**
 * @hide
 */
@IntDef({Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
@Retention(RetentionPolicy.SOURCE)
public @interface ToastDuration {
}
