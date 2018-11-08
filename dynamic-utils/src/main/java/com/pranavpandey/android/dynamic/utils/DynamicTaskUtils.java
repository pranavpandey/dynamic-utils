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

import android.os.AsyncTask;

import androidx.annotation.Nullable;

/**
 * Helper class to easily execute or cancel an {@link AsyncTask} by handling all the exceptions.
 */
public class DynamicTaskUtils {

    /**
     * Try to execute the supplied async task.
     *
     * @param asyncTask The async task to be executed.
     *
     * @see AsyncTask#execute(Object[])
     */
    public static void executeTask(@Nullable AsyncTask asyncTask) {
        try {
            if (asyncTask != null &&
                    asyncTask.getStatus() != AsyncTask.Status.RUNNING) {
                asyncTask.executeOnExecutor(
                        AsyncTask.THREAD_POOL_EXECUTOR, (Object[]) null);
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * Try to cancel the supplied async task.
     *
     * @param asyncTask The async task to be cancelled.
     *
     * @see AsyncTask#cancel(boolean)
     */
    public static void cancelTask(@Nullable AsyncTask asyncTask) {
        try {
            if (asyncTask != null &&
                    asyncTask.getStatus() == AsyncTask.Status.RUNNING) {
                asyncTask.cancel(true);
            }
        } catch (Exception ignored) {
        }
    }
}
