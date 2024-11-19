package ru.example.recordbookbackend.dto.mapper;

import org.mapstruct.*;
import ru.example.recordbookbackend.dto.SheetChangelogDto;
import ru.example.recordbookbackend.dto.SheetDto;
import ru.example.recordbookbackend.dto.controller.DetailedSheetChangelogDto;
import ru.example.recordbookbackend.dto.controller.SheetWithGradesDto;
import ru.example.recordbookbackend.entity.Sheet;
import ru.example.recordbookbackend.entity.SheetChangelog;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SheetChangelogMapper {
    SheetChangelog toEntity(SheetChangelogDto userInfoDto);

    SheetChangelogDto toDto(SheetChangelog userInfo);
    List<SheetChangelogDto> toDtos(List<SheetChangelog> userInfos);

    DetailedSheetChangelogDto toSheetWithGradesDto(SheetChangelog userInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SheetChangelog partialUpdate(SheetChangelogDto userInfoDto, @MappingTarget SheetChangelog userInfo);
}