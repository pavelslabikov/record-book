package ru.example.recordbookbackend.dto.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.example.recordbookbackend.dto.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@ToString
public class SheetWithGradesDto implements Serializable {

    private SheetDto sheetDto;

    private List<GradeDto> grades;

}