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

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

/**
 * Helper class to change colors dynamically.
 */
public class DynamicColorUtils {

    /**
     * Visible contrast between the two colors.
     */
    private static final float VISIBLE_CONTRAST = 0.32f;

    /**
     * Amount to calculate the tint of a color.
     */
    private static final float TINT_FACTOR = 0.68f;

    /**
     * Amount to calculate the contrast color.
     */
    private static final float CONTRAST_FACTOR = 0.6f;

    /**
     * Calculate tint based on a given color for better readability.
     *
     * @param color Color whose tint to be calculated.
     *
     * @return Tint of the given color.
     */
    public static @ColorInt int getTintColor(@ColorInt int color) {
        if (isColorDark(color)) {
            return getLighterColor(color, TINT_FACTOR);
        } else {
            return getDarkerColor(color, TINT_FACTOR);
        }
    }

    /**
     * Lightens a color by a given amount.
     *
     * @param color The color to lighten.
     * @param amount The amount to lighten the color.
     *               0 will leave the color unchanged.
     *               1 will make the color completely white.
     *
     * @return The lighter color.
     */
    public static @ColorInt int getLighterColor(@ColorInt int color, float amount) {
        int red = (int) ((Color.red(color) * (1 - amount) / 255 + amount) * 255);
        int green = (int) ((Color.green(color) * (1 - amount) / 255 + amount) * 255);
        int blue = (int) ((Color.blue(color) * (1 - amount) / 255 + amount) * 255);

        return Color.argb(Color.alpha(color), red, green, blue);
    }

    /**
     * Darkens a color by a given amount.
     *
     * @param color The color to darken.
     * @param amount The amount to darken the color.
     *               0 will leave the color unchanged.
     *               1 will make the color completely black.
     *
     * @return The darker color.
     */
    public static @ColorInt int getDarkerColor(@ColorInt int color, float amount) {
        int red = (int) ((Color.red(color) * (1 - amount) / 255) * 255);
        int green = (int) ((Color.green(color) * (1 - amount) / 255) * 255);
        int blue = (int) ((Color.blue(color) * (1 - amount) / 255) * 255);

        return Color.argb(Color.alpha(color), red, green, blue);
    }

    /**
     * Calculate accent color based on the given color for android theme generation.
     * Still in beta so, sometimes may be inaccurate colors.
     *
     * @param color Color whose accent color to be calculated.
     *
     * @return Accent color based on the given color.
     */
    public static @ColorInt int getAccentColor(@ColorInt int color) {
        int finalColor;
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        double Y = ((r * 299) + (g * 587) + (b * 114)) / 1000;

        int rc = b ^ 0x55;
        int gc = g & 0xFA;
        int bc = r ^ 0x55;

        finalColor = Color.argb(a, rc, gc, bc);
        int r1 = Color.red(finalColor);
        int g1 = Color.green(finalColor);
        int b1 = Color.blue(finalColor);
        double YC = ((r1 * 299) + (g1 * 587) + (b1 * 114)) / 1000;

        int CD = (Math.max(r, r1) - Math.min(r, r1)) + (Math.max(g, g1) - Math.min(g, g1))
                + (Math.max(b, b1) - Math.min(b, b1));
        if ((Y - YC <= 50) && CD <= 200) {
            rc = b ^ 0xFA;
            gc = g & 0x55;
            bc = r ^ 0x55;
        }

        finalColor = Color.argb(a, rc, gc, bc);
        return finalColor;
    }

    /**
     * Calculate contrast of a color based on the given base color so
     * that it will be visible always on top of the base color.
     *
     * @param color Color whose contrast to be calculated.
     * @param contrastWith Background color to calculate the contrast.
     *
     * @return Contrast of the given color according to the base color.
     */
    public static @ColorInt int getContrastColor(
            @ColorInt int color, @ColorInt int contrastWith) {
        float contrast = calculateContrast(color, contrastWith);
        if (contrast < VISIBLE_CONTRAST) {
            if (isColorDark(contrastWith)) {
                return getLighterColor(color,
                        Math.max(VISIBLE_CONTRAST + contrast, CONTRAST_FACTOR));
            } else {
                return getDarkerColor(color,
                        Math.max(VISIBLE_CONTRAST + contrast, CONTRAST_FACTOR));
            }
        }

        return color;
    }

