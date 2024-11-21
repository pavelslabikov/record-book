package ru.example.recordbookbackend.dto.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import ru.example.recordbookbackend.entity.SheetType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.Sheet}
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NormalizedRecordBookDto implements Serializable {
    private UUID studentIdCard;
    private String surname;
    private String name;
    private String patronymic;

    private Integer courseNumber;
    private String recordBookId;

    @JsonUnwrapped
    private Research research;
    @JsonUnwrapped
    private Diploma diploma;

    private List<String> midtermAssessmentResults;
    private List<String> facultyResults;
    private List<String> courseWorks;
    private List<String> practices;
    private List<String> stateExams;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Research {
        private String researchType;
        private String grade;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        private LocalDate examDate;
        private String teacher;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Diploma {
        private String form;
        private String topic;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        private LocalDate defenseDate;
        private String grade;
        private String protocolNumber;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        private LocalDate protocolDate;

    }
}