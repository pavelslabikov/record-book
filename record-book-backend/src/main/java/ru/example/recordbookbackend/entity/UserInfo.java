package ru.example.recordbookbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_info")
@ToString
@RequiredArgsConstructor
public class UserInfo {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "login", nullable = false, length = Integer.MAX_VALUE)
    private String login;

    @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
    private String password;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "surname", nullable = false, length = Integer.MAX_VALUE)
    private String surname;

    @Column(name = "patronymic", nullable = false, length = Integer.MAX_VALUE)
    private String patronymic;

    @Enumerated(EnumType.STRING)
    @Column(name = "system_role", nullable = false, length = Integer.MAX_VALUE)
    private SystemRoleType systemRole;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

}