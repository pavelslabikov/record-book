package ru.example.recordbookbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.recordbookbackend.entity.DeanEmployee;
import ru.example.recordbookbackend.entity.Student;
import ru.example.recordbookbackend.entity.Teacher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeanEmployeeRepository extends JpaRepository<DeanEmployee, Integer> {
    List<DeanEmployee> findAllByDeleted(Boolean deleted);

    Optional<DeanEmployee> findByIdAndDeleted(Integer id, Boolean deleted);

    Optional<DeanEmployee> findByUserIdAndDeleted(UUID id, Boolean deleted);
}