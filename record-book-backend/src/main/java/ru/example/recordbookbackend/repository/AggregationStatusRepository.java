package ru.example.recordbookbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.recordbookbackend.entity.AggregationStatus;

import java.util.Optional;

public interface AggregationStatusRepository extends JpaRepository<AggregationStatus, Long> {
    Optional<AggregationStatus> findByAggregationIdAndAggregationVersion(Long aggregationId, Long aggregationVersion);
}