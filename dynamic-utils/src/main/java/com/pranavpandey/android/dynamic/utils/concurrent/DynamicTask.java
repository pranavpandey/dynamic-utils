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

import android.os.Binder;
import android.os.Looper;
import android.os.Process;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Base class to represent {@link Runnable} according to the {@link DynamicRunnable}.
 *
 * @param <T> The type of the params.
 * @param <P> The type of the progress.
 * @param <R> The type of the result.
 */
public abstract class DynamicTask<T, P, R> extends DynamicRunnable<T, P, R> {

    /**
     * Thread handler to publish results.
     */
    private final DynamicHandler<P, R> mHandler;

    /**
     * Callable to implement the worker.
     */
    private final DynamicCallable<T, DynamicResult<R>> mWorker;

    /**
     * Future task to execute the operation.
     */
    private final FutureTask<DynamicResult<R>> mFuture;

    /**
     * Status to represent the various states of this callback.
     */
    private volatile DynamicStatus mStatus = DynamicStatus.PENDING;

    /**
     * Boolean to represent if task was cancelled before it completed.
     */
    private final AtomicBoolean mCancelled = new AtomicBoolean();

    /**
     * Boolean to represent if the task was invoked before.
     */
    private final AtomicBoolean mTaskInvoked = new AtomicBoolean();

    /**
     * Constructor to initialize an object of this class.
     */
    public DynamicTask() {
        this(Looper.getMainLooper());
    }

    /**
     * Constructor to initialize an object of this class.
     *
     * @param looper The looper to be used.
     */
    public DynamicTask(@NonNull Looper looper) {
        this.mHandler = new DynamicHandler<>(looper, this);

        mWorker = new DynamicCallable<T, DynamicResult<R>>() {
            @Override
            public DynamicResult<R> call() {
                mTaskInvoked.set(true);
                DynamicResult<R> result = null;

                try {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    result = new DynamicResult.Success<>(doInBackground(getParams()));
                    Binder.flushPendingCommands();
                } catch (Exception e) {
                    mCancelled.set(true);
                    result = new DynamicResult.Error<>(e);
                } finally {
                    postResult(result);
                }

                return result;
            }
        };

        mFuture = new FutureTask<DynamicResult<R>>(mWorker) {
            @Override
            protected void done() {
                try {
                    postResultIfNotInvoked(get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    throw new RuntimeException(
                            "An error occurred while executing doInBackground()", e.getCause());
                } catch (CancellationException e) {
                    postResultIfNotInvoked(null);
                }
            }
        };
    }

    /**
     * Notify results on the main thread if teh task was not started before.
     *
     * @param result The result to be notified.
     */
    private void postResultIfNotInvoked(final @Nullable DynamicResult<R> result) {
        if (!mTaskInvoked.get()) {
            postResult(result);
        }
    }

    /**
     * Notify results on the main thread.
     *
     * @param result The result to be notified.
     *
     * @return The notified result.
     */
    private @Nullable DynamicResult<R> postResult(final @Nullable DynamicResult<R> result) {
        getHandler().obtainMessage(DynamicHandler.MESSAGE_POST_RESULT, result).sendToTarget();
        return result;
    }

    /**
     * Returns the thread handler used by this task.
     *
     * @return The thread handler used by this task.
     */
    public @NonNull DynamicHandler<P, R> getHandler() {
        return mHandler;
    }

    /**
     * Returns the unboxed {@code boolean} value from the {@link Boolean} result.
     *
     * @param result The {@link Boolean} result to be unboxed.
     *
     * @return The unboxed {@code boolean} value from the {@link Boolean} result.
     */
    public boolean getBooleanResult(final @Nullable DynamicResult<R> result) {
        Boolean success = null;

        if (result instanceof DynamicResult.Success && result.getData() instanceof Boolean) {
            success = (Boolean) result.getData();
        }

        return success != null;
    }

    /**
     * Returns the current status of this task.
     *
     * @return The current status of this task.
     */
    public final @NonNull DynamicStatus getStatus() {
        return mStatus;
    }

    /**
     * Waits if necessary for the computation to complete, and then
     * retrieves its result.
     *
     * @return The computed result.
     *
     * @throws CancellationException If the computation was cancelled.
     * @throws ExecutionException If the computation threw an exception.
     * @throws InterruptedException If the current thread was interrupted
     *         while waiting.
     */
    public final @Nullable DynamicResult<R> get() throws InterruptedException, ExecutionException {
        return mFuture.get();
    }
    /**
     * Waits if necessary for at most the given time for the computation
     * to complete, and then retrieves its result.
     *
     * @param timeout Time to wait before cancelling the operation.
     * @param unit The time unit for the timeout.
     *
     * @return The computed result.
     *
     * @throws CancellationException If the computation was cancelled.
     * @throws ExecutionException If the computation threw an exception.
     * @throws InterruptedException If the current thread was interrupted
     *         while waiting.
     * @throws TimeoutException If the wait timed out.
     */
    public final @Nullable DynamicResult<R> get(long timeout, TimeUnit unit) throws
            InterruptedException, ExecutionException, TimeoutException {
        return mFuture.get(timeout, unit);
    }

    @Override
    public void run() {
        execute();
    }

    @Override
    public @Nullable DynamicResult<P> publishProgress(final @Nullable DynamicResult<P> progress) {
        getHandler().obtainMessage(DynamicHandler.MESSAGE_POST_PROGRESS, progress).sendToTarget();
        return progress;
    }

    @Override
    public void finish(@Nullable DynamicResult<R> result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }

        mStatus = DynamicStatus.FINISHED;
    }

