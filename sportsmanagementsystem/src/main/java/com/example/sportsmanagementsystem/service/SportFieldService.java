package com.example.sportsmanagementsystem.service;

import com.example.sportsmanagementsystem.Dto.SportFieldRequest;
import com.example.sportsmanagementsystem.Dto.SportFieldResponse;
import com.example.sportsmanagementsystem.mapper.SportFieldMapper;
import com.example.sportsmanagementsystem.model.LocationType;
import com.example.sportsmanagementsystem.model.SportField;
import com.example.sportsmanagementsystem.model.SportType;
import com.example.sportsmanagementsystem.repository.SportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SportFieldService {

    private final SportRepository sportRepository;
    private final SportFieldMapper sportFieldMapper;

    public SportFieldService(SportRepository sportRepository, SportFieldMapper sportFieldMapper) {
        this.sportRepository = sportRepository;
        this.sportFieldMapper = sportFieldMapper;
    }


    @Transactional
    public SportFieldResponse createSportField(SportFieldRequest dto) {
        SportField newField = sportFieldMapper.toEntity(dto);
        newField.setActive(true);
        SportField savedField = sportRepository.save(newField);
        return sportFieldMapper.toDto(savedField);
    }

    public SportFieldResponse getSportFieldById(Long fieldId) {
        return sportRepository.findById(fieldId)
                .map(sportFieldMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));
    }

    public List<SportFieldResponse> getAllSportFields() {
        List<SportField> sportFields = sportRepository.findAll();
        return sportFieldMapper.toDtoList(sportFields);
    }

    public List<SportFieldResponse> getAllActiveSportFields() {
        List<SportField> sportFields = sportRepository.findAllByActiveTrue();
        return sportFieldMapper.toDtoList(sportFields);
    }

    public List<SportFieldResponse> getSportFieldsBySportType(SportType sportType) {
        List<SportField> sportFields = sportRepository.findBySportTypeAndActiveTrue(sportType);
        return sportFieldMapper.toDtoList(sportFields);
    }

    public List<SportFieldResponse> getSportFieldsByLocationType(LocationType locationType) {
        List<SportField> sportFields = sportRepository.findByLocationTypeAndActiveTrue(locationType);
        return sportFieldMapper.toDtoList(sportFields);
    }


    @Transactional
    public SportFieldResponse updateSportField(Long fieldId, SportFieldRequest dto) {
        SportField fieldToUpdate = sportRepository.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        sportFieldMapper.updateEntityFromDto(fieldToUpdate, dto);
        SportField updatedField = sportRepository.save(fieldToUpdate);
        return sportFieldMapper.toDto(updatedField);
    }


    @Transactional
    public void deactivateSportField(Long fieldId) {
        SportField sportField = sportRepository.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));
        sportField.setActive(false);
        sportRepository.save(sportField);
    }
}
