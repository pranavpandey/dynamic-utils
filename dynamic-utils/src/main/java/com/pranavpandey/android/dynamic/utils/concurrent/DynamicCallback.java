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

package com.pranavpandey.android.dynamic.utils.concurrent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Callback to retrieve the result dynamically.
 *
 * @param <V> The type of the view.
 * @param <P> The type of the placeholder.
 * @param <R> The type of the result.
 */
public abstract class DynamicCallback<V, P, R> {

    /**
     * The view used by this callback.
     */
    private final WeakReference<V> mView;

    /**
     * Constructor to initialize an object of this class.
     *
     * @param view The view to be used.
     */
    public DynamicCallback(@Nullable V view) {
        this.mView = new WeakReference<>(view);
    }

    /**
     * This method will be called to retrieve the placeholder before getting the result.
     *
     * @param view The view used by this callback.
     *
     * @return The placeholder from this callback.
     */
    public @Nullable P onPlaceholder(@NonNull V view) {
        return null;
    }

    /**
     * This method will be called to retrieve the result from this callback.
     *
     * @param view The view used by this callback.
     *
     * @return The result from this callback.
     */
    public abstract @Nullable R onResult(@NonNull V view);

    /**
     * Returns the view used by this callback.
     *
     * @return The view used by this callback.
     */
    public @Nullable V getView() {
        return mView.get();
    }
}
