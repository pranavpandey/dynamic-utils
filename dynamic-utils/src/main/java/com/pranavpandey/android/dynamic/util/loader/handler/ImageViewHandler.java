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

package com.pranavpandey.android.dynamic.util.loader.handler;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pranavpandey.android.dynamic.util.concurrent.DynamicHandler;

import java.lang.ref.WeakReference;

/**
 * A {@link Handler} to set the {@link ImageView} on separate thread.
 *
 * @see ImageView#setImageDrawable(Drawable)
 * @see ImageView#setImageBitmap(Bitmap)
 */
public class ImageViewHandler extends Handler {

    /**
     * Image view used by this handler.
     */
    private final WeakReference<ImageView> mImageView;

    /**
     * Constructor to initialize an object of this class.
     *
     * @param imageView The image view to be used.
     */
    public ImageViewHandler(@Nullable ImageView imageView) {
        super(Looper.getMainLooper());

        this.mImageView = new WeakReference<>(imageView);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);

        if (getImageView() == null) {
            return;
        }

        if (msg.obj instanceof Drawable) {
            getImageView().setImageDrawable((Drawable) msg.obj);
        } else if (msg.obj instanceof Bitmap) {
            getImageView().setImageBitmap((Bitmap) msg.obj);
        } else {
            getImageView().setImageDrawable(msg.what == DynamicHandler.MESSAGE_NULL_IF_NOT
                    ? null : getImageView().getDrawable());
        }
    }

    /**
     * Returns the image view used by this handler.
     *
     * @return The image view used by this handler.
     */
    public @Nullable ImageView getImageView() {
        if (mImageView == null) {
            return null;
        }

        return mImageView.get();
    }
}
