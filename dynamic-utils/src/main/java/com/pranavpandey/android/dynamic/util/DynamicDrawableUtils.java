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
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * Helper class to perform {@link Drawable} operations.
 */
public class DynamicDrawableUtils {

    /**
     * Set background of a given view in an efficient way by detecting the Android SDK
     * at runtime.
     *
     * @param view The view to set the background.
     * @param drawable The background drawable to be set.
     *
     * @see DynamicSdkUtils#is16()
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setBackground(@NonNull View view, @Nullable Drawable drawable) {
        if (DynamicSdkUtils.is16()) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * Apply color filter and return the mutated drawable so that, all other references
     * do not change.
     *
     * @param drawable The drawable to be colorized.
     * @param wrap {@code true} to {@code wrap} the drawable so that it may be used for
     *             tinting across the different API levels.
     * @param colorFilter The color filter to be applied on the drawable.
     *
     * @return The drawable after applying the color filter.
     *
     * @see Drawable#setColorFilter(ColorFilter)
     * @see ColorFilter
     */
    public static @Nullable Drawable colorizeDrawable(@Nullable Drawable drawable,
            boolean wrap, @NonNull ColorFilter colorFilter) {
        if (drawable != null) {
            if (wrap) {
                drawable = drawable.mutate();
            }

            drawable.setColorFilter(colorFilter);
            drawable.invalidateSelf();
        }

        return drawable;
    }

    /**
     * Colorize and return the mutated drawable so that, all other references do not change.
     *
     * @param drawable The drawable to be colorized.
     * @param color The color to colorize the drawable.
     * @param wrap {@code true} to {@code wrap} the drawable so that it may be used for
     *             tinting across the different API levels.
     * @param mode The porter duff mode to be used.
     *
     * @return The drawable after applying the color filter.
     *
     * @see Drawable#setColorFilter(int, PorterDuff.Mode)
     * @see PorterDuff.Mode
     */
    @SuppressWarnings("deprecation")
    public static @Nullable Drawable colorizeDrawable(@Nullable Drawable drawable,
            boolean wrap, @ColorInt int color, @Nullable PorterDuff.Mode mode) {
        if (drawable != null) {
            if (mode == null) {
                mode = PorterDuff.Mode.SRC_IN;
            }

            if (wrap) {
                // Handle exception with layer drawables on older API levels.
                try {
                    drawable = DrawableCompat.wrap(drawable);
                    drawable = drawable.mutate();
                } catch (Exception ignored) {
                }
            }

            DrawableCompat.setTintMode(drawable, mode);
            DrawableCompat.setTint(drawable, color);
            drawable.invalidateSelf();
        }

        return drawable;
    }

    /**
     * Colorize and return the mutated drawable so that, all other references do not change.
     *
     * @param drawable The drawable to be colorized.
     * @param color The color to colorize the drawable.
     * @param mode The porter duff mode to be used.
     *
     * @return The drawable after applying the color filter.
     *
     * @see Drawable#setColorFilter(int, PorterDuff.Mode)
     * @see PorterDuff
     */
    public static @Nullable Drawable colorizeDrawable(@Nullable Drawable drawable,
            @ColorInt int color, @Nullable PorterDuff.Mode mode) {
        return colorizeDrawable(drawable, true, color, mode);
    }

    /**
     * Colorize and return the mutated drawable so that, all other references do not change.
     *
     * @param drawable The drawable to be colorized.
     * @param wrap {@code true} to {@code wrap} the drawable so that it may be used for
     *             tinting across the different API levels.
     * @param color The color to colorize the drawable.
     *
     * @return The drawable after applying the color filter.
     *
     * @see Drawable#setColorFilter(int, PorterDuff.Mode)
     * @see PorterDuff.Mode#SRC_IN
     */
    public static @Nullable Drawable colorizeDrawable(@Nullable Drawable drawable,
            boolean wrap, @ColorInt int color) {
        return colorizeDrawable(drawable, wrap, color, PorterDuff.Mode.SRC_IN);
    }

    /**
     * Colorize and return the mutated drawable so that, all other references do not change.
     *
     * @param drawable The drawable to be colorized.
     * @param color The color to colorize the drawable.
     *
     * @return The drawable after applying the color filter.
     */
    public static @Nullable Drawable colorizeDrawable(
            @Nullable Drawable drawable, @ColorInt int color) {
        return colorizeDrawable(drawable, true, color);
    }

