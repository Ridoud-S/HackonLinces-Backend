package com.FlightCrew.controller;

import com.FlightCrew.dto.SyncRequestDTO;
import com.FlightCrew.dto.SyncStatusDTO;
import com.FlightCrew.exception.SyncException;
import com.FlightCrew.service.MobileSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sync")
public class SyncController {

    private static final Logger logger = LoggerFactory.getLogger(SyncController.class);
    private final MobileSyncService mobileSyncService;

    public SyncController(MobileSyncService mobileSyncService) {
        this.mobileSyncService = mobileSyncService;
    }

    @PostMapping
    public ResponseEntity<?> processSync(@Valid @RequestBody SyncRequestDTO request) {
        try {
            logger.info("Recibida solicitud de sincronización desde dispositivo: {}", request.getDeviceId());

            String result = mobileSyncService.processMobileSync(request);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", result);

            return ResponseEntity.ok(response);
        } catch (SyncException e) {
            logger.error("Error de sincronización: {}", e.getMessage());

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            logger.error("Error no controlado durante sincronización", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error interno del servidor");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/status/{deviceId}")
    public ResponseEntity<?> getSyncStatus(@PathVariable String deviceId) {
        try {
            logger.info("Consultando estado de sincronización para dispositivo: {}", deviceId);

            // Usar el método correcto para obtener el estado de sincronización por dispositivo
            SyncStatusDTO status = mobileSyncService.getDeviceStatus(deviceId);

            return ResponseEntity.ok(status);
        } catch (Exception e) {
            logger.error("Error al consultar estado de sincronización", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error al obtener estado de sincronización");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/manual-sync")
    public ResponseEntity<?> triggerManualSync() {
        try {
            logger.info("Iniciando sincronización manual");

            // No existe syncData(), pero podríamos implementarlo o usar una alternativa
            // Por ejemplo, sincronizar todas las evaluaciones pendientes
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Funcionalidad de sincronización manual aún no implementada");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error durante sincronización manual", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error durante sincronización manual");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}