/*
 * Copyright (C) 2017 Pranav Pandey
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
import android.support.annotation.NonNull;

/**
 * Helper class to get package or app related information.
 */
public class DynamicPackageUtils {

    /**
     * Get component name from the given context.
     *
     * @param context Context to build the component. Usually your
     *                {@link android.app.Application} or {@link android.app.Activity}
     *                object.
     *
     * @return Component name from the given context.
     *
     * @see android.content.ComponentName#ComponentName(String, String)
     */
    public static ComponentName getComponentName(@NonNull Context context) {
        return new ComponentName(context.getPackageName(), context.getClass().getName());
    }

    /**
     * Get application label from the given context.
     *
     * @param context Context to retrieve the label. Usually your
     *                {@link android.app.Application} or {@link android.app.Activity}
     *                object.
     *
     * @return Application label or name.
     *
     * @see android.content.pm.ApplicationInfo#loadLabel(PackageManager)
     */
    public static CharSequence getAppLabel(@NonNull Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA).applicationInfo.loadLabel(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get application version name from the given context.
     *
     * @param context Context to retrieve the version name. Usually your
     *                {@link android.app.Application} or {@link android.app.Activity}
     *                object.
     *
     * @return Application version name.
     *
     * @see android.content.pm.PackageInfo#versionName
     */
    public static String getAppVersion(@NonNull Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Load application icon from the given context.
     *
     * @param context Context to retrieve the app icon. Usually your
     *                {@link android.app.Application} or {@link android.app.Activity}
     *                object.
     *
     * @return Application icon drawable.
     *
     * @see android.content.pm.ApplicationInfo#loadIcon(PackageManager)
     */
    public static Drawable getAppIcon(@NonNull Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA).applicationInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * To detect the given ApplicationInfo is a system app or not.
     *
     * @param applicationInfo ApplicationInfo of the package.
     *
     * @return {@code true} if the give package is a system app.
     */
    public static boolean isSystemApp(@NonNull ApplicationInfo applicationInfo) {
        int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
        return (applicationInfo.flags & mask) != 0;
    }
}
