package com.example.sportsmanagementsystem.mapper;

import com.example.sportsmanagementsystem.Dto.SportFieldRequest;
import com.example.sportsmanagementsystem.Dto.SportFieldResponse;
import com.example.sportsmanagementsystem.model.LocationType;
import com.example.sportsmanagementsystem.model.SportField;
import com.example.sportsmanagementsystem.model.SportType;
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
    default SportType normalizeSportType(SportType sportType) {
        if (sportType == null) {
            return null;
        }
        try {
            return SportType.valueOf(sportType.name()); // Or custom logic
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid sport type: " + sportType);
        }
    }

    default LocationType normalizeLocationType(LocationType locationType) {
        if (locationType == null) {
            return null;
        }
        try {
            return LocationType.valueOf(locationType.name()); // Or custom logic
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid location type: " + locationType);
        }
    }
}
