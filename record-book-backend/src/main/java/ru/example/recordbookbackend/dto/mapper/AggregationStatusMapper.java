package ru.example.recordbookbackend.dto.mapper;

import org.mapstruct.*;
import ru.example.recordbookbackend.dto.AggregationStatusDto;
import ru.example.recordbookbackend.entity.AggregationStatus;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AggregationStatusMapper {
    AggregationStatus toEntity(AggregationStatusDto aggregationStatusDto);

    AggregationStatusDto toDto(AggregationStatus aggregationStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AggregationStatus partialUpdate(AggregationStatusDto aggregationStatusDto, @MappingTarget AggregationStatus aggregationStatus);
}