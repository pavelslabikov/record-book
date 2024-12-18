package ru.example.recordbookbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.example.recordbookbackend.entity.CertificateType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.CertificateInfo}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CertificateInfoDto implements Serializable {
    private UUID id;
    private CertificateType type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate notBefore;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate notAfter;
    private String subjectInfo;

    private String issuerInfo;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime createdAt;
}