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

package com.pranavpandey.android.dynamic.util.concurrent;

import androidx.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The executor interface to provide concurrent functionality.
 */
public interface DynamicExecutor {

    /**
     * Gets the number of available cores.
     * (not always the same as the maximum number of cores)
     */
    int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    /**
     * Core pool size for the executor.
     */
    int CORE_POOL_SIZE = 1;

    /**
     * Maximum pool size for the executor.
     */
    int MAXIMUM_POOL_SIZE = 20;

    /**
     * Backup pool size for the executor.
     */
    int BACKUP_POOL_SIZE = 10;

    /**
     * Instantiates the queue of Runnables as a {@link SynchronousQueue}.
     */
    BlockingQueue<Runnable> WORK_QUEUE = new SynchronousQueue<>();

    /**
     * Instantiates the queue of Runnables as a {@link LinkedBlockingDeque}.
     */
    BlockingQueue<Runnable> BACKUP_WORK_QUEUE = new LinkedBlockingDeque<>();

    /**
     * The amount of time an idle thread waits before terminating.
     */
    long KEEP_ALIVE = 3000;

    /**
     * The keep alive time unit.
     */
    TimeUnit KEEP_ALIVE_UNIT = TimeUnit.MILLISECONDS;

    /**
     * Thread factory for the backup pool executor.
     */
    ThreadFactory THREAD_FACTORY = new ThreadFactory() {

        /**
         * Count for the task.
         */
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "DynamicTask #" + mCount.getAndIncrement());
        }
    };

    /**
     * Returns the default executor service.
     *
     * @return The default executor service.
     */
    @NonNull ExecutorService getDefaultExecutor();
}
