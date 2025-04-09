package com.FlightCrew.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling // Para tareas programadas (ej. sincronización periódica)
public class SyncConfig {

    @Value("${azure.sql.sync.interval:300000}") // 5 minutos por defecto
    private long syncInterval;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${sync.max-retries:3}")
    private int maxRetries;

    // Configuración para el cliente HTTP (comunicación con Flutter u otros servicios)
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Executor para tareas asíncronas (ej. procesamiento en segundo plano)
    @Bean
    public Executor syncTaskExecutor() {
        return Executors.newFixedThreadPool(4); // 4 hilos para sincronización
    }

    // Bean para manejar reintentos de sincronización
    @Bean
    public SyncRetryPolicy syncRetryPolicy() {
        return new SyncRetryPolicy(maxRetries, 5000); // 5 segundos entre reintentos
    }
}

// Clase auxiliar para política de reintentos
class SyncRetryPolicy {
    private final int maxAttempts;
    private final long backoffInterval;

    public SyncRetryPolicy(int maxAttempts, long backoffInterval) {
        this.maxAttempts = maxAttempts;
        this.backoffInterval = backoffInterval;
    }

    // Getters y lógica de reintentos...
}