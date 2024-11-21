package ru.example.recordbookbackend.dto.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.example.recordbookbackend.dto.UserInfoDto;
import ru.example.recordbookbackend.entity.OperationType;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.SheetChangelog}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DetailedSheetChangelogDto implements Serializable {
    private Long id;

    private SheetWithGradesDto oldSheet;

    private SheetWithGradesDto newSheet;

    private OperationType operation;
    private UserInfoDto userInfoDto;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime createdAt;
}