package ru.example.recordbookbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "certificate_info")
public class CertificateInfo {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = Integer.MAX_VALUE)
    private CertificateType type;

    @Column(name = "not_before")
    private LocalDate notBefore;

    @Column(name = "not_after")
    private LocalDate notAfter;

    @NotNull
    @Column(name = "content", nullable = false)
    private byte[] content;

    @NotNull
    @Column(name = "subject_info", nullable = false, length = Integer.MAX_VALUE)
    private String subjectInfo;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

}