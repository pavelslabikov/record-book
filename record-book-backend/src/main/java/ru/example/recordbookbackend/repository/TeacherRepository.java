package ru.example.recordbookbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.recordbookbackend.entity.Student;
import ru.example.recordbookbackend.entity.Teacher;
import ru.example.recordbookbackend.entity.UserInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    List<Teacher> findAllByDeleted(Boolean deleted);

    Optional<Teacher> findByIdAndDeleted(Integer id, Boolean deleted);

    Optional<Teacher> findByUserIdAndDeleted(UUID id, Boolean deleted);
}