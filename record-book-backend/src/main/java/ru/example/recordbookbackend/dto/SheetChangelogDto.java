package ru.example.recordbookbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
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
public class SheetChangelogDto implements Serializable {
    private Long id;
    private OperationType operation;
    private UUID author;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime createdAt;
}