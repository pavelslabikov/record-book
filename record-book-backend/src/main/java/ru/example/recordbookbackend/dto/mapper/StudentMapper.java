package ru.example.recordbookbackend.dto.mapper;

import org.mapstruct.*;
import ru.example.recordbookbackend.dto.StudentDto;
import ru.example.recordbookbackend.dto.UserInfoDto;
import ru.example.recordbookbackend.dto.controller.StudentWithUserInfoDto;
import ru.example.recordbookbackend.dto.controller.TeacherWithUserInfoDto;
import ru.example.recordbookbackend.entity.Student;
import ru.example.recordbookbackend.entity.Teacher;
import ru.example.recordbookbackend.entity.UserInfo;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentDto userInfoDto);

    StudentDto toDto(Student userInfo);
    List<StudentDto> toDtos(List<Student> userInfos);

    StudentWithUserInfoDto toUserDto(Student userInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Student partialUpdate(StudentDto userInfoDto, @MappingTarget Student userInfo);
}