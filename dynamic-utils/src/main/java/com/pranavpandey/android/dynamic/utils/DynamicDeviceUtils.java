package com.pranavpandey.android.dynamic.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

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
}
