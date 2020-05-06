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
import android.content.Intent;
import android.net.Uri;

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
            @NonNull Class<?> clazz, int flags) {
        Intent intent = new Intent(context, clazz);
        intent.setComponent(new ComponentName(context, clazz));
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
    public static @NonNull Intent getIntent(@NonNull Context context, @NonNull Class<?> clazz) {
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
            @NonNull Context context, @NonNull Class<?> clazz) {
        return getIntent(context, clazz, Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
    public @NonNull static Intent getActivityIntentForResult(
            @NonNull Context context, @NonNull Class<?> clazz) {
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

        if (intent.getAction() != null && intent.getAction().equals(action)
                && intent.getParcelableExtra(Intent.EXTRA_STREAM) != null) {
            return (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        }

        return intent.getData();
    }
}
