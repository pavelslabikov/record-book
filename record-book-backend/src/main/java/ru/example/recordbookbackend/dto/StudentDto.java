package ru.example.recordbookbackend.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.Student}
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto implements Serializable {
    private UUID id;
    private UUID userId;
    private Integer courseNumber;
    private String groupName;
    private String faculty;
    private String gradeBookNumber;
    private String specializationCode;
}