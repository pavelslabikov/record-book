package ru.example.recordbookbackend.dto.controller;

import lombok.*;
import ru.example.recordbookbackend.dto.UserInfoDto;

import java.io.Serializable;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeacherWithUserInfoDto implements Serializable {
    private Integer id;
    private String academicRank;
    private String jobTitle;
    private UserInfoDto userInfoDto;
}