package ru.example.recordbookbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.recordbookbackend.entity.Student;
import ru.example.recordbookbackend.entity.Teacher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findAllByDeleted(Boolean deleted);

    Optional<Student> findByIdAndDeleted(UUID id, Boolean deleted);

    Optional<Student> findByUserIdAndDeleted(UUID id, Boolean deleted);
}