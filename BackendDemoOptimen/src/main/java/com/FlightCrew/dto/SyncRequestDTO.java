package com.FlightCrew.dto;

import com.FlightCrew.model.Evaluation;
import lombok.Data;

import java.util.List;

@Data
public class SyncRequestDTO {
    private String deviceId;
    private String lastSyncToken;
    private List<Evaluation> evaluations;
}