    /**
     * Apply color filter and return the mutated drawable so that, all other references
     * do not change.
     *
     * @param drawable The drawable to be colorized.
     * @param colorFilter The color filter to be applied on the drawable.
     *
     * @return The drawable after applying the color filter.
     *
     * @see Drawable#setColorFilter(ColorFilter)
     * @see ColorFilter
     */
    public static @Nullable Drawable colorizeDrawable(@Nullable Drawable drawable,
            @NonNull ColorFilter colorFilter) {
        return colorizeDrawable(drawable, true, colorFilter);
    }

    /**
     * Get a gradient drawable according to the supplied drawable.
     *
     * @param width The width in dip for the drawable.
     * @param height The height in dip for the drawable.
     * @param drawable The drawable drawable to create the gradient drawable.
     * @param color The color for the drawable.
     *
     * @return The gradient drawable according to the supplied parameters.
     */
    public static @NonNull Drawable getCornerDrawable(int width, int height,
            @NonNull GradientDrawable drawable, @ColorInt int color) {
        drawable.setColor(color);

        if (width > 0 && height > 0) {
            drawable.setSize(DynamicUnitUtils.convertDpToPixels(width),
                    DynamicUnitUtils.convertDpToPixels(height));
        }

        return drawable;
    }

    /**
     * Get a gradient drawable according to the supplied drawable.
     *
     * @param width The width in dip for the drawable.
     * @param height The height in dip for the drawable.
     * @param drawable The drawable drawable to create the gradient drawable.
     *
     * @return The gradient drawable according to the supplied parameters.
     */
    public static @NonNull Drawable getCornerDrawable(int width,
            int height, @NonNull GradientDrawable drawable) {
        return getCornerDrawable(width, height, drawable, Color.WHITE);
    }

    /**
     * Get a gradient drawable according to the supplied drawable.
     * 
     * @param drawable The drawable drawable to create the gradient drawable.
     * @param color The color for the drawable.
     *
     * @return The gradient drawable according to the supplied parameters.
     */
    public static @NonNull Drawable getCornerDrawable(
            @NonNull GradientDrawable drawable, @ColorInt int color) {
        return getCornerDrawable(0, 0, drawable, color);
    }

    /**
     * Get a gradient drawable according to the supplied drawable.
     *
     * @param drawable The drawable drawable to create the gradient drawable.
     *
     * @return The gradient drawable according to the supplied parameters.
     */
    public static @NonNull Drawable getCornerDrawable(@NonNull GradientDrawable drawable) {
        return getCornerDrawable(0, 0, drawable, Color.WHITE);
    }

    /**
     * Get a gradient drawable according to the corner radius.
     *
     * @param width The width in dip for the drawable.
     * @param height The height in dip for the drawable.
     * @param cornerRadius The corner size in dip for the drawable.
     * @param color The color for the drawable.
     *
     * @return The gradient drawable according to the supplied parameters.
     */
    public static @NonNull Drawable getCornerDrawable(int width, int height,
            float cornerRadius, @ColorInt int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(DynamicUnitUtils.convertDpToPixels(cornerRadius));
        
        return getCornerDrawable(width, height, drawable, color);
    }

    /**
     * Get a gradient drawable according to the corner radius.
     *
     * @param width The width in dip for the drawable.
     * @param height The height in dip for the drawable.
     * @param cornerRadius The corner size in dip for the drawable.
     *
     * @return The gradient drawable according to the supplied parameters.
     */
    public static @NonNull Drawable getCornerDrawable(int width, int height, float cornerRadius) {
        return getCornerDrawable(width, height, cornerRadius, Color.WHITE);
    }

    /**
     * Get a gradient drawable according to the corner radius.
     *
     * @param cornerRadius The corner size in dip for the drawable.
     * @param color The color for the drawable.
     *
     * @return The gradient drawable according to the supplied parameters.
     */
    public static @NonNull Drawable getCornerDrawable(float cornerRadius, @ColorInt int color) {
        return getCornerDrawable(0, 0, cornerRadius, color);
    }

    /**
     * Get a gradient drawable according to the corner radius.
     *
     * @param cornerRadius The corner size in dip for the drawable.
     *
     * @return The gradient drawable according to the supplied parameters.
     */
    public static @NonNull Drawable getCornerDrawable(float cornerRadius) {
        return getCornerDrawable(0, 0, cornerRadius, Color.WHITE);
    }
}
