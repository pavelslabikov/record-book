package ru.example.recordbookbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.Grade}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeDto implements Serializable {
    private Long gradeId;
    private String value;
    private UUID student_id;
}