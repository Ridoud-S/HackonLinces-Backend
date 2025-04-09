package com.FlightCrew.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class SyncMetrics {

    private final Counter syncAttemptsCounter;
    private final Counter syncSuccessCounter;
    private final Counter syncFailureCounter;
    private final Counter syncConflictCounter;
    private final Timer syncDurationTimer;

    public SyncMetrics(MeterRegistry registry) {
        this.syncAttemptsCounter = Counter.builder("flightcrew.sync.attempts")
                .description("Total number of sync attempts")
                .register(registry);

        this.syncSuccessCounter = Counter.builder("flightcrew.sync.success")
                .description("Number of successful syncs")
                .register(registry);

        this.syncFailureCounter = Counter.builder("flightcrew.sync.failure")
                .description("Number of failed syncs")
                .register(registry);

        this.syncConflictCounter = Counter.builder("flightcrew.sync.conflicts")
                .description("Number of sync conflicts")
                .register(registry);

        this.syncDurationTimer = Timer.builder("flightcrew.sync.duration")
                .description("Sync operation duration")
                .register(registry);
    }

    public void incrementSyncAttempts() {
        syncAttemptsCounter.increment();
    }

    public void incrementSyncSuccess() {
        syncSuccessCounter.increment();
    }

    public void incrementSyncFailure() {
        syncFailureCounter.increment();
    }

    public void incrementSyncConflict() {
        syncConflictCounter.increment();
    }

    public <T> T recordSyncDuration(Supplier<T> operation) {
        return syncDurationTimer.record(operation);
    }

    public void recordSyncDuration(Runnable operation) {
        syncDurationTimer.record(operation);
    }

    public Timer.Sample startTimer() {
        return Timer.start();
    }

    public long stopTimer(Timer.Sample sample) {
        return sample.stop(syncDurationTimer);
    }
}