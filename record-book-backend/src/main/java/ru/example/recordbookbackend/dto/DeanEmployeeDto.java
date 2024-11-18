package ru.example.recordbookbackend.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ru.example.recordbookbackend.entity.DeanEmployee}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeanEmployeeDto implements Serializable {
    private Integer id;
    private UUID userId;
    private String jobTitle;
}