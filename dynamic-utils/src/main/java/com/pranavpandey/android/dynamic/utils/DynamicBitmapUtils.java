/*
 * Copyright 2017 Pranav Pandey
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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Helper class to perform {@link Bitmap} operations.
 */
public class DynamicBitmapUtils {

    /**
     * Get bitmap from the supplied drawable.
     *
     * @param drawable Drawable to get the bitmap.
     *
     * @return The bitmap from the supplied drawable.
     */
    public @Nullable static Bitmap getBitmapFormDrawable(@NonNull Drawable drawable) {
        try {
            Bitmap bitmap;

            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        } catch (Exception ignored) {
        }

        return null;
    }

    /**
     * Resize bitmap to the new width and height.
     *
     * @param bitmap Bitmap to resize.
     * @param newWidth New width for the bitmap.
     * @param newHeight New height for the bitmap.
     *
     * @return Resized bitmap with new width and height.
     */
    public static @NonNull Bitmap resizeBitmap(@NonNull Bitmap bitmap,
                                               int newWidth, int newHeight) {
        Bitmap resizedBitmap = Bitmap.createBitmap(
                newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(resizedBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return resizedBitmap;
    }

    /**
     * Apply color filter on the supplied bitmap.
     *
     * @param bitmap Bitmap to apply color filter.
     * @param colorFilter Color filter to be applied on the bitmap.
     *
     * @return New bitmap with applied color filter.
     */
    public static @NonNull Bitmap applyColorFilter(@NonNull Bitmap bitmap,
                                                   @NonNull ColorFilter colorFilter) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(colorFilter);
        Canvas canvas1 = new Canvas(bitmap);
        canvas1.drawBitmap(bitmap, 0, 0, paint);

        return bitmap;
    }

    /**
     * Apply monochrome color filter on the supplied bitmap.
     *
     * @param bitmap Bitmap to apply color filter.
     * @param color Color to generate color filter.
     *
     * @return New bitmap with applied color filter.
     */
    public static @NonNull Bitmap applyColorFilter(@NonNull Bitmap bitmap,
                                                   @ColorInt int color) {
        return applyColorFilter(bitmap,
                new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
    }

    /**
     * Extract the dominant color form the supplied bitmap.
     *
     * @param bitmap Bitmap to extract the dominant color.
     *
     * @return Dominant color extracted from the bitmap.
     */
    public static @ColorInt int getDominantColor(@NonNull Bitmap bitmap) {
        Bitmap newBitmap = resizeBitmap(bitmap, 1, 1);
        final @ColorInt int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();

        return color;
    }
}
