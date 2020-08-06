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

package com.pranavpandey.android.dynamic.utils.concurrent;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * A {@link Handler} to handle updates from the {@link DynamicTask}.
 *
 * @see DynamicRunnable
 * @see DynamicRunnable#finish(DynamicResult)
 * @see DynamicRunnable#onProgressUpdate(DynamicResult)
 */
public class DynamicHandler<Progress, Result> extends Handler {

    /**
     * Message constant to post the final result on the main thread.
     */
    public static final int MESSAGE_POST_RESULT = 0x1;

    /**
     * Message constant to publish the progress on the main thread.
     */
    public static final int MESSAGE_POST_PROGRESS = 0x2;

    /**
     * Runnable to receive the callbacks.
     */
    private final DynamicRunnable<?, Progress, Result> mRunnable;

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
            @NonNull DynamicRunnable<?, Progress, Result> runnable) {
        super(looper);

        this.mRunnable = runnable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleMessage(@NonNull Message msg) {
        if (mRunnable == null) {
            return;
        }

        switch (msg.what) {
            case MESSAGE_POST_RESULT:
                mRunnable.finish((DynamicResult<Result>) msg.obj);
                break;
            case MESSAGE_POST_PROGRESS:
                mRunnable.onProgressUpdate((DynamicResult<Progress>) msg.obj);
                break;
        }
    }

    /**
     * Returns the runnable handled by this handler.
     *
     * @return The runnable handled by this handler.
     */
    public @NonNull DynamicRunnable<?, Progress, Result> getRunnable() {
        return mRunnable;
    }
}
