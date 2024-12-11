package ru.example.recordbookbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "record_books_aggregation")
public class RecordBooksAggregation {

    @EmbeddedId
    private VersionedId versionedId;

    @NotNull
    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;

    @NotNull
    @Column(name = "period_end", nullable = false)
    private LocalDate periodEnd;

    @Column(name = "signature")
    private UUID signature;

    @Column(name = "author", nullable = false)
    private UUID author;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "serialization_format", nullable = false)
    @Enumerated(EnumType.STRING)
    private SerializationFormat serializationFormat;

    @Column(name = "serialized_content", nullable = false)
    private byte[] serializedContent;

}