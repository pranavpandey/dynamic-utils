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
    public static void setBackground(@NonNull View view, @NonNull Drawable drawable) {
        if (DynamicVersionUtils.isJellyBean()) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
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
     * @see android.graphics.drawable.Drawable#setColorFilter(int, PorterDuff.Mode)
     * @see android.graphics.PorterDuff.Mode
     */
    public static Drawable colorizeDrawable(
            @NonNull Drawable drawable, boolean wrap, @ColorInt int color,
            @Nullable PorterDuff.Mode mode) {
        if (drawable != null) {
            if (wrap) {
                if (DynamicVersionUtils.isLollipop()) {
                    drawable = DrawableCompat.wrap(drawable.mutate());
                    if (mode != null) {
                        DrawableCompat.setTintMode(drawable, mode);
                    }
                } else {
                    drawable = drawable.mutate();
                }
            }

            if (DynamicVersionUtils.isLollipop()) {
                DrawableCompat.setTint(drawable, color);
            } else {
                drawable.setColorFilter(color, mode);
            }

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
     * @see android.graphics.drawable.Drawable#setColorFilter(int, PorterDuff.Mode)
     * @see android.graphics.PorterDuff.Mode
     */
    public static Drawable colorizeDrawable(
            @NonNull Drawable drawable, @ColorInt int color,
            @Nullable PorterDuff.Mode mode) {
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
     * @see android.graphics.drawable.Drawable#setColorFilter(int, PorterDuff.Mode)
     * @see android.graphics.PorterDuff.Mode#SRC_IN
     */
    public static Drawable colorizeDrawable(
            @NonNull Drawable drawable, boolean wrap, @ColorInt int color) {
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
    public static Drawable colorizeDrawable(
            @NonNull Drawable drawable, @ColorInt int color) {
        return colorizeDrawable(drawable, true, color);
    }
}
