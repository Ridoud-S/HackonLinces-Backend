package com.FlightCrew.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "evaluations")
public class EvaluationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_number", nullable = false, length = 20)
    private String flightNumber;

    @Column(name = "evaluator_id", nullable = false, length = 50)
    private String evaluatorId;

    @Column(name = "evaluation_date", nullable = false)
    private LocalDateTime evaluationDate;


    @Column(name = "sync_status", length = 20)
    private String syncStatus;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime lastModified = LocalDateTime.now();

    // Constructor vacío necesario para JPA
    public EvaluationEntity() {}

    // Constructor para conversión manual (opcional)
    public EvaluationEntity(String flightNumber, String evaluatorId,
                            LocalDateTime evaluationDate, String syncStatus) {
        this.flightNumber = flightNumber;
        this.evaluatorId = evaluatorId;
        this.evaluationDate = evaluationDate;
        this.syncStatus = syncStatus;
    }
}