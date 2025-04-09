package com.FlightCrew.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SyncStatusDTO {
    private String deviceId;
    private long pendingCount;
    private long syncedCount;
    private long conflictCount;
    private LocalDateTime lastUpdated;

    // Calculado automáticamente
    public long getTotalCount() {
        return pendingCount + syncedCount + conflictCount;
    }

    public boolean hasPendingSync() {
        return pendingCount > 0;
    }

    public boolean hasConflicts() {
        return conflictCount > 0;
    }
}