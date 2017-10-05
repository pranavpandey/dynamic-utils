package com.pranavpandey.android.dynamic.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.text.DateFormat;

/**
 * Helper class to detect device specific features like
 * Telephony, etc.
 */
public class DynamicDeviceUtils {

    /**
     * To detect if device has telephony feature or not by using
     * PackageManager.
     *
     * @param context Context to get the PackageManager.
     *
     * @return {@code true} if the device has telephony feature.
     *
     * @see PackageManager#hasSystemFeature(String)
     * @see PackageManager#FEATURE_TELEPHONY
     */
    public static boolean hasTelephony(@NonNull Context context) {
        return context.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
    }

    /**
     * Retrieve a Date and Time string from date milliSeconds based
     * on the system settings.
     *
     * @param context to retrieve system date and time format.
     * @param milliSeconds to be converted into date and time.
     *
     * @return Formated date according to system settings.
     *
     * @see java.util.Date
     */
    public static String getDate(Context context, long milliSeconds) {
        DateFormat df = android.text.format.DateFormat.getDateFormat(context);
        DateFormat tf = android.text.format.DateFormat.getTimeFormat(context);

        return String.format(context.getResources().getString(R.string.adu_format_blank_space),
                df.format(milliSeconds), tf.format(milliSeconds));
    }
}
