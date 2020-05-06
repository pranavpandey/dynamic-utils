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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

/**
 * Helper class to perform {@link Bitmap} operations.
 */
public class DynamicBitmapUtils {

    /**
     * Get bitmap from the supplied drawable.
     *
     * @param drawable The drawable to get the bitmap.
     * @param width The width in dip for the bitmap.
     * @param height The height in dip for the bitmap.
     * @param compress {@code true} to compress the bitmap.
     * @param quality The quality of the compressed bitmap.
     *
     * @return The bitmap from the supplied drawable.
     */
    public static @Nullable Bitmap getBitmapFromDrawable(@Nullable Drawable drawable,
            int width, int height, boolean compress, int quality) {
        if (drawable != null) {
            try {
                if (width < 0 || height < 0) {
                    width = 1;
                    height = 1;
                }

                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);

                if (compress) {
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, quality, byteArray);
                    return BitmapFactory.decodeByteArray(byteArray.toByteArray(),
                            0, byteArray.size());
                } else {
                    return bitmap;
                }
            } catch (Exception ignored) {
            }
        }

        return null;
    }

    /**
     * Get bitmap from the supplied drawable.
     *
     * @param drawable The drawable to get the bitmap.
     * @param compress {@code true} to compress the bitmap.
     * @param quality The quality of the compressed bitmap.
     *
     * @return The bitmap from the supplied drawable.
     */
    public static @Nullable Bitmap getBitmapFromDrawable(
            @Nullable Drawable drawable, boolean compress, int quality) {
        if (drawable != null) {
            return getBitmapFromDrawable(drawable, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), compress, quality);
        }

        return null;
    }

    /**
     * Get bitmap from the supplied drawable.
     *
     * @param drawable The drawable to get the bitmap.
     *
     * @return The bitmap from the supplied drawable.
     */
    public static @Nullable Bitmap getBitmapFromDrawable(@Nullable Drawable drawable) {
        if (drawable != null) {
            return getBitmapFromDrawable(drawable, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), false, 0);
        }

        return null;
    }

    /**
     * Resize bitmap to the new width and height.
     *
     * @param bitmap The bitmap to resize.
     * @param newWidth The new width for the bitmap.
     * @param newHeight The new height for the bitmap.
     *
     * @return The resized bitmap with new width and height.
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

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFilterBitmap(true);

        Canvas canvas = new Canvas(resizedBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return resizedBitmap;
    }

    /**
     * Crop bitmap to the new width and height.
     *
     * @param bitmap The bitmap to crop.
     * @param newWidth The new width for the bitmap.
     * @param newHeight The new height for the bitmap.
     *
     * @return The cropped bitmap with new width and height.
     */
    public static @Nullable Bitmap cropBitmap(@Nullable Bitmap bitmap,
            int newWidth, int newHeight) {
        if (bitmap == null) {
            return null;
        }

        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, 0, 0, newWidth, newHeight);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFilterBitmap(true);

        Canvas canvas = new Canvas(croppedBitmap);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return croppedBitmap;
    }

    /**
     * Apply color filter on the supplied bitmap.
     *
     * @param bitmap The bitmap to apply color filter.
     * @param colorFilter The color filter to be applied on the bitmap.
     *
     * @return The new bitmap with applied color filter.
     */
    public static @NonNull Bitmap applyColorFilter(@NonNull Bitmap bitmap,
            @NonNull ColorFilter colorFilter) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(colorFilter);
        paint.setFilterBitmap(true);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return bitmap;
    }

    /**
     * Apply monochrome color filter on the supplied bitmap.
     *
     * @param bitmap The bitmap to apply color filter.
     * @param color The color to generate color filter.
     *
     * @return The new bitmap with applied color filter.
     */
    public static @NonNull Bitmap applyColorFilter(@NonNull Bitmap bitmap, @ColorInt int color) {
        return applyColorFilter(bitmap, new PorterDuffColorFilter(
                color, PorterDuff.Mode.SRC_ATOP));
    }

    /**
     * Extract the dominant color from the supplied bitmap.
     *
     * @param bitmap The bitmap to extract the dominant color.
     *
     * @return The dominant color extracted from the bitmap.
     */
    public static @ColorInt int getDominantColor(@NonNull Bitmap bitmap) {
        Bitmap newBitmap = resizeBitmap(bitmap, 1, 1);
        final @ColorInt int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();

        return color;
    }

    /**
     * Creates a bitmap from the supplied view.
     *
     * @param view The view to get the bitmap.
     * @param width The width for the bitmap.
     * @param height The height for the bitmap.
     *
     * @return The bitmap from the supplied drawable.
     */
    public static @NonNull Bitmap createBitmapFromView(@NonNull View view, int width, int height) {
        final int oldWidth = view.getWidth();
        final int oldHeight = view.getHeight();

        if (width > 0 && height > 0) {
            view.measure(View.MeasureSpec.makeMeasureSpec(DynamicUnitUtils
                            .convertDpToPixels(width), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(DynamicUnitUtils
                            .convertDpToPixels(height), View.MeasureSpec.EXACTLY));
        }
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable background = view.getBackground();

        if (background != null) {
            background.draw(canvas);
        }

        view.draw(canvas);
        view.measure(View.MeasureSpec.makeMeasureSpec(oldWidth, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(oldHeight, View.MeasureSpec.EXACTLY));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        return bitmap;
    }

    /**
     * Creates a bitmap from the supplied view.
     *
     * @param view The view to get the bitmap.
     *
     * @return The bitmap from the supplied drawable.
     */
    public static @NonNull Bitmap createBitmapFromView(@NonNull View view) {
        return createBitmapFromView(view, 0, 0);
    }
}
