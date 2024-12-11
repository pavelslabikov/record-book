package ru.example.recordbookbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.example.recordbookbackend.entity.Sheet;
import ru.example.recordbookbackend.entity.Student;
import ru.example.recordbookbackend.entity.VersionedId;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface SheetRepository extends JpaRepository<Sheet, VersionedId> {
    List<Sheet> findAllByDeleted(Boolean deleted);

    List<Sheet> findAllByVersionedIdId(Long id);

    @Query("select s from Sheet as s where s.versionedId.id = :id order by s.versionedId.version desc limit 1")
    Optional<Sheet> findLatest(@Param("id") Long id);

    List<Sheet> findAllByExamDateBetweenAndDeleted(LocalDate before, LocalDate after, Boolean deleted);



}