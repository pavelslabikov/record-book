package ru.example.recordbookbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.example.recordbookbackend.entity.IntegrityValidationResultType;
import ru.example.recordbookbackend.entity.SignatureValidationResultType;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.AggregationStatus}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AggregationStatusDto implements Serializable {

    private SignatureValidationResultType signatureValidationResult;
    private String signatureValidationReason;

    private IntegrityValidationResultType integrityValidationResult;
    private String integrityValidationReason;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime createdAt;
}