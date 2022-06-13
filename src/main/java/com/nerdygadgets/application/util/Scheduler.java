package com.nerdygadgets.application.util;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class Scheduler {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(100); // Create new thread pool

    /**
     * Schedules a {@link Runnable} at the given interval after the provided delay.
     *
     * @param runnable The {@code Runnable} to schedule.
     * @param delay The initial delay in seconds.
     * @param interval The intervals in between executions, in seconds.
     */
    public static ScheduledFuture<?> scheduleAtFixedRate(@NotNull final Runnable runnable, final long delay, final long interval) {
        return scheduler.scheduleAtFixedRate(runnable, delay, interval, TimeUnit.SECONDS);
    }
}
