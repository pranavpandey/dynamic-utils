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

package com.pranavpandey.android.dynamic.utils.cache;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

/**
 * A {@link DynamicLruCache} for the {@link Drawable}.
 */
public final class DrawableLruCache<T> extends DynamicLruCache<T, Drawable> {

    /**
     * Constructor to initialize an object of this class.
     */
    public DrawableLruCache() {
        super();
    }

    @Override
    protected int sizeOf(@NonNull T key, @NonNull Drawable value) {
        if (value instanceof BitmapDrawable) {
            return ((BitmapDrawable) value).getBitmap().getByteCount() / getByteMultiplier();
        } else {
            Bitmap bitmap = Bitmap.createBitmap(value.getIntrinsicWidth(),
                    value.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            value.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            value.draw(canvas);

            return bitmap.getByteCount() / getByteMultiplier();
        }
    }
}