    @Override
    public final boolean isCancelled() {
        return mCancelled.get();
    }

    @Override
    public final boolean cancel(boolean mayInterruptIfRunning) {
        if (isCancelled()) {
            return false;
        }

        mCancelled.set(true);
        return mFuture.cancel(mayInterruptIfRunning);
    }

    /**
     * Executes the task on the supplied executor.
     *
     * @param executor The executor to execute the task.
     * @param params The optional parameters for the task.
     *
     * @return The instance of the executed task.
     */
    @MainThread
    public final @NonNull DynamicTask<T, P, R> executeOnExecutor(
            @NonNull Executor executor, @Nullable T params) {
        if (mStatus != DynamicStatus.PENDING) {
            switch (mStatus) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task:"
                            + " the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task:"
                            + " the task has already been executed "
                            + "(a task can be executed only once)");
            }
        }

        mStatus = DynamicStatus.RUNNING;
        onPreExecute();

        mWorker.setParams(params);
        executor.execute(mFuture);

        return this;
    }

    /**
     * Executes the task on the supplied executor.
     *
     * @param executor The executor to execute the task.
     *
     * @return The instance of the executed task.
     */
    @MainThread
    public final @NonNull DynamicTask<T, P, R> executeOnExecutor(@NonNull Executor executor) {
        return executeOnExecutor(executor, null);
    }

    /**
     * Executes the task with the supplied parameters.
     *
     * @param params The parameters for the task.
     *
     * @return The instance of the executed task.
     *
     * @see #executeOnExecutor(Executor, Object)
     * @see DynamicConcurrent#getThreadPoolExecutor()
     */
    @MainThread
    public final @NonNull DynamicTask<T, P, R> execute(@Nullable T params) {
        return executeOnExecutor(DynamicConcurrent.getInstance().getThreadPoolExecutor(), params);
    }

    /**
     * Executes the task with the default executor and {@code null} parameters.
     *
     * @return The instance of the executed task.
     *
     * @see #execute(Object)
     * @see DynamicConcurrent#getThreadPoolExecutor()
     */
    @MainThread
    public final @NonNull DynamicTask<T, P, R> execute() {
        return execute(null);
    }
}
