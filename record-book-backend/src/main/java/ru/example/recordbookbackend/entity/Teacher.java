package ru.example.recordbookbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "teacher")
@ToString
@RequiredArgsConstructor
public class Teacher {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "academic_rank", nullable = false, length = Integer.MAX_VALUE)
    private String academicRank;

    @Column(name = "job_title", nullable = false, length = Integer.MAX_VALUE)
    private String jobTitle;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

}