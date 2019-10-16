package dev.jko;

import org.junit.Assert;
import org.junit.Test;

public class StillAliveTest {

    private boolean called = false;

    @Test
    public void checkConsumerCalled() throws Exception {
        StillAlive.setExecutionCallback(this::logLine);
        Thread.sleep(1000);
        Assert.assertTrue(called);
    }

    private void logLine(String line) {
        called = true;
    }
}
