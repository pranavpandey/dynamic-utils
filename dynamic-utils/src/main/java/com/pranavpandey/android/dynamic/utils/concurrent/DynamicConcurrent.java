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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Executor class to manage or submit the asynchronous works.
 */
public class DynamicConcurrent {

    /**
     * An {@link Executor} that executes tasks one at a time in serial order.
     * This serialization is global to a particular process.
     */
    public static final Executor SERIAL_EXECUTOR;

    /**
     * An {@link Executor} that can be used to execute tasks in parallel.
     */
    public static final Executor THREAD_POOL_EXECUTOR;

    static {
        SERIAL_EXECUTOR = Executors.newSingleThreadExecutor();
        THREAD_POOL_EXECUTOR = getDefaultExecutor();
    }

    /**
     * Singleton instance of {@link DynamicConcurrent}.
     */
    private static DynamicConcurrent sInstance;

    /**
     * Lock object to provide the thread safety.
     */
    private static final Object sLock = new Object();

    /**
     * Constructor to initialize an object of this class.
     */
    private DynamicConcurrent() { }

    /**
     * Retrieves the singleton instance of {@link DynamicConcurrent}.
     * <p>Must be called before accessing the public methods.
     *
     * @return The singleton instance of {@link DynamicConcurrent}
     */
    public static @NonNull DynamicConcurrent getInstance() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new DynamicConcurrent();
            }
        }

        return sInstance;
    }

    /**
     * Returns the default executor service.
     *
     * @return The default executor service.
     */
    public static @NonNull ExecutorService getDefaultExecutor() {
        return new ThreadPoolExecutor(DynamicExecutor.NUMBER_OF_CORES,
                DynamicExecutor.NUMBER_OF_CORES, DynamicExecutor.KEEP_ALIVE,
                DynamicExecutor.KEEP_ALIVE_UNIT, DynamicExecutor.WORK_QUEUE);
    }

    /**
     * Executes a {@link Runnable} for the supplied executor service.
     * <p>Otherwise, use the default executor service.
     *
     * @param executorService The executor service to be used.
     * @param runnable The task to be executed.
     */
    public void execute(@Nullable ExecutorService executorService, @Nullable Runnable runnable) {
        if (runnable == null) {
            return;
        }

        if (executorService != null) {
            if (runnable instanceof DynamicTask) {
                ((DynamicTask<?, ?, ?>) runnable).executeOnExecutor(executorService);
            } else {
                executorService.execute(runnable);
            }
        } else {
            if (runnable instanceof DynamicTask) {
                ((DynamicTask<?, ?, ?>) runnable).executeOnExecutor(THREAD_POOL_EXECUTOR);
            } else {
                THREAD_POOL_EXECUTOR.execute(runnable);
            }
        }
    }

    /**
     * Executes a {@link Runnable} for the default executor service.
     *
     * @param runnable The task to be executed.
     */
    public void execute(@Nullable Runnable runnable) {
        execute(null, runnable);
    }
}
