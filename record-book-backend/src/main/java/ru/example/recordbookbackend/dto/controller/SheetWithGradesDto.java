package ru.example.recordbookbackend.dto.controller;

import lombok.Data;
import ru.example.recordbookbackend.dto.*;

import java.io.Serializable;
import java.util.List;


@Data
public class SheetWithGradesDto implements Serializable {

    private SheetDto sheetDto;

    private List<GradeDto> grades;

}