/*
 * Copyright 2017 Pranav Pandey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pranavpandey.android.dynamic.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import java.text.DateFormat;

import androidx.annotation.NonNull;

/**
 * Helper class to detect device specific features like Telephony, etc.
 */
public class DynamicDeviceUtils {

    /**
     * Detects the telephony feature by using {@link PackageManager}.
     *
     * @param context The context to get the package manager.
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
     * Retrieve a date and time string from milliSeconds according to the system settings.
     *
     * @param context The context to retrieve system date and time format.
     * @param milliSeconds The millis to be converted into date and time.
     *
     * @return The formatted date according to system settings.
     *
     * @see java.util.Date
     */
    public static @NonNull String getDate(Context context, long milliSeconds) {
        DateFormat df = android.text.format.DateFormat.getDateFormat(context);
        DateFormat tf = android.text.format.DateFormat.getTimeFormat(context);

        return String.format(context.getResources().getString(R.string.adu_format_blank_space),
                df.format(milliSeconds), tf.format(milliSeconds));
    }
}
