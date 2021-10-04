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
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ImageDecoder;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;

/**
 * Helper class to perform {@link Bitmap} operations.
 */
public class DynamicBitmapUtils {

    /**
     * Default size to decode the bitmap.
     */
    public static final int SIZE_DEFAULT = 1;

    /**
     * Default sample size to decode the bitmap.
     */
    public static final int SAMPLE_DEFAULT = 0;

    /**
     * Default sample size to decode the logo bitmap.
     */
    public static final int SAMPLE_LOGO = 4;

    /**
     * Retrieve the bitmap from the supplied URI.
     *
     * @param context The context to get the content resolver.
     * @param uri The URI to retrieve the bitmap.
     * @param options The bitmap factory options to be used.
     *
     * @return The bitmap from the supplied URI.
     *
     * @see BitmapFactory#decodeFileDescriptor(FileDescriptor, Rect, BitmapFactory.Options)
     * @see ImageDecoder#decodeBitmap(ImageDecoder.Source)
     * @see MediaStore.Images.Media#getBitmap(ContentResolver, Uri)
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.P)
    public static @Nullable Bitmap getBitmap(@Nullable Context context,
            @Nullable Uri uri, @Nullable BitmapFactory.Options options) {
        if (context == null || uri == null) {
            return null;
        }

        Bitmap bitmap = null;

        try {
            if (options != null || DynamicSdkUtils.is21()) {
                AssetFileDescriptor asset = context.getContentResolver()
                        .openAssetFileDescriptor(uri, "r");

                if (asset != null) {
                    bitmap = BitmapFactory.decodeFileDescriptor(asset.getParcelFileDescriptor()
                                    .getFileDescriptor(), null, options);
                    asset.close();
                }
            } else {
                if (DynamicSdkUtils.is28()) {
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(
                            context.getContentResolver(), uri));
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * Retrieve the bitmap from the supplied URI.
     *
     * @param context The context to get the content resolver.
     * @param uri The URI to retrieve the bitmap.
     *
     * @return The bitmap from the supplied URI.
     *
     * @see #getBitmap(Context, Uri, BitmapFactory.Options)
     */
    public static @Nullable Bitmap getBitmap(@Nullable Context context, @Nullable Uri uri) {
        return getBitmap(context, uri, null);
    }

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
    public static @Nullable Bitmap getBitmap(@Nullable Drawable drawable,
            int width, int height, boolean compress, int quality) {
        if (drawable == null) {
            return null;
        }

        if (width <= 0) {
            width = SIZE_DEFAULT;
        }

        if (height <= 0) {
            height = SIZE_DEFAULT;
        }

        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        float ratio = (float) width / height;
        float ratioDrawable = (float) drawableWidth / drawableHeight;

        drawableWidth = width;
        drawableHeight = height;
        if (ratio > ratioDrawable) {
            drawableWidth = (int) (width * ratioDrawable);
        } else if (ratio < ratioDrawable) {
            drawableHeight = (int) (height / ratioDrawable);
        }

        Bitmap bitmap = null;
        final int left = (width - drawableWidth) / 2;
        final int top = (height - drawableHeight) / 2;

        try {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(left, top, drawableWidth + left, drawableHeight + top);
            drawable.draw(canvas);

            if (compress) {
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, quality, byteArray);

                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }

                return BitmapFactory.decodeByteArray(byteArray.toByteArray(),
                        0, byteArray.size());
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        return bitmap;
    }

    /**
     * Get bitmap from the supplied drawable.
     *
     * @param drawable The drawable to get the bitmap.
     * @param width The width in dip for the bitmap.
     * @param height The height in dip for the bitmap.
     *
     * @return The bitmap from the supplied drawable.
     *
     * @see #getBitmap(Drawable, int, int, boolean, int)
     */
    public static @Nullable Bitmap getBitmap(@Nullable Drawable drawable, int width, int height) {
        return getBitmap(drawable, width, height, false, SAMPLE_DEFAULT);
    }

