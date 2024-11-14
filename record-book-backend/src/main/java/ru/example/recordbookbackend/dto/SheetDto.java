package ru.example.recordbookbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.recordbookbackend.entity.SheetType;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.Sheet}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SheetDto implements Serializable {
    private Long sheetId;
    private String subjectName;
    private String academicYear;
    private Integer creditUnits;
    private LocalDate examDate;
    private String diplomaForm;
    private String topic;
    private LocalDate diplomaDefenseDate;
    private LocalDate protocolDate;
    private String protocolNumber;
    private SheetType type;
    private String researchType;
    private String practicePlace;
    private Integer teacherId;
}