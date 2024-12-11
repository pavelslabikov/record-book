package ru.example.recordbookbackend.dto.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.example.recordbookbackend.dto.*;

import java.io.Serializable;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.UserInfo}
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AggregationWithStatusDto implements Serializable {

    private RecordBooksAggregationDto aggregation;
    private AggregationStatusDto status;

    private SignatureInfoDto signatureInfo;
    private CertificateInfoDto certificateInfo;




}