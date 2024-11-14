package ru.example.recordbookbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "student")
@ToString
@RequiredArgsConstructor
public class Student {
    @Id
    @Column(name = "id_card_number", nullable = false)
    private UUID id;

    @Column(name = "user_id")
    private UUID user_id;

    @Column(name = "course_number", nullable = false)
    private Integer courseNumber;

    @Column(name = "group_name", nullable = false, length = Integer.MAX_VALUE)
    private String groupName;

    @Column(name = "faculty", nullable = false, length = Integer.MAX_VALUE)
    private String faculty;

    @Column(name = "grade_book_number", nullable = false, length = Integer.MAX_VALUE)
    private String gradeBookNumber;

    @Column(name = "specialization_code", nullable = false, length = Integer.MAX_VALUE)
    private String specializationCode;

}