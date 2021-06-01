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

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

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
     * Returns the intent stream or data uri according to the supplied action.
     *
     * @param intent The intent to get the uri.
     * @param action The action to return stream uri.
     *
     * @return The intent uri according to the stream or data.
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
     * @param context The context to ge the package manager.
     * @param intent The intent to be resolved.
     *
     * @return {@code true} if the supplied intent has at least one activity to handle it.
     */
    public static boolean isActivityResolved(@Nullable Context context, @Nullable Intent intent) {
        if (context == null || intent == null) {
            return false;
        }

        return (context.getPackageManager().resolveActivity(
                intent, PackageManager.MATCH_DEFAULT_ONLY) != null);
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
    @TargetApi(Build.VERSION_CODES.M)
    public static int addMutabilityFlag(int flags, boolean mutable) {
        if (DynamicSdkUtils.is23() && !mutable) {
            return flags | PendingIntent.FLAG_IMMUTABLE;
        } else if (DynamicSdkUtils.isS() && mutable) {
            // TODO: Add mutability flag.
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
