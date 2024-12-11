package ru.example.recordbookbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.example.recordbookbackend.entity.DigestAlgorithmType;
import ru.example.recordbookbackend.entity.SignatureType;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.SignatureInfo}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SignatureInfoDto implements Serializable {
    private UUID id;
    private SignatureType type;
    private DigestAlgorithmType digestAlgorithm;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime createdAt;
}