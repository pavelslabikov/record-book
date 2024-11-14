package ru.example.recordbookbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "grade")
@ToString
@RequiredArgsConstructor
public class Grade {
    @EmbeddedId
    private VersionedId id;

    @Column(name = "value", length = Integer.MAX_VALUE)
    private String value;

    @Column(name = "student_id", nullable = false)
    private UUID student_id;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "sheet_id", nullable = false)
    private Long sheetId;

    @Column(name = "sheet_version", nullable = false)
    private Long sheetVersion;

}