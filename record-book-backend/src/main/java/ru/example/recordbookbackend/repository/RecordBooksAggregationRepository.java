package ru.example.recordbookbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.recordbookbackend.entity.RecordBooksAggregation;

import java.util.UUID;

public interface RecordBooksAggregationRepository extends JpaRepository<RecordBooksAggregation, UUID> {
}