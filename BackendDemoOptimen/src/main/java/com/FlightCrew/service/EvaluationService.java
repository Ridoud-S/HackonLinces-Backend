package com.FlightCrew.service;

import com.FlightCrew.dto.EvaluationDTO;
import com.FlightCrew.model.Evaluation;
import com.FlightCrew.repository.EvaluationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class EvaluationService {

    private final EvaluationRepository mongoRepo;

    public EvaluationService(EvaluationRepository mongoRepo) {
        this.mongoRepo = mongoRepo;
    }

    public Evaluation saveOfflineEvaluation(EvaluationDTO dto) {
        Evaluation eval = convertToMongoEntity(dto);
        return mongoRepo.save(eval);
    }

    private Evaluation convertToMongoEntity(EvaluationDTO dto) {
        Evaluation entity = new Evaluation();
        entity.setFlightNumber(dto.getFlightNumber());
        entity.setEvaluatorId(dto.getEvaluatorId());
        entity.setEvaluationDate(LocalDateTime.now());
        // ... mapear respuestas
        return entity;
    }
}