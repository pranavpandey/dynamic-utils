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

package com.pranavpandey.android.dynamic.util;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Helper class to perform {@link Intent} operations.
 */
public class DynamicIntentUtils {

    /**
     * Returns the intent for the supplied class and flags.
     *
     * @param context The context to create the intent.
     * @param clazz The class for which the intent to be created.
     * @param flags intent flags to be set.
     *
     * @see Intent#setComponent(ComponentName)
     * @see Intent#addFlags(int)
     *
     * @return The intent for the supplied class and flags.
     */
    public static @NonNull Intent getIntent(@NonNull Context context,
            @Nullable Class<?> clazz, int flags) {
        Intent intent;
        if (clazz != null) {
            intent = new Intent(context, clazz);
            intent.setComponent(new ComponentName(context, clazz));
        } else {
            intent = new Intent();
        }

        intent.addFlags(flags);
        return intent;
    }

    /**
     * Returns the intent for the supplied class.
     *
     * @param context The context to create the intent.
     * @param clazz The class for which the intent to be created.
     *
     * @see #getIntent(Context, Class, int)
     *
     * @return The intent for the supplied class.
     */
    public static @NonNull Intent getIntent(@NonNull Context context, @Nullable Class<?> clazz) {
        return getIntent(context, clazz, 0);
    }

    /**
     * Returns the activity intent for the supplied class.
     *
     * @param context The context to create the intent.
     * @param clazz The activity class for which the intent to be created.
     *
     * @see Intent#FLAG_ACTIVITY_NEW_TASK
     * @see Intent#FLAG_ACTIVITY_CLEAR_TOP
     * @see #getIntent(Context, Class, int)
     *
     * @return The activity intent for the supplied class.
     */
    public static @NonNull Intent getActivityIntent(
            @NonNull Context context, @Nullable Class<?> clazz) {
        return getIntent(context, clazz, Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * Returns the activity intent for the supplied class to get the result.
     *
     * @param context The context to create the intent.
     * @param clazz The activity class for which the intent to be created.
     *
     * @see Intent#FLAG_ACTIVITY_CLEAR_TOP
     * @see #getIntent(Context, Class, int)
     *
     * @return The activity intent for the supplied class to get the result.
     */
    public static @NonNull Intent getActivityIntentForResult(
            @NonNull Context context, @Nullable Class<?> clazz) {
        return getIntent(context, clazz, Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    /**
     * Returns the intent stream or data URI according to the supplied action.
     *
     * @param intent The intent to get the URI.
     * @param action The action to return stream URI.
     *
     * @return The intent URI according to the stream or data.
     *
     * @see Intent#EXTRA_STREAM
     * @see Intent#getData()
     */
    public static @Nullable Uri getStreamOrData(@Nullable Intent intent, @NonNull String action) {
        if (intent == null) {
            return null;
        }

        if (intent.getAction() != null && action.equals(intent.getAction())
                && intent.getParcelableExtra(Intent.EXTRA_STREAM) != null) {
            return intent.getParcelableExtra(Intent.EXTRA_STREAM);
        }

        return intent.getData();
    }

    /**
     * Checks whether the supplied intent has at least one activity to handle it.
     *
     * @param context The context to get the package manager.
     * @param intent The intent to be resolved.
     *
     * @return {@code true} if the supplied intent has at least one activity to handle it.
     *
     * @see PackageManager#resolveActivity(Intent, int)
     */
    public static boolean isActivityResolved(@Nullable Context context, @Nullable Intent intent) {
        if (context == null || intent == null) {
            return false;
        }

        return (context.getPackageManager().resolveActivity(
                intent, PackageManager.MATCH_DEFAULT_ONLY) != null);
    }

    /**
     * Checks the availability of a file picker.
     *
     * @param context The context to get the package manager.
     * @param downloads {@code true} to consider the download location on older API levels.
     *
     * @return {@code true} if a file picker is present.
     *
     * @see #isActivityResolved(Context, Intent)
     * @see Intent#ACTION_GET_CONTENT
     * @see Intent#ACTION_OPEN_DOCUMENT
     *
     * @see Environment#getExternalStoragePublicDirectory(String)
     * @see Environment#DIRECTORY_DOWNLOADS
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isFilePicker(@Nullable Context context, boolean downloads) {
        if (context == null) {
            return false;
        }

        final Intent intent;
        if (DynamicSdkUtils.is19()) {
             intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
             intent.setType(DynamicFileUtils.FILE_MIME);
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);

            if (downloads) {
                return Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS) != null;
            }
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        return isActivityResolved(context, intent);
    }

    /**
     * Checks the availability of a file picker.
     *
     * @param context The context to get the package manager.
     *
     * @return {@code true} if a file picker is present.
     *
     * @see #isFilePicker(Context)
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isFilePicker(@Nullable Context context) {
        return isFilePicker(context, false);
    }

    /**
     * Add mutability flag to the {@link PendingIntent}.
     *
     * @param flags The flags to be updated.
     * @param mutable {@code true} to make it mutable.
     *
     * @return The updated flags.
     * 
     * @see PendingIntent#FLAG_IMMUTABLE
     */
    @TargetApi(Build.VERSION_CODES.S)
    public static int addMutabilityFlag(int flags, boolean mutable) {
        if (DynamicSdkUtils.is23() && !mutable) {
            return flags | PendingIntent.FLAG_IMMUTABLE;
        } else if (DynamicSdkUtils.is31() && mutable) {
            return flags | PendingIntent.FLAG_MUTABLE;
        }

        return flags;
    }

    /**
     * Add mutable flag to the {@link PendingIntent}.
     *
     * @param flags The flags to be updated.
     *
     * @return The updated flags.
     *
     * @see #addMutabilityFlag(int, boolean)
     */
    public static int addMutableFlag(int flags) {
        return addMutabilityFlag(flags, true);
    }

    /**
     * Add immutable flag to the {@link PendingIntent}.
     *
     * @param flags The flags to be updated.
     *
     * @return The updated flags.
     *
     * @see #addMutabilityFlag(int, boolean)
     * @see PendingIntent#FLAG_IMMUTABLE
     */
    public static int addImmutableFlag(int flags) {
        return addMutabilityFlag(flags, false);
    }

    /**
     * View any Intent in the available app or browser.
     * <p>Use {@code queries} tag for {@link Intent#ACTION_VIEW} with appropriate
     * {@code scheme(s)} in {@code AndroidManifest} to support API 30.
     *
     * @param context The context to retrieve the resources.
     * @param intent The intent to view.
     *
     * @return {@code true} on successful operation.
     *
     * @see Intent#ACTION_VIEW
     */
    @TargetApi(Build.VERSION_CODES.R)
    public static boolean viewIntent(@Nullable Context context, @Nullable Intent intent) {
        if (context == null || intent == null) {
            return false;
        }

        Intent intentTemp = new Intent(intent);
        intentTemp.addCategory(Intent.CATEGORY_BROWSABLE);
        intentTemp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            if (DynamicSdkUtils.is30()) {
                intentTemp.addFlags(Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER);
            }

            context.startActivity(intentTemp);
            return true;
        } catch (Exception ignored) {
            intentTemp = new Intent(intent);
            intentTemp.addCategory(Intent.CATEGORY_BROWSABLE);
            intentTemp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (isActivityResolved(context, intentTemp)) {
                context.startActivity(intentTemp);
                return true;
            }
        }

        return false;
    }
}
