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

package com.pranavpandey.android.dynamic.util.loader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.pranavpandey.android.dynamic.util.DynamicPackageUtils;
import com.pranavpandey.android.dynamic.util.cache.DrawableLruCache;
import com.pranavpandey.android.dynamic.util.concurrent.DynamicCallback;
import com.pranavpandey.android.dynamic.util.concurrent.DynamicConcurrent;
import com.pranavpandey.android.dynamic.util.loader.handler.ImageViewHandler;
import com.pranavpandey.android.dynamic.util.loader.handler.TextViewHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * An {@link Enum} to load {@link Drawable} from the {@link DrawableLruCache}.
 */
public class DynamicLoader {

    /**
     * Singleton instance of {@link DynamicLoader}.
     */
    private static DynamicLoader sInstance;

    /**
     * Lock object to provide the thread safety.
     */
    private static final Object sLock = new Object();

    /**
     * Cache for the drawables.
     */
    private final DrawableLruCache<String> mDrawableLruCache;

    /**
     * Making default constructor private so that it cannot be initialized directly.
     * <p>Use {@link #getInstance()} instead.
     */
    private DynamicLoader() {
        mDrawableLruCache = new DrawableLruCache<>();
    }

    /**
     * Retrieves the singleton instance of {@link DynamicLoader}.
     * <p>Must be called before accessing the public methods.
     *
     * @return The singleton instance of {@link DynamicLoader}
     */
    public static @NonNull DynamicLoader getInstance() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new DynamicLoader();
            }
        }

        return sInstance;
    }

    /**
     * Returns the drawable cache used by this loader.
     *
     * @return The drawable cache used by this loader.
     */
    public @Nullable DrawableLruCache<String> getDrawableLruCache() {
        return mDrawableLruCache;
    }

    /**
     * Get the drawable in the cache.
     *
     * @param key The cache key of the drawable.
     */
    private @Nullable Drawable getDrawableFromCache(@Nullable String key) {
        if (getDrawableLruCache() == null || key == null) {
            return null;
        }

        return getDrawableLruCache().get(key);
    }

    /**
     * Put the drawable in the cache.
     *
     * @param key The cache key for the drawable.
     * @param drawable The drawable to be cached.
     */
    private void putDrawableInCache(@Nullable String key, @Nullable Drawable drawable) {
        if (key != null && drawable != null && getDrawableLruCache() != null
                && getDrawableFromCache(key) == null) {
            getDrawableLruCache().put(key, drawable);
        }
    }

    /**
     * Clear the drawable cache.
     */
    public void clearDrawables() {
        try {
            if (getDrawableLruCache() != null) {
                getDrawableLruCache().evictAll();
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * Load the drawable resource from the cache.
     *
     * @param context The context to get the resources.
     * @param drawableRes The drawable resource to be used.
     *
     * @return The drawable resource from the cache.
     */
    public @Nullable Drawable loadDrawable(@NonNull Context context,
            @DrawableRes int drawableRes) {
        Drawable drawable = getDrawableFromCache(String.valueOf(drawableRes));
        if (drawable == null) {
            drawable = ContextCompat.getDrawable(context, drawableRes);
        }

        putDrawableInCache(Integer.toString(drawableRes), drawable);
        return drawable;
    }

    /**
     * Load the app icon from the cache.
     *
     * @param context The context to get the package manager.
     * @param packageName The package name to be used.
     *
     * @return The app icon from the cache.
     */
    public @Nullable Drawable loadAppIcon(@NonNull Context context, @Nullable String packageName) {
        Drawable icon = getDrawableFromCache(packageName);
        if (icon == null) {
            icon = DynamicPackageUtils.getAppIcon(context, packageName);
        }

        putDrawableInCache(packageName, icon);
        return icon;
    }

    /**
     * Set the view asynchronously by using the {@link DynamicCallback}.
     *
     * @param executorService The executor service to be used.
     * @param callback The callback to be used.
     * @param <V> The type of the callback view.
     * @param <P> The type of the callback placeholder.
     * @param <R> The type of the callback result.
     *
     * @return The {@link Future} representing the pending completion of the task.
     *
     * @see DynamicConcurrent#async(ExecutorService, Handler, DynamicCallback)
     */
    public @Nullable <V, P, R> Future<?> setAsync(@Nullable ExecutorService executorService,
            @Nullable DynamicCallback<V, P, R> callback) {
        if (callback == null || callback.getView() == null) {
            return null;
        }

        if (callback.getView() instanceof ImageView) {
            return DynamicConcurrent.getInstance().async(executorService,
                    new ImageViewHandler((ImageView) callback.getView()), callback);
        } else if (callback.getView() instanceof TextView) {
            return DynamicConcurrent.getInstance().async(executorService,
                    new TextViewHandler((TextView) callback.getView()), callback);
        } else {
            return null;
        }
    }

    /**
     * Set the view asynchronously by using the {@link DynamicCallback}.
     *
     * @param callback The callback to be used.
     * @param <V> The type of the callback view.
     * @param <P> The type of the callback placeholder.
     * @param <R> The type of the callback result.
     *
     * @return The {@link Future} representing the pending completion of the task.
     *
     * @see DynamicConcurrent#async(Handler, DynamicCallback)
     */
    public @Nullable <V, P, R> Future<?> setAsync(@Nullable DynamicCallback<V, P, R> callback) {
        if (callback == null || callback.getView() == null) {
            return null;
        }

        if (callback.getView() instanceof ImageView) {
            return DynamicConcurrent.getInstance().async(
                    new ImageViewHandler((ImageView) callback.getView()), callback);
        } else if (callback.getView() instanceof TextView) {
            return DynamicConcurrent.getInstance().async(
                    new TextViewHandler((TextView) callback.getView()), callback);
        } else {
            return null;
        }
    }
}
