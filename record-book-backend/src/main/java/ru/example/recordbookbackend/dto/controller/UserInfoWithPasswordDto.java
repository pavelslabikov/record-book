package ru.example.recordbookbackend.dto.controller;

import lombok.*;
import ru.example.recordbookbackend.dto.UserInfoDto;

import java.io.Serializable;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.UserInfo}
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoWithPasswordDto implements Serializable {

    private String password;

    private UserInfoDto userInfoDto;

}