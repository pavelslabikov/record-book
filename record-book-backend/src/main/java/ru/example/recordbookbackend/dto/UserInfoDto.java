package ru.example.recordbookbackend.dto;

import lombok.*;
import ru.example.recordbookbackend.entity.SystemRoleType;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.UserInfo}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserInfoDto implements Serializable {
    private UUID id;
    private String login;
    private String name;
    private String surname;
    private String patronymic;
}