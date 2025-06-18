/*
 * Copyright 2017-2024 Pranav Pandey
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

package com.pranavpandey.android.dynamic.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Helper class to detect device specific features like Telephony, etc.
 */
public class DynamicDeviceUtils {

    /**
     * Pattern for the WEP encryption.
     */
    public static final Pattern PATTERN_WEP = Pattern.compile(
            "WEP", Pattern.CASE_INSENSITIVE);

    /**
     * Pattern for the WPA encryption.
     */
    public static final Pattern PATTERN_WPA = Pattern.compile(
            "WPA", Pattern.CASE_INSENSITIVE);

    /**
     * Returns the Wi-Fi configuration according to the supplied parameters.
     *
     * @param ssid The network ssid to be used.
     * @param password The password to be used.
     * @param encryption The encryption method to be used.
     * @param hidden {@code true} if the network is hidden.
     *
     * @return The Wi-Fi configuration according to the supplied parameters.
     *
     * @see WifiConfiguration
     */
    @SuppressWarnings("deprecation")
    public static @Nullable WifiConfiguration getWifiConfiguration(@Nullable String ssid,
            @Nullable String password, @Nullable String encryption, boolean hidden) {
        if (DynamicSdkUtils.is29() || ssid == null) {
            return null;
        }

        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();

        config.SSID = "\"" + ssid + "\"";
        config.hiddenSSID = hidden;

        if (encryption != null && PATTERN_WEP.matcher(encryption).find()) {
            config.wepKeys[0]= "\"" + password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (encryption != null && PATTERN_WPA.matcher(encryption).find()) {
            config.preSharedKey = "\"" + password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.status = WifiConfiguration.Status.ENABLED;
        } else {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }

        return config;
    }

    /**
     * Try to save the Wi-Fi configuration according to the supplied parameters.
     *
     * @param context The context to used.
     * @param ssid The network ssid to be used.
     * @param password The password to be used.
     * @param encryption The encryption method to be used.
     * @param hidden {@code true} if the network is hidden.
     *
     * @return {code true} on successful operation.
     *
     * @see #getWifiConfiguration(String, String, String, boolean)
     * @see WifiManager#addNetworkSuggestions(List)
     * @see WifiManager#enableNetwork(int, boolean)
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.Q)
    @RequiresPermission(allOf = { Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE })
    public static boolean connectWifi(@Nullable Context context, @Nullable String ssid,
            @Nullable String password, @Nullable String encryption, boolean hidden) {
        if (context == null || ssid == null) {
            return false;
        }

        WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        if (wifiManager == null) {
            return false;
        }

        if (DynamicSdkUtils.is29()) {
            List<WifiNetworkSuggestion> suggestionsList = new ArrayList<>();

            if (encryption != null && PATTERN_WPA.matcher(encryption).find() && password != null) {
                WifiNetworkSuggestion wpa2 = new WifiNetworkSuggestion.Builder()
                        .setSsid(ssid).setWpa2Passphrase(password)
                        .setIsHiddenSsid(hidden).build();
                WifiNetworkSuggestion wpa3 = new WifiNetworkSuggestion.Builder()
                        .setSsid(ssid).setWpa3Passphrase(password)
                        .setIsHiddenSsid(hidden).build();

                suggestionsList.add(wpa2);
                suggestionsList.add(wpa3);
            } else {
                WifiNetworkSuggestion open =
                        new WifiNetworkSuggestion.Builder()
                                .setSsid(ssid)
                                .setIsHiddenSsid(hidden)
                                .build();

                suggestionsList.add(open);
            }

            wifiManager.addNetworkSuggestions(suggestionsList);

            return true;
        }

        WifiConfiguration configuration;
        if ((configuration = getWifiConfiguration(ssid, password, encryption, hidden)) != null) {
            if (!wifiManager.isWifiEnabled()){
                wifiManager.setWifiEnabled(true);
            }

            int networkId;
            if ((networkId = wifiManager.addNetwork(configuration)) != -1) {
                wifiManager.saveConfiguration();

                if (wifiManager.getConnectionInfo() != null) {
                    if (wifiManager.getConnectionInfo().getNetworkId() != networkId) {
                        wifiManager.disableNetwork(wifiManager.getConnectionInfo().getNetworkId());
                        wifiManager.disconnect();

                        return wifiManager.enableNetwork(networkId, true);
                    } else {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Checks whether the device has a system feature.
     *
     * @param context The context to be used.
     * @param feature The feature to be checked.
     *
     * @return {@code true} if the device has the system feature.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean hasSystemFeature(@Nullable Context context, @NonNull String feature) {
        if (context == null) {
            return false;
        }

        return context.getPackageManager().hasSystemFeature(feature);
    }

    /**
     * Checks whether the device has camera feature.
     *
     * @param context The context to be used.
     *
     * @return {@code true} if the device has camera feature.
     *
     * @see #hasSystemFeature(Context, String)
     * @see PackageManager#FEATURE_CAMERA
     * @see PackageManager#FEATURE_CAMERA_ANY
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean hasCameraFeature(@Nullable Context context) {
        return hasSystemFeature(context, DynamicSdkUtils.is17()
                ? PackageManager.FEATURE_CAMERA_ANY : PackageManager.FEATURE_CAMERA);
    }

    /**
     * Checks whether the device has flashlight feature.
     *
     * @param context The context to be used.
     *
     * @return {@code true} if the device has flashlight feature.
     *
     * @see #hasSystemFeature(Context, String)
     * @see PackageManager#FEATURE_CAMERA_FLASH
     */
    public static boolean hasFlashlightFeature(@Nullable Context context) {
        return hasSystemFeature(context, PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * Detects the telephony feature by using {@link PackageManager}.
     *
     * @param context The context to get the package manager.
     *
     * @return {@code true} if the device has telephony feature.
     *
     * @see #hasSystemFeature(Context, String)
     * @see PackageManager#FEATURE_TELEPHONY
     */
    public static boolean hasTelephony(@NonNull Context context) {
        return hasSystemFeature(context, PackageManager.FEATURE_TELEPHONY);
    }

    /**
     * Detects the hinge sensor feature by using {@link PackageManager}.
     *
     * @param context The context to get the package manager.
     *
     * @return {@code true} if the device has hinge sensor feature feature.
     *
     * @see #hasSystemFeature(Context, String)
     * @see PackageManager#FEATURE_SENSOR_HINGE_ANGLE
     */
    @TargetApi(Build.VERSION_CODES.R)
    public static boolean hasHingeFeature(@NonNull Context context) {
        return DynamicSdkUtils.is30() && hasSystemFeature(
                context, PackageManager.FEATURE_SENSOR_HINGE_ANGLE);
    }

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

        Vibrator vibrator = ContextCompat.getSystemService(context, Vibrator.class);
        if (vibrator == null) {
            return;
        }

        if (DynamicSdkUtils.is26()) {
            try {
                vibrator.vibrate(VibrationEffect.createOneShot(
                        duration, VibrationEffect.DEFAULT_AMPLITUDE));
            } catch (Exception e) {
                vibrator.vibrate(duration);
            }
        } else {
            vibrator.vibrate(duration);
        }
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
     * Retrieve a date and time string with separators {@code yyyy-MM-dd-HH-mm-ss}
     * from the supplied milliSeconds.
     *
     * @param milliSeconds The millis to be converted into date and time.
     *
     * @return The formatted date and time string with separators.
     *
     * @see SimpleDateFormat
     */
    public static @NonNull String getDateWithSeparator(long milliSeconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
        return dateFormat.format(milliSeconds);
    }

    /**
     * Checks whether the device has a One UI version installed.
     *
     * @return {@code true} if the device has a One UI version installed.
     */
    @SuppressWarnings("JavaReflectionMemberAccess")
    public static boolean isSamsungOneUI() {
        try {
            return Build.VERSION.class.getDeclaredField("SEM_PLATFORM_INT")
                    .getInt(null) >= 9000;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks whether the device has an EMUI version installed.
     *
     * @param context The context to resolve the activities.
     *
     * @return {@code true} if the device has an EMUI version installed.
     */
    public static boolean isHuaweiEMUI(@Nullable Context context) {
        return  (Build.MANUFACTURER.toLowerCase().contains("huawei")
                || Build.BRAND.toLowerCase().contains("huawei"))
                && DynamicIntentUtils.isActivityResolved(context,
                new Intent("com.huawei.appmarket.intent.action.MainActivity"));
    }

    /**
     * Checks whether the device has MIUI services installed.
     *
     * @param context The context to resolve the activities.
     *
     * @return {@code true} if the device has MIUI services installed.
     */
    public static boolean isXiaomiMIUI(@Nullable Context context) {
        if (context == null) {
            return false;
        }

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
