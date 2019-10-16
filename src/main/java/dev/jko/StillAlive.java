package dev.jko;

import java.util.List;
import java.util.function.Consumer;

public class StillAlive {

    private static StillAliveInstance instance;

    static {
        instance = new StillAliveInstance();
    }

    public static void start() {
        instance.startup();
    }

    public static long getInterval() {
        return instance.getInterval();
    }

    public static void setInterval(long interval) {
        instance.setInterval(interval);
    }

    public static long getStartDelay() {
        return instance.getStartDelay();
    }

    public static void setStartDelay(long startDelay) {
        instance.setStartDelay(startDelay);
    }

    public static void setExecutionCallback(Consumer<String> executionCallback) {
        instance.setExecutionCallback(executionCallback);
    }

    public static void setLines(List<String> lines) {
        instance.setLines(lines);
    }

}
