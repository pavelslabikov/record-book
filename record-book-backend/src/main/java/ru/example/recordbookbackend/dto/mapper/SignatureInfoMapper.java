package ru.example.recordbookbackend.dto.mapper;

import org.mapstruct.*;
import ru.example.recordbookbackend.dto.SignatureInfoDto;
import ru.example.recordbookbackend.entity.SignatureInfo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SignatureInfoMapper {
    SignatureInfo toEntity(SignatureInfoDto signatureInfoDto);

    SignatureInfoDto toDto(SignatureInfo signatureInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SignatureInfo partialUpdate(SignatureInfoDto signatureInfoDto, @MappingTarget SignatureInfo signatureInfo);
}