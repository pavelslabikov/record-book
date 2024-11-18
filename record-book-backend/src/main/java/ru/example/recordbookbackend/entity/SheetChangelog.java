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
@Table(name = "sheet_changelog")
@ToString
@RequiredArgsConstructor
public class SheetChangelog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "old_version")
    private Long oldVersion;

    @Column(name = "new_version")
    private Long newVersion;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation", nullable = false, length = Integer.MAX_VALUE)
    private OperationType operation;

    @Column(name = "author")
    private UUID author;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

}