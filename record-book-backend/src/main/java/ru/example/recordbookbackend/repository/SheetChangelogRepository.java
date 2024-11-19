package ru.example.recordbookbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.recordbookbackend.entity.SheetChangelog;

import java.util.List;

public interface SheetChangelogRepository extends JpaRepository<SheetChangelog, Long> {
    List<SheetChangelog> findAllByEntityIdOrderByCreatedAtAsc(Long entityId);
}