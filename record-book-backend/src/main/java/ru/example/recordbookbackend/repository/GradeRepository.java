package ru.example.recordbookbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.example.recordbookbackend.entity.Grade;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findAllBySheetIdAndSheetVersion(Long sheetId, Long sheetVersion);

    List<Grade> findAllBySheetId(Long sheetId);
}