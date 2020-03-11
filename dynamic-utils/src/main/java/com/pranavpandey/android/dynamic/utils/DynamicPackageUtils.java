/*
 * Copyright 2017-2020 Pranav Pandey
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

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Helper class to get package or app related information.
 */
public class DynamicPackageUtils {

    /**
     * Checks if a given package name exits.
     *
     * @param context The context to get the package manager.
     * @param packageName The package name to be checked.
     *
     * @return {@code true} if the given package name exits.
     */
    public static boolean isPackageExists(@NonNull Context context, @Nullable String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

        return true;
    }

    /**
     * Get component name from the given context.
     *
     * @param context The context to build the component.
     *
     * @return The component name from the given context.
     *
     * @see ComponentName#ComponentName(String, String)
     */
    public static @NonNull ComponentName getComponentName(@NonNull Context context) {
        return new ComponentName(context.getPackageName(), context.getClass().getName());
    }

    /**
     * Get application label from the given package.
     *
     * @param context The context to get the package manager.
     * @param packageName The package name of the app to get its label.
     *
     * @return The application label or name.
     *
     * @see ApplicationInfo#loadLabel(PackageManager)
     */
    public static @Nullable CharSequence getAppLabel(@NonNull Context context,
            @Nullable String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(packageName,
                    PackageManager.GET_META_DATA).applicationInfo.loadLabel(packageManager);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get application label from the given context.
     *
     * @param context The context to get the package manager.
     *
     * @return The application label or name.
     *
     * @see ApplicationInfo#loadLabel(PackageManager)
     */
    public static @Nullable CharSequence getAppLabel(@NonNull Context context) {
        return getAppLabel(context, context.getPackageName());
    }

    /**
     * Get package version name from the given package.
     *
     * @param context The context to get the package manager.
     * @param packageName The package name to get its version name.
     *
     * @return The package version name.
     *
     * @see android.content.pm.PackageInfo#versionName
     */
    public static @Nullable String getAppVersion(@NonNull Context context,
            @Nullable String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_META_DATA).versionName;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get package version name from the given context.
     *
     * @param context The context to get the package manager.
     *
     * @return The package version name.
     *
     * @see android.content.pm.PackageInfo#versionName
     */
    public static @Nullable String getAppVersion(@NonNull Context context) {
        return getAppVersion(context, context.getPackageName());
    }

    /**
     * Load application icon from the given package.
     *
     * @param context The context to get the package manager.
     * @param packageName The package name of the app to load its icon.
     *
     * @return The application icon drawable.
     *
     * @see ApplicationInfo#loadIcon(PackageManager)
     */
    public static @Nullable Drawable getAppIcon(@NonNull Context context,
            @Nullable String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(packageName,
                    PackageManager.GET_META_DATA).applicationInfo.loadIcon(packageManager);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Load application icon from the given context.
     *
     * @param context The context to get the package manager.
     *
     * @return The application icon drawable.
     *
     * @see ApplicationInfo#loadIcon(PackageManager)
     */
    public static @Nullable Drawable getAppIcon(@NonNull Context context) {
        return getAppIcon(context, context.getPackageName());
    }

    /**
     * Detects if the given application info is a system app or not.
     *
     * @param applicationInfo The application info of the package.
     *
     * @return {@code true} if the associated package is a system app.
     */
    public static boolean isSystemApp(@NonNull ApplicationInfo applicationInfo) {
        int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
        return (applicationInfo.flags & mask) != 0;
    }
}
