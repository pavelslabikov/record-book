package ru.example.recordbookbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.example.recordbookbackend.entity.SheetType;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.Sheet}
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SheetDto implements Serializable {
    private Long sheetId;
    private String subjectName;
    private String academicYear;
    private Integer creditUnits;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate examDate;
    private String diplomaForm;
    private String topic;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate diplomaDefenseDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate protocolDate;
    private String protocolNumber;
    private SheetType type;
    private String researchType;
    private String practicePlace;
    private Integer teacherId;
}