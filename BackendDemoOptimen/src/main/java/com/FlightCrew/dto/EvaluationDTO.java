package com.FlightCrew.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EvaluationDTO {
    private String id;
    private String flightNumber;
    private String evaluatorId;
    private String evaluatorName;
    private String evaluatedId;
    private String evaluatedName;
    private String destination;
    private LocalDateTime evaluationDate;
    private int score;
    private List<String> evaluatedAreas;
    private String status;
    private List<QuestionResponseDTO> responses;

    @Data
    public static class QuestionResponseDTO {
        private String questionId;
        private String answer;
        private String comment;
    }
}