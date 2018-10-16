package com.x.y.utils;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskUtils {
    private static Timer timer = new Timer(true);

    private TimerTaskUtils() {
    }

    public static void run(TimerTask timerTask, long delay, long period) {
        timer.schedule(timerTask, delay, period);
    }

    public static void run(TimerTask timerTask, long delay) {
        timer.schedule(timerTask, delay);
    }
}