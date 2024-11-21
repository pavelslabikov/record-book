package ru.example.recordbookbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.example.recordbookbackend.entity.SignatureValidationResultType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.RecordBooksAggregation}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordBooksAggregationDto implements Serializable {
    private UUID id;
    private SignatureValidationResultType signatureValidationResult;
    private String signatureValidationReason;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private ZonedDateTime createdAt;
}