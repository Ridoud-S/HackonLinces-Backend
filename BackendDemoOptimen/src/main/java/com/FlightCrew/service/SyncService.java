package com.FlightCrew.service;

import com.FlightCrew.dto.SyncRequestDTO;
import com.FlightCrew.exception.SyncException;
import com.FlightCrew.model.Evaluation;
import com.FlightCrew.repository.EvaluationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SyncService {

    private static final Logger logger = LoggerFactory.getLogger(SyncService.class);
    private final EvaluationRepository evaluationRepository;

    public SyncService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @Transactional
    public String processSync(SyncRequestDTO request) {
        if (request.getEvaluations() == null || request.getEvaluations().isEmpty()) {
            return "No hay evaluaciones para sincronizar.";
        }

        int processedCount = 0;
        int errorCount = 0;

        for (Evaluation evaluation : request.getEvaluations()) {
            try {
                // Asignar ID si es null
                if (evaluation.getId() == null || evaluation.getId().isEmpty()) {
                    evaluation.setId(UUID.randomUUID().toString());
                }

                // Asignar deviceId desde el request
                evaluation.setDeviceId(request.getDeviceId());

                // Actualizar estado de sincronización y fecha de modificación
                evaluation.setSyncStatus("synced");
                evaluation.setLastModified(LocalDateTime.now());

                evaluationRepository.save(evaluation);
                processedCount++;
            } catch (Exception e) {
                logger.error("Error al procesar evaluación: {}", e.getMessage());
                errorCount++;
            }
        }

        return String.format("Procesadas %d evaluaciones. Errores: %d", processedCount, errorCount);
    }

    /**
     * Método para sincronización manual de datos pendientes
     */
    @Transactional
    public void syncData() {
        List<Evaluation> pendingEvaluations = evaluationRepository.findBySyncStatus("pending");

        logger.info("Iniciando sincronización manual de {} evaluaciones pendientes", pendingEvaluations.size());

        for (Evaluation evaluation : pendingEvaluations) {
            try {
                evaluation.setSyncStatus("synced");
                evaluation.setLastModified(LocalDateTime.now());
                evaluationRepository.save(evaluation);
            } catch (Exception e) {
                logger.error("Error al sincronizar evaluación {}: {}", evaluation.getId(), e.getMessage());
                evaluation.setSyncStatus("conflict");
                evaluationRepository.save(evaluation);
            }
        }

        logger.info("Sincronización manual completada");
    }
}