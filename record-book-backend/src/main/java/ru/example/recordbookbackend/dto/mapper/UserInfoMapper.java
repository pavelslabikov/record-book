package ru.example.recordbookbackend.dto.mapper;

import ru.example.recordbookbackend.dto.UserInfoDto;
import ru.example.recordbookbackend.entity.UserInfo;

import java.util.List;

@org.mapstruct.Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserInfoMapper {
    UserInfo toEntity(UserInfoDto userInfoDto);

    UserInfoDto toDto(UserInfo userInfo);
    List<UserInfoDto> toDtos(List<UserInfo> userInfos);

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    UserInfo partialUpdate(UserInfoDto userInfoDto, @org.mapstruct.MappingTarget UserInfo userInfo);
}