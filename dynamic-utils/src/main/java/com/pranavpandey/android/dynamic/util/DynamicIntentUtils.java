/*
 * Copyright 2017-2022 Pranav Pandey
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
     * Action to capture the matrix result.
     */
    public static final String ACTION_MATRIX_CAPTURE_RESULT =
            "com.pranavpandey.matrix.intent.action.CAPTURE_RESULT";

    /**
     * Intent extra key for the matrix data.
     */
    public static final String EXTRA_MATRIX_DATA =
            "com.pranavpandey.matrix.intent.extra.CODE_DATA";

    /**
     * Intent extra key for the matrix format.
     */
    public static final String EXTRA_MATRIX_FORMAT =
            "com.pranavpandey.matrix.intent.extra.CODE_FORMAT";

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
    public static @NonNull Intent getIntent(@Nullable Context context,
            @Nullable Class<?> clazz, int flags) {
        Intent intent;
        if (context != null && clazz != null) {
            intent = new Intent(context, clazz);
            intent.setComponent(new ComponentName(context, clazz));
        } else {
            intent = new Intent();
        }

        return intent.addFlags(flags);
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
    public static @NonNull Intent getIntent(@Nullable Context context, @Nullable Class<?> clazz) {
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
            @Nullable Context context, @Nullable Class<?> clazz) {
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
            @Nullable Context context, @Nullable Class<?> clazz) {
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
     * @param mimeType The mime type for the file.
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
    public static boolean isFilePicker(@Nullable Context context,
            @Nullable String mimeType, boolean downloads) {
        if (context == null) {
            return false;
        }

        if (mimeType == null) {
            mimeType = DynamicFileUtils.MIME_ALL;
        }

        final Intent intent;
        if (DynamicSdkUtils.is19()) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }

        intent.setType(mimeType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        return isActivityResolved(context, intent)
                || (downloads && Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS) != null);
    }

    /**
     * Checks the availability of a file picker.
     *
     * @param context The context to get the package manager.
     * @param mimeType The mime type for the file.
     *
     * @return {@code true} if a file picker is present.
     *
     * @see #isFilePicker(Context, String, boolean)
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isFilePicker(@Nullable Context context, @Nullable String mimeType) {
        return isFilePicker(context, mimeType, false);
    }

    /**
     * Checks the availability of a file picker.
     *
     * @param context The context to get the package manager.
     * @param downloads {@code true} to consider the download location on older API levels.
     *
     * @return {@code true} if a file picker is present.
     *
     * @see #isFilePicker(Context, String, boolean)
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isFilePicker(@Nullable Context context, boolean downloads) {
        return isFilePicker(context, null, downloads);
    }

    /**
     * Checks the availability of a file picker.
     *
     * @param context The context to get the package manager.
     *
     * @return {@code true} if a file picker is present.
     *
     * @see #isFilePicker(Context, String, boolean)
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isFilePicker(@Nullable Context context) {
        return isFilePicker(context, null, false);
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
     * @param context The context to be used.
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

                if (!isActivityResolved(context, intentTemp)) {
                    intentTemp.removeFlags(Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER);
                }
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

    /**
     * Checks whether the device can capture the matrix result.
     *
     * @param context The context to get the package manager.
     *
     * @return {@code true} if the device has at least one activity to capture the matrix result.
     *
     * @see #isActivityResolved(Context, Intent)
     * @see #ACTION_MATRIX_CAPTURE_RESULT
     */
    public static boolean isMatrixCaptureResult(@Nullable Context context) {
        return isActivityResolved(context, new Intent(ACTION_MATRIX_CAPTURE_RESULT));
    }

    /**
     * Returns the intent to capture the matrix result.
     *
     * @param context The context to get the intent.
     *
     * @return The intent to capture the matrix result.
     *
     * @see #getActivityIntentForResult(Context, Class)
     * @see #ACTION_MATRIX_CAPTURE_RESULT
     */
    public static @NonNull Intent getMatrixCaptureResultIntent(@Nullable Context context) {
        return getActivityIntentForResult(context, null)
                .setAction(ACTION_MATRIX_CAPTURE_RESULT);
    }
}
