package com.FlightCrew.repository;

import com.FlightCrew.entity.EvaluationSQL;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EvaluationSQLRepository extends JpaRepository<EvaluationSQL, Long> {
    List<EvaluationSQL> findBySyncStatus(String status);
}
