package ru.example.recordbookbackend.dto.mapper;

import org.mapstruct.*;
import ru.example.recordbookbackend.dto.DeanEmployeeDto;
import ru.example.recordbookbackend.entity.DeanEmployee;
import ru.example.recordbookbackend.entity.Teacher;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DeanEmployeeMapper {
    DeanEmployee toEntity(DeanEmployeeDto userInfoDto);

    DeanEmployeeDto toDto(DeanEmployee userInfo);
    List<DeanEmployeeDto> toDtos(List<DeanEmployee> userInfos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DeanEmployee partialUpdate(DeanEmployeeDto userInfoDto, @MappingTarget DeanEmployee userInfo);
}