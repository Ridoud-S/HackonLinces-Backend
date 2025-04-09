package com.FlightCrew.repository;

import com.FlightCrew.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findBySyncStatus(String status);
    List<Evaluation> findByFlightNumberAndEvaluatorId(String flightNumber, String evaluatorId);
}
