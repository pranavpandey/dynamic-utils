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

package com.pranavpandey.android.dynamic.utils.concurrent.task;

import android.content.Context;

import androidx.annotation.Nullable;

import com.pranavpandey.android.dynamic.utils.concurrent.DynamicTask;

import java.lang.ref.WeakReference;

/**
 * A {@link DynamicTask} to perform operations with {@link Context}.
 *
 * @param <T> The type of the params.
 * @param <P> The type of the progress.
 * @param <R> The type of the result.
 */
public abstract class ContextTask<T, P, R> extends DynamicTask<T, P, R> {

    /**
     * Context used by this task.
     */
    private final WeakReference<Context> mContext;

    /**
     * Constructor to initialize an object of this class.
     *
     * @param context The context for this task.
     */
    public ContextTask(@Nullable Context context) {
        this.mContext = new WeakReference<>(context);
    }

    /**
     * Get the context used by this task.
     *
     * @return The context used by this task.
     */
    public @Nullable Context getContext() {
        if (mContext == null) {
            return null;
        }

        return mContext.get();
    }
}
