package ru.example.recordbookbackend.dto.mapper;

import org.mapstruct.*;
import ru.example.recordbookbackend.dto.StudentDto;
import ru.example.recordbookbackend.dto.TeacherDto;
import ru.example.recordbookbackend.dto.controller.TeacherWithUserInfoDto;
import ru.example.recordbookbackend.entity.Student;
import ru.example.recordbookbackend.entity.Teacher;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TeacherMapper {
    Teacher toEntity(TeacherDto userInfoDto);

    TeacherDto toDto(Teacher userInfo);

    TeacherWithUserInfoDto toUserDto(Teacher userInfo);
    List<TeacherDto> toDtos(List<Teacher> userInfos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Teacher partialUpdate(TeacherDto userInfoDto, @MappingTarget Teacher userInfo);
}