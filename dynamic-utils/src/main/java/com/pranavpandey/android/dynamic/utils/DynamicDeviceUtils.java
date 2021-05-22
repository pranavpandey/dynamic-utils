/*
 * Copyright 2017-2021 Pranav Pandey
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

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import java.text.DateFormat;

/**
 * Helper class to detect device specific features like Telephony, etc.
 */
public class DynamicDeviceUtils {

    /**
     * Vibrate device for the supplied duration.
     *
     * @param context The context to get the vibrator service.
     * @param duration The duration in milliseconds.
     *
     * @see Vibrator#vibrate(VibrationEffect)
     * @see VibrationEffect#createOneShot(long, int)
     * @see Vibrator#vibrate(long)
     */
    @SuppressWarnings("deprecation")
    @RequiresPermission(Manifest.permission.VIBRATE)
    @TargetApi(Build.VERSION_CODES.O)
    public static void vibrate(@Nullable Context context, long duration) {
        if (context == null) {
            return;
        }

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator == null) {
            return;
        }

        if (DynamicSdkUtils.is26()) {
            vibrator.vibrate(VibrationEffect.createOneShot(
                    duration, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(duration);
        }
    }

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
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
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
    public static @NonNull String getDate(@NonNull Context context, long milliSeconds) {
        DateFormat df = android.text.format.DateFormat.getDateFormat(context);
        DateFormat tf = android.text.format.DateFormat.getTimeFormat(context);

        return String.format(context.getResources().getString(R.string.adu_format_blank_space),
                df.format(milliSeconds), tf.format(milliSeconds));
    }

    /**
     * Checks whether the device has MIUI services installed.
     *
     * @param context The context to resolve the activities.
     *
     * @return {@code true} if the device has MIUI services installed.
     */
    public static boolean isXiaomiMIUI(@NonNull Context context) {
        return DynamicIntentUtils.isActivityResolved(context,
                new Intent("miui.intent.action.OP_AUTO_START")
                        .addCategory(Intent.CATEGORY_DEFAULT))
                || DynamicIntentUtils.isActivityResolved(context,
                new Intent().setComponent(new ComponentName("com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity")))
                || DynamicIntentUtils.isActivityResolved(context,
                new Intent("miui.intent.action.POWER_HIDE_MODE_APP_LIST")
                        .addCategory(Intent.CATEGORY_DEFAULT))
                || DynamicIntentUtils.isActivityResolved(context,
                new Intent().setComponent(new ComponentName("com.miui.securitycenter",
                        "com.miui.powercenter.PowerSettings")));
    }
}
