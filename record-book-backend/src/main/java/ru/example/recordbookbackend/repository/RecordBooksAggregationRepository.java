package ru.example.recordbookbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.example.recordbookbackend.entity.RecordBooksAggregation;
import ru.example.recordbookbackend.entity.VersionedId;

import java.util.Optional;

public interface RecordBooksAggregationRepository extends JpaRepository<RecordBooksAggregation, VersionedId> {
    @Query("select s from RecordBooksAggregation as s where s.versionedId.id = :id order by s.versionedId.version desc limit 1")
    Optional<RecordBooksAggregation> findLatest(@Param("id") Long id);

    @Query("select s from RecordBooksAggregation as s  order by s.versionedId.id desc limit 1")
    Optional<RecordBooksAggregation> getLatestId();
}