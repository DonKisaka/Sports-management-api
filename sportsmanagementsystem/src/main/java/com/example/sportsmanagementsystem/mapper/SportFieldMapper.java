package com.example.sportsmanagementsystem.mapper;

import com.example.sportsmanagementsystem.Dto.SportFieldRequest;
import com.example.sportsmanagementsystem.Dto.SportFieldResponse;
import com.example.sportsmanagementsystem.model.SportField;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SportFieldMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SportField toEntity(SportFieldRequest dto);

    SportFieldResponse toDto(SportField entity);

    List<SportFieldResponse> toDtoList(List<SportField> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "sportType", expression = "java(normalizeSportType(request.sportType()))")
    @Mapping(target = "locationType", expression = "java(normalizeLocationType(request.locationType()))")
    void updateEntityFromDto(@MappingTarget SportField entity, SportFieldRequest dto);

}
