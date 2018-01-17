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

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;

/**
 * Helper class to perform {@link Drawable} operations.
 */
public class DynamicDrawableUtils {

    /**
     * Set background of a given view in an efficient way by detecting
     * the Android SDK at runtime
     *
     * @param view View to set the background.
     * @param drawable Background drawable for the view.
     *
     * @see DynamicVersionUtils
     */
    public static void setBackground(@NonNull View view, @Nullable Drawable drawable) {
        if (DynamicVersionUtils.isJellyBean()) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * Apply color filter and return the mutated drawable so that, all other
     * references do not change.
     *
     * @param drawable Drawable to be colorized.
     * @param wrap {@code true} if  wrap {@code drawable} so that it may be
     *             used for tinting across the different API levels.
     * @param colorFilter Color filter to be applied on the drawable.
     *
     * @return Drawable after applying the color filter.
     *
     * @see Drawable#setColorFilter(ColorFilter)
     * @see ColorFilter
     */
    public static @Nullable Drawable colorizeDrawable(
            @Nullable Drawable drawable, boolean wrap, @NonNull ColorFilter colorFilter) {
        if (drawable != null) {
            if (wrap) {
                drawable = DrawableCompat.wrap(drawable.mutate());
            }

            drawable.setColorFilter(colorFilter);

            if (!DynamicVersionUtils.isMarshmallow()) {
                drawable.invalidateSelf();
            }
        }

        return drawable;
    }

    /**
     * Colorize and return the mutated drawable so that, all other references
     * do not change.
     *
     * @param drawable Drawable to be colorized.
     * @param color Color to colorize the drawable.
     * @param wrap {@code true} if  wrap {@code drawable} so that it may be
     *             used for tinting across the different API levels.
     * @param mode PorterDuff mode.
     *
     * @return Drawable after applying the color filter.
     *
     * @see Drawable#setColorFilter(int, PorterDuff.Mode)
     * @see PorterDuff.Mode
     */
    public static @Nullable Drawable colorizeDrawable(
            @Nullable Drawable drawable, boolean wrap,
            @ColorInt int color, @Nullable PorterDuff.Mode mode) {
        if (drawable != null) {
            if (wrap) {
                drawable = DrawableCompat.wrap(drawable.mutate());
            }

            if (mode != null) {
                DrawableCompat.setTintMode(drawable, mode);
            } else {
                DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
            }
            DrawableCompat.setTint(drawable, color);

            if (!DynamicVersionUtils.isMarshmallow()) {
                drawable.invalidateSelf();
            }
        }

        return drawable;
    }

    /**
     * Colorize and return the mutated drawable so that, all other references
     * do not change.
     *
     * @param drawable Drawable to be colorized.
     * @param color Color to colorize the drawable.
     * @param mode PorterDuff mode.
     *
     * @return Drawable after applying the color filter.
     *
     * @see Drawable#setColorFilter(int, PorterDuff.Mode)
     * @see PorterDuff
     */
    public static @Nullable Drawable colorizeDrawable(
            @Nullable Drawable drawable, @ColorInt int color, @Nullable PorterDuff.Mode mode) {
        return colorizeDrawable(drawable, true, color, mode);
    }

    /**
     * Colorize and return the mutated drawable so that, all other references
     * do not change.
     *
     * @param drawable Drawable to be colorized.
     * @param wrap {@code true} if  wrap {@code drawable} so that it may be
     *             used for tinting across the different API levels.
     * @param color Color to colorize the drawable.
     *
     * @return Drawable after applying the color filter.
     *
     * @see Drawable#setColorFilter(int, PorterDuff.Mode)
     * @see PorterDuff.Mode#SRC_IN
     */
    public static @Nullable Drawable colorizeDrawable(
            @Nullable Drawable drawable, boolean wrap, @ColorInt int color) {
        return colorizeDrawable(drawable, wrap, color, PorterDuff.Mode.SRC_IN);
    }

    /**
     * Colorize and return the mutated drawable so that, all other references
     * do not change.
     *
     * @param drawable Drawable to be colorized.
     * @param color Color to colorize the drawable.
     *
     * @return Drawable after applying the color filter.
     */
    public static @Nullable Drawable colorizeDrawable(
            @Nullable Drawable drawable, @ColorInt int color) {
        return colorizeDrawable(drawable, true, color);
    }

    /**
     * Apply color filter and return the mutated drawable so that, all other
     * references do not change.
     *
     * @param drawable Drawable to be colorized.
     * @param colorFilter Color filter to be applied on the drawable.
     *
     * @return Drawable after applying the color filter.
     *
     * @see Drawable#setColorFilter(ColorFilter)
     * @see ColorFilter
     */
    public static @Nullable Drawable colorizeDrawable(@Nullable Drawable drawable,
                                                      @NonNull ColorFilter colorFilter) {
        return colorizeDrawable(drawable, true, colorFilter);
    }
}
