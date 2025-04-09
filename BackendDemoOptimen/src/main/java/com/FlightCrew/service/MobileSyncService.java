package com.FlightCrew.service;

import com.FlightCrew.dto.SyncRequestDTO;
import com.FlightCrew.dto.SyncStatusDTO;
import com.FlightCrew.exception.SyncException;
import com.FlightCrew.model.Evaluation;
import com.FlightCrew.repository.EvaluationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MobileSyncService {

    private static final Logger logger = LoggerFactory.getLogger(MobileSyncService.class);

    private final EvaluationRepository evaluationRepository;
    private final SyncService syncService;

    private final Map<String, SyncStatusDTO> deviceStatusMap = new HashMap<>();

    public MobileSyncService(EvaluationRepository evaluationRepository, SyncService syncService) {
        this.evaluationRepository = evaluationRepository;
        this.syncService = syncService;
    }

    public String processMobileSync(SyncRequestDTO request) {
        if (request.getDeviceId() == null || request.getDeviceId().isEmpty()) {
            throw new SyncException("El ID del dispositivo es requerido");
        }

        if (request.getEvaluations() == null || request.getEvaluations().isEmpty()) {
            return "No hay evaluaciones para sincronizar.";
        }

        // Filtrar solo las evaluaciones nuevas (que no estén ya en la base de datos)
        List<Evaluation> newEvaluations = request.getEvaluations().stream()
                .filter(eval -> evaluationRepository.findByFlightNumberAndEvaluatorId(
                        eval.getFlightNumber(), eval.getEvaluatorId()).isEmpty())
                .collect(Collectors.toList());

        if (newEvaluations.isEmpty()) {
            return "Todas las evaluaciones ya están sincronizadas.";
        }

        // Procesar solo las nuevas evaluaciones
        SyncRequestDTO newRequest = new SyncRequestDTO();
        newRequest.setDeviceId(request.getDeviceId());
        newRequest.setEvaluations(newEvaluations);

        String result = syncService.processSync(newRequest);
        updateDeviceStatus(request.getDeviceId());

        return result;
    }

    @Cacheable(value = "syncStatus", key = "#deviceId")
    public SyncStatusDTO getDeviceStatus(String deviceId) {
        SyncStatusDTO status = deviceStatusMap.getOrDefault(deviceId, new SyncStatusDTO());
        status.setDeviceId(deviceId);

        long pendingCount = evaluationRepository.findBySyncStatus("pending").stream()
                .filter(e -> deviceId.equals(e.getDeviceId()))
                .count();

        long syncedCount = evaluationRepository.findBySyncStatus("synced").stream()
                .filter(e -> deviceId.equals(e.getDeviceId()))
                .count();

        long conflictCount = evaluationRepository.findBySyncStatus("conflict").stream()
                .filter(e -> deviceId.equals(e.getDeviceId()))
                .count();

        status.setPendingCount(pendingCount);
        status.setSyncedCount(syncedCount);
        status.setConflictCount(conflictCount);
        status.setLastUpdated(LocalDateTime.now());

        deviceStatusMap.put(deviceId, status);
        return status;
    }

    private void updateDeviceStatus(String deviceId) {
        SyncStatusDTO status = new SyncStatusDTO();
        status.setDeviceId(deviceId);
        status.setLastUpdated(LocalDateTime.now());

        deviceStatusMap.put(deviceId, status);
    }

    @Scheduled(fixedRate = 3600000)
    public void cleanupDeviceStatus() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        List<String> devicesToRemove = deviceStatusMap.entrySet().stream()
                .filter(entry -> entry.getValue().getLastUpdated().isBefore(oneHourAgo))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        devicesToRemove.forEach(deviceStatusMap::remove);
        logger.info("Limpieza de estado de dispositivos: {} dispositivos eliminados", devicesToRemove.size());
    }
}
