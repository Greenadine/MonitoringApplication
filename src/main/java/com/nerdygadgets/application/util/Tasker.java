package com.nerdygadgets.application.util;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public final class Tasker {

    private final static Timer timer = new Timer();

    /**
     * Schedules a {@link TimerTask} for repeated execution with the provided interval.
     *
     * @param task The {@code TimerTask}.
     * @param delay The delay before executing the task a first time.
     * @param interval The interval in seconds.
     */
    public static void scheduleTask(@NotNull final TimerTask task, long delay, long interval) {
        timer.schedule(task, delay, interval);
    }
}
