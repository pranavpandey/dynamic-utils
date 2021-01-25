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
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Executor class to manage or submit the asynchronous works.
 */
public class DynamicConcurrent {

    /**
     * An {@link ExecutorService} that executes tasks one at a time in serial order.
     * This serialization is global to a particular process.
     */
    public final ExecutorService SERIAL_EXECUTOR;

    /**
     * An {@link ExecutorService} that can be used to execute tasks in parallel.
     */
    public final ExecutorService THREAD_POOL_EXECUTOR;

    /**
     * An {@link ExecutorService} to be used as backup for the {@link #THREAD_POOL_EXECUTOR}.
     */
    private static ThreadPoolExecutor sBackupExecutor;

    /**
     * An {@link RejectedExecutionHandler} to be used for the {@link #THREAD_POOL_EXECUTOR}.
     */
    private static final RejectedExecutionHandler sRunOnSerialPolicy =
            new RejectedExecutionHandler() {
                public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                    // As a last ditch fallback, run it on an executor with an unbounded queue.
                    // Create this executor lazily, hopefully almost never.
                    synchronized (this) {
                        if (sBackupExecutor == null) {
                            sBackupExecutor = new ThreadPoolExecutor(
                                    DynamicExecutor.BACKUP_POOL_SIZE,
                                    DynamicExecutor.BACKUP_POOL_SIZE,
                                    DynamicExecutor.KEEP_ALIVE, DynamicExecutor.KEEP_ALIVE_UNIT,
                                    DynamicExecutor.BACKUP_WORK_QUEUE,
                                    DynamicExecutor.THREAD_FACTORY);
                            sBackupExecutor.allowCoreThreadTimeOut(true);
                        }
                    }

                    sBackupExecutor.execute(r);
                }
            };

    /**
     * Singleton instance of {@link DynamicConcurrent}.
     */
    private static DynamicConcurrent sInstance;

    /**
     * Lock object to provide the thread safety.
     */
    private static final Object sLock = new Object();

    /**
     * Making default constructor private so that it cannot be initialized directly.
     * <p>Use {@link #getInstance()} instead.
     */
    private DynamicConcurrent() {
        SERIAL_EXECUTOR = Executors.newSingleThreadExecutor();
        THREAD_POOL_EXECUTOR = getDefaultExecutor();
        ((ThreadPoolExecutor) THREAD_POOL_EXECUTOR)
                .setRejectedExecutionHandler(sRunOnSerialPolicy);
    }

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
        return new ThreadPoolExecutor(DynamicExecutor.CORE_POOL_SIZE,
                DynamicExecutor.MAXIMUM_POOL_SIZE, DynamicExecutor.KEEP_ALIVE,
                DynamicExecutor.KEEP_ALIVE_UNIT, DynamicExecutor.WORK_QUEUE);
    }

    /**
     * Returns the serial executor service.
     *
     * @return The serial executor service.
     */
    public @NonNull ExecutorService getSerialExecutor() {
        return SERIAL_EXECUTOR;
    }

    /**
     * Returns the thread pool executor service.
     *
     * @return The thread pool executor service.
     */
    public @NonNull ExecutorService getThreadPoolExecutor() {
        return THREAD_POOL_EXECUTOR;
    }

    /**
     * Executes a {@link Runnable} for the supplied executor service.
     * <p>Otherwise, use the default executor service.
     *
     * @param executorService The executor service to be used.
     * @param runnable The task to be executed.
     *
     * @see ExecutorService#execute(Runnable)
     */
    public void execute(@Nullable ExecutorService executorService, @Nullable Runnable runnable) {
        if (executorService == null || runnable == null) {
            return;
        }

        if (runnable instanceof DynamicTask) {
            ((DynamicTask<?, ?, ?>) runnable).executeOnExecutor(executorService);
        } else {
            executorService.execute(runnable);
        }
    }

    /**
     * Executes a {@link Runnable} on the default executor service.
     *
     * @param runnable The task to be executed.
     *
     * @see #THREAD_POOL_EXECUTOR
     * @see ExecutorService#execute(Runnable)
     */
    public void execute(@Nullable Runnable runnable) {
        execute(getThreadPoolExecutor(), runnable);
    }

    /**
     * Submits a {@link Runnable} or {@link Callable} task on the supplied executor service
     * and returns a {@link Future} representing that task.
     *
     * @param executorService The executor service to be used.
     * @param task The task to be submitted.
     * @param result The result to return.
     * @param <T> The type of the task.
     * @param <V> The type of the result.
     *
     * @return The {@link Future} representing the pending completion of the task.
     *
     * @see ExecutorService#submit(Runnable)
     * @see ExecutorService#submit(Callable)
     */
    public @Nullable <T, V> Future<?> submit(@Nullable ExecutorService executorService,
            @Nullable T task, @Nullable V result) {
        if (executorService != null && task != null) {
            if (task instanceof Runnable) {
                if (result != null) {
                    return executorService.submit((Runnable) task, result);
                } else {
                    return executorService.submit((Runnable) task);
                }
            } else if (task instanceof Callable<?>) {
                return executorService.submit((Callable<?>) task);
            }
        }

        return null;
    }

    /**
     * Submits a {@link Runnable} or {@link Callable} task on the default executor service
     * and returns a {@link Future} representing that task.
     *
     * @param task The task to be submitted.
     * @param result The result to return.
     * @param <T> The type of the task.
     * @param <V> The type of the result.
     *
     * @return The {@link Future} representing the pending completion of the task.
     *
     * @see #THREAD_POOL_EXECUTOR
     * @see ExecutorService#submit(Runnable)
     * @see ExecutorService#submit(Callable)
     */
    public @Nullable <T, V> Future<?> submit(@Nullable T task, @Nullable V result) {
        return submit(getThreadPoolExecutor(), task, result);
    }

    /**
     * Submits a {@link Runnable} or {@link Callable} task on the default executor service
     * and returns a {@link Future} representing that task.
     *
     * @param task The task to be submitted.
     * @param <T> The type of the task.
     *
     * @return The {@link Future} representing the pending completion of the task.
     *
     * @see #THREAD_POOL_EXECUTOR
     * @see ExecutorService#submit(Runnable)
     * @see ExecutorService#submit(Callable)
     */
    public @Nullable <T> Future<?> submit(@Nullable T task) {
        return submit(getThreadPoolExecutor(), task, null);
    }

    /**
     * Submits a {@link Runnable} task on the supplied executor service and perform
     * operations with the handler and callback.
     *
     * @param executorService The executor service to be used.
     * @param handler The handler to be used.
     * @param callback The callback to be used.
     * @param <V> The type of the callback view.
     * @param <P> The type of the callback placeholder.
     * @param <R> The type of the callback result.
     *
     * @return The {@link Future} representing the pending completion of the task.
     */
    public @Nullable <V, P, R> Future<?> async(@Nullable ExecutorService executorService,
            final @Nullable Handler handler, final @Nullable DynamicCallback<V, P, R> callback) {
        if (handler == null || callback == null || callback.getView() == null) {
            return null;
        }

        final Message message = Message.obtain();
        message.obj = callback.onPlaceholder(callback.getView());
        handler.sendMessage(message);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.obj = callback.onResult(callback.getView());
                handler.sendMessage(message);
            }
        };

        if (executorService != null) {
            return executorService.submit(runnable);
        } else {
            new Handler().post(runnable);
            return null;
        }
    }

    /**
     * Submits a {@link Runnable} task on the default executor service and perform
     * operations with the handler and callback.
     *
     * @param handler The handler to be used.
     * @param callback The callback to be used.
     * @param <V> The type of the callback view.
     * @param <P> The type of the callback placeholder.
     * @param <R> The type of the callback result.
     *
     * @return The {@link Future} representing the pending completion of the task.
     *
     * @see #THREAD_POOL_EXECUTOR
     */
    public @Nullable <V, P, R> Future<?> async(@Nullable Handler handler,
            @Nullable DynamicCallback<V, P, R> callback) {
        return async(null, handler, callback);
    }
}
