package ru.example.recordbookbackend.dto.controller;

import lombok.*;
import ru.example.recordbookbackend.dto.UserInfoDto;

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
public class StudentWithUserInfoDto implements Serializable {
    private UUID id;
    private Integer courseNumber;
    private String groupName;
    private String faculty;
    private String gradeBookNumber;
    private String specializationCode;

    private UserInfoDto userInfoDto;
}