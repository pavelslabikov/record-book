package ru.example.recordbookbackend.dto.mapper;

import org.mapstruct.*;
import ru.example.recordbookbackend.dto.SheetDto;
import ru.example.recordbookbackend.dto.StudentDto;
import ru.example.recordbookbackend.dto.controller.SheetWithGradesDto;
import ru.example.recordbookbackend.dto.controller.StudentWithUserInfoDto;
import ru.example.recordbookbackend.entity.Sheet;
import ru.example.recordbookbackend.entity.Student;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SheetMapper {
    Sheet toEntity(SheetDto userInfoDto);

    SheetDto toDto(Sheet userInfo);
    List<SheetDto> toDtos(List<Sheet> userInfos);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Sheet partialUpdate(SheetDto userInfoDto, @MappingTarget Sheet userInfo);
}