package ru.example.recordbookbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "aggregation_status")
public class AggregationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "aggregation_version", nullable = false)
    private Long aggregationVersion;

    @Column(name = "aggregation_id", nullable = false)
    private Long aggregationId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "signature_validation_result", nullable = false, length = Integer.MAX_VALUE)
    private SignatureValidationResultType signatureValidationResult;

    @Column(name = "signature_validation_reason", length = Integer.MAX_VALUE)
    private String signatureValidationReason;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "integrity_validation_result", nullable = false, length = Integer.MAX_VALUE)
    private IntegrityValidationResultType integrityValidationResult;

    @Column(name = "integrity_validation_reason", length = Integer.MAX_VALUE)
    private String integrityValidationReason;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

}