    /**
     * Get bitmap from the supplied drawable.
     *
     * @param drawable The drawable to get the bitmap.
     * @param compress {@code true} to compress the bitmap.
     * @param quality The quality of the compressed bitmap.
     *
     * @return The bitmap from the supplied drawable.
     *
     * @see #getBitmap(Drawable, int, int, boolean, int)
     */
    public static @Nullable Bitmap getBitmap(@Nullable Drawable drawable,
            boolean compress, int quality) {
        if (drawable != null) {
            return getBitmap(drawable, drawable.getIntrinsicWidth(),
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
     *
     * @see #getBitmap(Drawable, int, int)
     */
    public static @Nullable Bitmap getBitmap(@Nullable Drawable drawable) {
        if (drawable != null) {
            return getBitmap(drawable, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
        }

        return null;
    }

    /**
     * Retrieve the compressed bitmap from the supplied URI.
     *
     * @param context The context to get the content resolver.
     * @param uri The URI to retrieve the bitmap.
     *
     * @return The compressed bitmap from the supplied URI.
     *
     * @see #SAMPLE_LOGO
     * @see #getBitmap(Context, Uri, BitmapFactory.Options)
     */
    public static @Nullable Bitmap getBitmapLogo(@Nullable Context context, @Nullable Uri uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = SAMPLE_LOGO;

        return getBitmap(context, uri, options);
    }

    /**
     * Resize bitmap to the new width and height.
     *
     * @param bitmap The bitmap to resize.
     * @param newWidth The new width for the bitmap.
     * @param newHeight The new height for the bitmap.
     * @param maxWidth The maximum width for the bitmap.
     * @param maxHeight The maximum height for the bitmap.
     * @param recycle {@code true} to recycle the original bitmap.
     *
     * @return The resized bitmap with new width and height.
     */
    public static @Nullable Bitmap resizeBitmap(@Nullable Bitmap bitmap,
            int newWidth, int newHeight, int maxWidth, int maxHeight, boolean recycle) {
        if (bitmap == null) {
            return null;
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float ratioBitmap = (float) width / height;
        float ratioNew = (float) newWidth / newHeight;
        float ratioMax = (float) maxWidth / maxHeight;

        Bitmap resized;
        newWidth = maxWidth;
        newHeight = maxHeight;
        if (ratioMax > ratioNew) {
            newWidth = (int) (maxWidth * ratioBitmap);
        } else {
            newHeight = (int) (maxHeight / ratioBitmap);
        }

        try {
            resized = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        } catch (Exception e) {
            e.getStackTrace();

            resized = !bitmap.isRecycled() ? bitmap.copy(
                    Bitmap.Config.ARGB_8888, true) : null;
        } finally {
            if (recycle && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }

        return resized;
    }

    /**
     * Resize bitmap to the new width and height.
     *
     * @param bitmap The bitmap to resize.
     * @param newWidth The new width for the bitmap.
     * @param newHeight The new height for the bitmap.
     * @param maxWidth The maximum width for the bitmap.
     * @param maxHeight The maximum height for the bitmap.
     *
     * @return The resized bitmap with new width and height.
     *
     * @see #resizeBitmap(Bitmap, int, int, int, int, boolean)
     */
    public static @Nullable Bitmap resizeBitmap(@Nullable Bitmap bitmap,
            int newWidth, int newHeight, int maxWidth, int maxHeight) {
        return resizeBitmap(bitmap, newWidth, newHeight, maxWidth, maxHeight, true);
    }

    /**
     * Resize bitmap to the new width and height.
     *
     * @param bitmap The bitmap to resize.
     * @param newWidth The new width for the bitmap.
     * @param newHeight The new height for the bitmap.
     *
     * @return The resized bitmap with new width and height.
     *
     * @see #resizeBitmap(Bitmap, int, int, int, int)
     */
    public static @Nullable Bitmap resizeBitmap(@Nullable Bitmap bitmap,
            int newWidth, int newHeight) {
        return resizeBitmap(bitmap, newWidth, newHeight, newWidth, newHeight);
    }

    /**
     * Resize bitmap according to the max width and height.
     *
     * @param bitmap The bitmap to resize.
     * @param maxWidth The maximum width for the bitmap.
     * @param maxHeight The maximum height for the bitmap.
     * @param recycle {@code true} to recycle the original bitmap.
     *
     * @return The resized bitmap according to the max width and height.
     *
     * @see #resizeBitmap(Bitmap, int, int, int, int)
     */
    public static @Nullable Bitmap resizeBitmapMax(@Nullable Bitmap bitmap,
            int maxWidth, int maxHeight, boolean recycle) {
        if (bitmap == null) {
            return null;
        }

        return resizeBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(),
                maxWidth, maxHeight, recycle);
    }

    /**
     * Resize bitmap according to the max width and height.
     *
     * @param bitmap The bitmap to resize.
     * @param maxWidth The maximum width for the bitmap.
     * @param maxHeight The maximum height for the bitmap.
     *
     * @return The resized bitmap according to the max width and height.
     *
     * @see #resizeBitmap(Bitmap, int, int, int, int)
     */
    public static @Nullable Bitmap resizeBitmapMax(@Nullable Bitmap bitmap,
            int maxWidth, int maxHeight) {
        if (bitmap == null) {
            return null;
        }

        return resizeBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), maxWidth, maxHeight);
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

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
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
    public static @Nullable Bitmap applyColorFilter(@Nullable Bitmap bitmap,
            @NonNull ColorFilter colorFilter) {
        if (bitmap == null) {
            return null;
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setFilterBitmap(true);
        paint.setColorFilter(colorFilter);

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
    public static @Nullable Bitmap applyColorFilter(@Nullable Bitmap bitmap, @ColorInt int color) {
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
        Bitmap newBitmap = resizeBitmap(bitmap, 1, 1,
                1, 1, false);
        @ColorInt int color = Color.BLACK;

        if (newBitmap != null) {
            color = newBitmap.getPixel(0, 0);
            newBitmap.recycle();
        }

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
    public static @Nullable Bitmap createBitmap(@Nullable View view, int width, int height) {
        if (view == null) {
            return null;
        }

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
    public static @Nullable Bitmap createBitmap(@Nullable View view) {
        return createBitmap(view, 0, 0);
    }
}
