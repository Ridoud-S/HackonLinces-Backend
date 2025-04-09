package com.FlightCrew.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "evaluations")
public class Evaluation {
    @Id
    private String id;

    private String evaluatorName;
    private String evaluatorId;
    private String evaluatedName;
    private String evaluatedId;
    private String flightNumber;
    private String destination;
    private LocalDateTime evaluationDate;
    private int score;

    @ElementCollection
    @CollectionTable(name = "evaluation_areas", joinColumns = @JoinColumn(name = "evaluation_id"))
    @Column(name = "area")
    private List<String> evaluatedAreas;

    private String status;
    private String syncStatus = "pending"; // "pending", "synced", "conflict"
    private LocalDateTime lastModified = LocalDateTime.now();
    private String deviceId;
    private int version = 1;

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvaluatorName() {
        return evaluatorName;
    }

    public void setEvaluatorName(String evaluatorName) {
        this.evaluatorName = evaluatorName;
    }

    public String getEvaluatorId() {
        return evaluatorId;
    }

    public void setEvaluatorId(String evaluatorId) {
        this.evaluatorId = evaluatorId;
    }

    public String getEvaluatedName() {
        return evaluatedName;
    }

    public void setEvaluatedName(String evaluatedName) {
        this.evaluatedName = evaluatedName;
    }

    public String getEvaluatedId() {
        return evaluatedId;
    }

    public void setEvaluatedId(String evaluatedId) {
        this.evaluatedId = evaluatedId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDateTime evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getEvaluatedAreas() {
        return evaluatedAreas;
    }

    public void setEvaluatedAreas(List<String> evaluatedAreas) {
        this.evaluatedAreas = evaluatedAreas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}