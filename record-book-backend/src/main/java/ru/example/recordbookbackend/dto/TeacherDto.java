package ru.example.recordbookbackend.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.Teacher}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeacherDto implements Serializable {
    private Integer id;
    private UUID userId;
    private String academicRank;
    private String jobTitle;
}