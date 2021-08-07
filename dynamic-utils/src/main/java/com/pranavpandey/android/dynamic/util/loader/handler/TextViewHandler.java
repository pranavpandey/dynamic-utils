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

package com.pranavpandey.android.dynamic.util.loader.handler;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pranavpandey.android.dynamic.util.concurrent.DynamicHandler;

import java.lang.ref.WeakReference;

/**
 * A {@link Handler} to set the {@link TextView} on separate thread.
 *
 * @see ImageView#setImageDrawable(Drawable)
 * @see ImageView#setImageBitmap(Bitmap)
 */
public class TextViewHandler extends Handler {

    /**
     * text view used by this handler.
     */
    private final WeakReference<TextView> mTextView;

    /**
     * Constructor to initialize an object of this class.
     *
     * @param textView The text view to be used.
     */
    public TextViewHandler(@Nullable TextView textView) {
        super(Looper.getMainLooper());

        this.mTextView = new WeakReference<>(textView);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);

        if (getTextView() == null) {
            return;
        }

        if (msg.obj instanceof StringBuilder) {
            getTextView().setText((StringBuilder) msg.obj, TextView.BufferType.SPANNABLE);
        } else if (msg.obj instanceof CharSequence) {
            getTextView().setText((CharSequence) msg.obj);
        } else {
            getTextView().setText(msg.what == DynamicHandler.MESSAGE_NULL_IF_NOT
                    ? null : getTextView().getText());
        }
    }

    /**
     * Returns the text view used by this handler.
     *
     * @return The text view used by this handler.
     */
    public @Nullable TextView getTextView() {
        if (mTextView == null) {
            return null;
        }

        return mTextView.get();
    }
}
