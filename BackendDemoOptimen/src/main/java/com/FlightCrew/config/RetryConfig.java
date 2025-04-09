package com.FlightCrew.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RetryConfig {

    @Value("${sync.max-retries:3}")
    private int maxRetries;

    @Value("${sync.initial-backoff:1000}")
    private long initialBackoff;

    @Value("${sync.max-backoff:10000}")
    private long maxBackoff;

    @Value("${sync.backoff-multiplier:2.0}")
    private double backoffMultiplier;

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // Configurar la política de reintentos
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(
                maxRetries,
                Collections.singletonMap(Exception.class, true)
        );

        // Configurar el backoff exponencial
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(initialBackoff);
        backOffPolicy.setMaxInterval(maxBackoff);
        backOffPolicy.setMultiplier(backoffMultiplier);

        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }

    // Bean para registrar listeners de errores específicos
    @Bean
    public Map<Class<? extends Throwable>, Boolean> retryableExceptions() {
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();

        // Excepciones transitorias que merecen reintento
        retryableExceptions.put(java.net.ConnectException.class, true);
        retryableExceptions.put(java.net.SocketTimeoutException.class, true);
        retryableExceptions.put(jakarta.persistence.PessimisticLockException.class, true);
        retryableExceptions.put(org.springframework.dao.DataAccessResourceFailureException.class, true);

        // Excepciones que no merecen reintento
        retryableExceptions.put(java.lang.IllegalArgumentException.class, false);
        retryableExceptions.put(com.FlightCrew.exception.SyncException.class, false);

        return retryableExceptions;
    }
}