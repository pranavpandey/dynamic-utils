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

package com.pranavpandey.android.dynamic.utils;

import android.os.AsyncTask;

import androidx.annotation.Nullable;

import com.pranavpandey.android.dynamic.utils.concurrent.DynamicConcurrent;
import com.pranavpandey.android.dynamic.utils.concurrent.DynamicStatus;
import com.pranavpandey.android.dynamic.utils.concurrent.DynamicTask;

import java.util.concurrent.Executor;

/**
 * Helper class to easily execute or cancel an {@link AsyncTask} by handling all the exceptions.
 */
public class DynamicTaskUtils {

    /**
     * Try to execute the supplied async task.
     *
     * @param task The async task to be executed.
     *
     * @see AsyncTask#executeOnExecutor(Executor, Object[])
     */
    public static void executeTask(@Nullable AsyncTask<Object, ?, ?> task) {
        if (task == null) {
            return;
        }

        try {
            if (task.getStatus() == AsyncTask.Status.PENDING) {
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[]) null);
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * Try to cancel the supplied async task.
     *
     * @param task The async task to be cancelled.
     *
     * @see AsyncTask#cancel(boolean)
     */
    public static void cancelTask(@Nullable AsyncTask<?, ?, ?> task) {
        if (task == null) {
            return;
        }

        try {
            task.cancel(true);
        } catch (Exception ignored) {
        }
    }

    /**
     * Try to execute the supplied dynamic task.
     *
     * @param task The dynamic task to be executed.
     *
     * @see DynamicTask#executeOnExecutor(Executor)
     */
    public static void executeTask(@Nullable DynamicTask<?, ?, ?> task) {
        if (task == null) {
            return;
        }

        try {
            if (task.getStatus() == DynamicStatus.PENDING) {
                task.executeOnExecutor(DynamicConcurrent.getInstance().getThreadPoolExecutor());
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * Try to cancel the supplied dynamic task.
     *
     * @param task The dynamic task to be cancelled.
     *
     * @param mayInterruptIfRunning {@code true} if the thread executing the
     *        task should be interrupted; otherwise, in-progress tasks are allowed
     *        to complete.
     *
     * @see DynamicTask#cancel(boolean)
     */
    public static void cancelTask(@Nullable DynamicTask<?, ?, ?> task,
            boolean mayInterruptIfRunning) {
        if (task == null) {
            return;
        }

        try {
            task.cancel(mayInterruptIfRunning);
        } catch (Exception ignored) {
        }
    }
}
