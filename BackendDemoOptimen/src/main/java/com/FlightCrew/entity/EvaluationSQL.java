package com.FlightCrew.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "evaluations")
public class EvaluationSQL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String flightNumber;

    @Column(nullable = false, length = 50)
    private String evaluatorId;

    @Column(nullable = false)
    private LocalDateTime evaluationDate;

    @Column(length = 20)
    private String syncStatus = "pending";

    @Column(nullable = false)
    private LocalDateTime lastModified = LocalDateTime.now();
}
