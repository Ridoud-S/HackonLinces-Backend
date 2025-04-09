package com.FlightCrew.controller;

import com.FlightCrew.repository.EvaluationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/sync/status")
public class SyncStatusController {
    private final EvaluationRepository evaluationRepo;

    public SyncStatusController(EvaluationRepository evaluationRepo) {
        this.evaluationRepo = evaluationRepo;
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<?> getSyncStatus(@PathVariable String deviceId) {
        long pendingCount = evaluationRepo.findBySyncStatus("pending").size();
        long conflictCount = evaluationRepo.findBySyncStatus("conflict").size();
        return ResponseEntity.ok("Pendientes: " + pendingCount + ", Conflictos: " + conflictCount);
    }
}