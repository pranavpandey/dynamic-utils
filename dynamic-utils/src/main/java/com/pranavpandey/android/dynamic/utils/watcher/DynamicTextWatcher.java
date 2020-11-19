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

package com.pranavpandey.android.dynamic.utils.watcher;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;

/**
 * A {@link TextWatcher} to delay the text changed event.
 *
 * @see TextWatcher#afterTextChanged(Editable)
 */
public abstract class DynamicTextWatcher implements TextWatcher {

    /**
     * Message constant to dispatch the text change event.
     */
    public static final int MESSAGE_TEXT_CHANGED = 0x1;

    /**
     * Default value in milliseconds by which the text changed event should be delayed.
     */
    public static final long DELAY_TEXT_CHANGE = 220L;

    /**
     * Handler used by this watcher to delay the text changed event.
     */
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == MESSAGE_TEXT_CHANGED) {
                delayedTextChanged((Editable) msg.obj);
            }
        }
    };

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        getHandler().removeMessages(MESSAGE_TEXT_CHANGED);
    }

    @Override
    public void afterTextChanged(Editable s) {
        getHandler().sendMessageDelayed(getHandler().obtainMessage(
                MESSAGE_TEXT_CHANGED, s), getTextChangeDelay());
    }

    /**
     * This method will be called after delaying the text changed event.
     *
     * @param s The text after the change.
     *
     * @see #afterTextChanged(Editable)
     */
    public abstract void delayedTextChanged(Editable s);

    /**
     * Returns the delay in milliseconds by which the text changed event should be delayed.
     *
     * @return The delay in milliseconds by which the text changed event should be delayed.
     */
    public long getTextChangeDelay() {
        return DELAY_TEXT_CHANGE;
    }

    /**
     * Returns the handler used by this watcher to delay the text changed event.
     *
     * @return The handler used by this watcher to delay the text changed event.
     */
    public @NonNull Handler getHandler() {
        return mHandler;
    }
}
