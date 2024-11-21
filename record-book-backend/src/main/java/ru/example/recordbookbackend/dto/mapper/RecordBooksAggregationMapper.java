package ru.example.recordbookbackend.dto.mapper;

import org.mapstruct.*;
import ru.example.recordbookbackend.dto.RecordBooksAggregationDto;
import ru.example.recordbookbackend.entity.RecordBooksAggregation;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RecordBooksAggregationMapper {
    RecordBooksAggregation toEntity(RecordBooksAggregationDto recordBooksAggregationDto);

    RecordBooksAggregationDto toDto(RecordBooksAggregation recordBooksAggregation);

    List<RecordBooksAggregationDto> toDtos(List<RecordBooksAggregation> recordBooksAggregation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RecordBooksAggregation partialUpdate(RecordBooksAggregationDto recordBooksAggregationDto, @MappingTarget RecordBooksAggregation recordBooksAggregation);
}