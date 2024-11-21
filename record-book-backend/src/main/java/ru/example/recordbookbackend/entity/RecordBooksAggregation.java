package ru.example.recordbookbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "record_books_aggregation")
public class RecordBooksAggregation {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "signature_validation_result", nullable = false, length = Integer.MAX_VALUE)
    private SignatureValidationResultType signatureValidationResult;

    @Column(name = "signature_file")
    private byte[] signatureFile;

    @Column(name = "original_file")
    private byte[] originalFile;

    @Column(name = "file_digest")
    private byte[] fileDigest;

    @Column(name = "signature_validation_reason", length = Integer.MAX_VALUE)
    private String signatureValidationReason;

    @NotNull
    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;

    @NotNull
    @Column(name = "period_end", nullable = false)
    private LocalDate periodEnd;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

}