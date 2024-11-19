package ru.example.recordbookbackend.dto.mapper;

import org.mapstruct.*;
import ru.example.recordbookbackend.dto.GradeDto;
import ru.example.recordbookbackend.dto.SheetDto;
import ru.example.recordbookbackend.dto.controller.SheetWithGradesDto;
import ru.example.recordbookbackend.entity.Grade;
import ru.example.recordbookbackend.entity.Sheet;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GradeMapper {
    Grade toEntity(GradeDto userInfoDto);

    GradeDto toDto(Grade userInfo);
    List<GradeDto> toDtos(List<Grade> userInfos);

    List<Grade> toEntities(List<GradeDto> userInfos);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Grade partialUpdate(GradeDto userInfoDto, @MappingTarget Grade userInfo);
}