package com.FlightCrew.dto;

import com.FlightCrew.model.Evaluation;

import java.time.LocalDateTime;
import java.util.List;

public class SyncResponseDTO {
    private boolean success;
    private String syncToken;
    private LocalDateTime serverTime;
    private List<Evaluation> evaluations;
    private List<Evaluation> conflicts;
    private long pendingCount;
    private long conflictCount;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSyncToken() {
        return syncToken;
    }

    public void setSyncToken(String syncToken) {
        this.syncToken = syncToken;
    }

    public LocalDateTime getServerTime() {
        return serverTime;
    }

    public void setServerTime(LocalDateTime serverTime) {
        this.serverTime = serverTime;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public List<Evaluation> getConflicts() {
        return conflicts;
    }

    public void setConflicts(List<Evaluation> conflicts) {
        this.conflicts = conflicts;
    }

    public long getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(long pendingCount) {
        this.pendingCount = pendingCount;
    }

    public long getConflictCount() {
        return conflictCount;
    }

    public void setConflictCount(long conflictCount) {
        this.conflictCount = conflictCount;
    }
}