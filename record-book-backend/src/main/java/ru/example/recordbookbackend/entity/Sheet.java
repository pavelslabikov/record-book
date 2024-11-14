package ru.example.recordbookbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "sheet")
@ToString
@RequiredArgsConstructor
public class Sheet {
    @EmbeddedId
    private VersionedId id;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "subject_name", length = Integer.MAX_VALUE)
    private String subjectName;

    @Column(name = "academic_year", length = Integer.MAX_VALUE)
    private String academicYear;

    @Column(name = "credit_units")
    private Integer creditUnits;

    @Column(name = "exam_date")
    private LocalDate examDate;

    @Column(name = "diploma_form", length = Integer.MAX_VALUE)
    private String diplomaForm;

    @Column(name = "topic", length = Integer.MAX_VALUE)
    private String topic;

    @Column(name = "diploma_defense_date")
    private LocalDate diplomaDefenseDate;

    @Column(name = "protocol_date")
    private LocalDate protocolDate;

    @Column(name = "protocol_number", length = Integer.MAX_VALUE)
    private String protocolNumber;

    @Column(name = "type", length = Integer.MAX_VALUE, nullable = false)
    @Enumerated(EnumType.STRING)
    private SheetType type;

    @Column(name = "research_type", length = Integer.MAX_VALUE)
    private String researchType;

    @Column(name = "practice_place", length = Integer.MAX_VALUE)
    private String practicePlace;

    @Column(name = "teacher_id")
    private Integer teacherId;

}