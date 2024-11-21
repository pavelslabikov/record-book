package ru.example.recordbookbackend.dto.controller;

import lombok.*;
import ru.example.recordbookbackend.dto.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
public class SheetWithGradesDto implements Serializable {

    private SheetDto sheetDto;

    private List<GradeDto> grades;

}