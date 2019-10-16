package dev.jko;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class StillAliveInstance {

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private List<String> lines;

    private long interval;
    private long startDelay;
    private int currentLine;

    private ScheduledFuture scheduled;

    private Consumer<String> executionCallback;


    private static final Logger ANNOUNCE_LOG = LoggerFactory.getLogger("StillAlive");
    public static final Logger LOG = LoggerFactory.getLogger(StillAliveInstance.class);

    StillAliveInstance() {
        Properties props = System.getProperties();
        interval = Long.parseLong(props.getProperty("dev.jko.alive.interval", Long.toString(10 * 60 * 60 * 1000)));
        startDelay = Long.parseLong(props.getProperty("dev.jko.alive.delay", Long.toString(interval)));
    }

    void startup() {
        readLines("/still-alive.txt");
        schedule();
        executionCallback = ANNOUNCE_LOG::info;
    }

    void readLines(String path) {
        try (InputStream textFile = StillAliveInstance.class.getResourceAsStream(path)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(textFile));
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            LOG.error("Unable to read resource {}", path);
        }
    }

    void schedule() {
        cancel();
        scheduled = executor.scheduleWithFixedDelay(this::announceStillAlive, startDelay, interval, TimeUnit.MILLISECONDS);
    }

    private void cancel() {
        if(scheduled != null && !scheduled.isCancelled() && !scheduled.isDone()) {
            scheduled.cancel(true);
            scheduled = null;
        }
    }

    void announceStillAlive() {
        if(currentLine == lines.size()) {
            currentLine = 0;
        }
        executionCallback.accept(lines.get(currentLine++));
    }

    long getInterval() {
        return interval;
    }

    void setInterval(long interval) {
        cancel();
        this.interval = interval;
        schedule();
    }


    long getStartDelay() {
        return startDelay;
    }

    void setStartDelay(long startDelay) {
        cancel();
        this.startDelay = startDelay;
        schedule();
    }

    void setExecutionCallback(Consumer<String> executionCallback) {
        this.executionCallback = executionCallback;
    }

    void setLines(List<String> lines) {
        this.lines = lines;
    }
}