    /**
     * Detect light or dark color.
     *
     * @param color Color whose darkness to be calculated.
     *
     * @return {@code true} if color is dark.
     */
    public static boolean isColorDark(@ColorInt int color) {
        return getColorDarkness(color) >= 0.5;
    }

    /**
     * Calculate darkness of a color.
     *
     * @param color Color whose darkness to be calculated.
     *
     * @return Darkness of color (less that or equal to 1); 0 for white and 1 for black.
     */
    public static double getColorDarkness(@ColorInt int color) {
        return 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color)
                + 0.114 * Color.blue(color)) / 255;
    }

    /**
     * Calculate luma value according to XYZ color space of a color.
     *
     * @param color Color whose XyzLuma to be calculated.
     *
     * @return Luma value according to XYZ color space in the range 0.0 - 1.0.
     */
    private static float calculateXyzLuma(@ColorInt int color) {
        return (0.2126f * Color.red(color) +
                0.7152f * Color.green(color) +
                0.0722f * Color.blue(color)) / 255f;
    }

    /**
     * Calculate color contrast difference between two colors based
     * on luma value according to XYZ color space.
     *
     * @param color1 First color to calculate the contrast difference.
     * @param color2 Second color to calculate the contrast difference.
     *
     * @return Color contrast between the two colors.
     *
     * @see #calculateXyzLuma(int)
     */
    public static float calculateContrast(@ColorInt int color1, @ColorInt int color2) {
        return Math.abs(calculateXyzLuma(color1) - calculateXyzLuma(color2));
    }

    /**
     * Shift a color according to the given parameter. Useful to create
     * different color states.
     *
     * @param color Color to be shifted.
     * @param by Factor in float by which shift the color.
     *
     * @return Shifted color.
     */
    public static @ColorInt int shiftColor(
            @ColorInt int color, @FloatRange(from = 0.0f, to = 2.0f) float by) {
        if (by == 1f) {
            return color;
        }

        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= by;
        return Color.HSVToColor(hsv);
    }

    /**
     * Calculate less visible color of a given color. Useful to create
     * unselected or disabled color states.
     *
     * @param color Color whose less visible color to be calculated.
     *
     * @return Less visible color by shifting the color.
     */
    public static @ColorInt int getLessVisibleColor(@ColorInt int color) {
        return shiftColor(color, isColorDark(color) ? 0.6f : 1.6f);
    }

    /**
     * Adjust alpha of a color according to the given parameter.
     *
     * @param color Color whose alpha to be adjusted.
     * @param factor Factor in float by which adjust the alpha.
     *
     * @return Color with adjusted alpha.
     */
    public static @ColorInt int adjustAlpha(@ColorInt int color, float factor) {
        int alpha = Math.min(255, (int) (Color.alpha(color) * factor));
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        return Color.argb(alpha, red, green, blue);
    }

    /**
     * Remove alpha from a color.
     *
     * @param color Color whose alpha to be removed.
     *
     * @return Color without alpha.
     */
    public static @ColorInt int removeAlpha(@ColorInt int color) {
        return Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Get hexadecimal string from the color integer.
     *
     * @param color Color to get the hex code.
     * @param includeAlpha {@code true} to include alpha in the string.
     * @param includeHash {@code true} to include # in the string.
     *
     * @return Hexadecimal string equivalent of the supplied color integer.
     */
    public static String getColorString(@ColorInt int color, boolean includeAlpha,
                                        boolean includeHash) {
        String colorString;
        if (includeAlpha) {
            colorString = String.format("%08X", color);
        } else {
            colorString = String.format("%06X", 0xFFFFFF & color);
        }

        if (includeHash) {
            colorString = "#" + colorString;
        }

        return colorString;
    }
}
