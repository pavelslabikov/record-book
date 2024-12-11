package ru.example.recordbookbackend.dto.mapper;

import org.mapstruct.*;
import ru.example.recordbookbackend.dto.CertificateInfoDto;
import ru.example.recordbookbackend.entity.CertificateInfo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CertificateInfoMapper {
    CertificateInfo toEntity(CertificateInfoDto certificateInfoDto);

    CertificateInfoDto toDto(CertificateInfo certificateInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CertificateInfo partialUpdate(CertificateInfoDto certificateInfoDto, @MappingTarget CertificateInfo certificateInfo);
}