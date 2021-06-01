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

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.annotation.AnyRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Helper class to get package or app related information.
 */
public class DynamicPackageUtils {

    /**
     * The {@code null} resource ID. This denotes an invalid resource ID that is returned by the
     * system when a resource is not found or the value is set to {@code @null} in XML.
     */
    public static final @AnyRes int ID_NULL = 0;

    /**
     * Checks if a given package name exits.
     *
     * @param context The context to get the package manager.
     * @param packageName The package name to be checked.
     *
     * @return {@code true} if the given package name exits.
     */
    public static boolean isPackageExists(@Nullable Context context,
            @Nullable String packageName) {
        if (context == null || packageName == null) {
            return false;
        }

        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
        } catch (Exception e) {
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
    public static @Nullable CharSequence getAppLabel(@Nullable Context context,
            @Nullable String packageName) {
        if (context == null || packageName == null) {
            return null;
        }

        try {
            PackageManager packageManager = context.getPackageManager();
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
    public static @Nullable CharSequence getAppLabel(@Nullable Context context) {
        if (context == null) {
            return null;
        }

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
    public static @Nullable String getAppVersion(@Nullable Context context,
            @Nullable String packageName) {
        if (context == null || packageName == null) {
            return null;
        }

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
    public static @Nullable String getAppVersion(@Nullable Context context) {
        if (context == null) {
            return null;
        }

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
    public static @Nullable Drawable getAppIcon(@Nullable Context context,
            @Nullable String packageName) {
        if (context == null || packageName == null) {
            return null;
        }

        try {
            return context.getPackageManager().getApplicationIcon(packageName);
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
    public static @Nullable Drawable getAppIcon(@Nullable Context context) {
        if (context == null) {
            return null;
        }

        return getAppIcon(context, context.getPackageName());
    }

    /**
     * Load activity icon from the given component name.
     *
     * @param context The context to get the package manager.
     *
     * @return The activity icon drawable.
     *
     * @see PackageManager#getActivityIcon(ComponentName)
     * @see PackageManager#getDefaultActivityIcon()
     */
    public static @Nullable Drawable getActivityIcon(@Nullable Context context,
            @Nullable ComponentName componentName) {
        if (context == null || componentName == null) {
            return null;
        }

        try {
            return context.getPackageManager().getActivityIcon(componentName);
        } catch (Exception e) {
            return context.getPackageManager().getDefaultActivityIcon();
        }
    }

    /**
     * Load activity icon resource from the given component name.
     *
     * @param context The context to get the package manager.
     *
     * @return The activity icon resource.
     *
     * @see PackageManager#getActivityIcon(ComponentName)
     * @see PackageManager#getDefaultActivityIcon()
     */
    public static @DrawableRes int getActivityIconRes(@Nullable Context context,
            @Nullable ComponentName componentName) {
        if (context == null || componentName == null) {
            return ID_NULL;
        }

        try {
            return context.getPackageManager().getActivityInfo(componentName,
                    PackageManager.GET_META_DATA).getIconResource();
        } catch (Exception e) {
            return ID_NULL;
        }
    }

    /**
     * Detects if the given application info is a system app or not.
     *
     * @param applicationInfo The application info of the package.
     *
     * @return {@code true} if the associated package is a system app.
     */
    public static boolean isSystemApp(@Nullable ApplicationInfo applicationInfo) {
        if (applicationInfo == null) {
            return false;
        }

        int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
        return (applicationInfo.flags & mask) != 0;
    }
}
