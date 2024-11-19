package ru.example.recordbookbackend.dto;

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
    private ZonedDateTime createdAt;
}