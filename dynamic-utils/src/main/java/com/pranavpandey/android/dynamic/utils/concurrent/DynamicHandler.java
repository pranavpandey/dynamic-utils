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

package com.pranavpandey.android.dynamic.utils.concurrent;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * A {@link Handler} to handle updates from the {@link DynamicTask}.
 *
 * @see DynamicRunnable
 * @see DynamicRunnable#finish(DynamicResult)
 * @see DynamicRunnable#onProgressUpdate(DynamicResult)
 */
public class DynamicHandler<P, R> extends Handler {

    /**
     * Message constant to post the final result on the main thread.
     */
    public static final int MESSAGE_POST_RESULT = 0x1;

    /**
     * Message constant to publish the progress on the main thread.
     */
    public static final int MESSAGE_POST_PROGRESS = 0x2;

    /**
     * Message constant to set the content to {@code null} if not matched the criteria.
     */
    public static final int MESSAGE_NULL_IF_NOT = 0x3;

    /**
     * Runnable to receive the callbacks.
     */
    private final WeakReference<DynamicRunnable<?, P, R>> mRunnable;

    /**
     * Constructor to initialize an object of this class.
     *
     * @param looper The looper to be used.
     * @param runnable The runnable to receive the callbacks.
     *
     * @see DynamicRunnable#finish(DynamicResult)
     * @see DynamicRunnable#onProgressUpdate(DynamicResult)
     */
    public DynamicHandler(@NonNull Looper looper,
            @NonNull DynamicRunnable<?, P, R> runnable) {
        super(looper);

        this.mRunnable = new WeakReference<DynamicRunnable<?, P, R>>(runnable);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);

        if (getRunnable() == null) {
            return;
        }

        switch (msg.what) {
            case MESSAGE_POST_RESULT:
                getRunnable().finish((DynamicResult<R>) msg.obj);
                break;
            case MESSAGE_POST_PROGRESS:
                getRunnable().onProgressUpdate((DynamicResult<P>) msg.obj);
                break;
        }
    }

    /**
     * Returns the runnable handled by this handler.
     *
     * @return The runnable handled by this handler.
     */
    public @Nullable DynamicRunnable<?, P, R> getRunnable() {
        if (mRunnable == null) {
            return null;
        }

        return mRunnable.get();
    }
}
