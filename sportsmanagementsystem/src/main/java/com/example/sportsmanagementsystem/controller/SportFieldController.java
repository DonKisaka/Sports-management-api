package com.example.sportsmanagementsystem.controller;

import com.example.sportsmanagementsystem.Dto.SportFieldRequest;
import com.example.sportsmanagementsystem.Dto.SportFieldResponse;
import com.example.sportsmanagementsystem.model.LocationType;
import com.example.sportsmanagementsystem.model.SportType;
import com.example.sportsmanagementsystem.service.SportFieldService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sport-fields")
public class SportFieldController {

    private final SportFieldService sportFieldService;

    public SportFieldController(SportFieldService sportFieldService) {
        this.sportFieldService = sportFieldService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SportFieldResponse> createSportField(
         @Valid @RequestBody SportFieldRequest dto
    ) {
        SportFieldResponse response = sportFieldService.createSportField(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{fieldId}")
    public ResponseEntity<SportFieldResponse> getSportFieldById(
            @PathVariable Long fieldId
    ) {
        SportFieldResponse response = sportFieldService.getSportFieldById(fieldId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SportFieldResponse>> getAllSportFields(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) SportType sportType,
            @RequestParam(required = false) LocationType locationType
    ) {
        if (active != null && active) {
            List<SportFieldResponse> response = sportFieldService.getAllActiveSportFields();
            return ResponseEntity.ok(response);
        }
        if (sportType != null) {
            List<SportFieldResponse> response = sportFieldService.getSportFieldsBySportType(sportType);
            return ResponseEntity.ok(response);
        }
        if (locationType != null) {
            List<SportFieldResponse> response = sportFieldService.getSportFieldsByLocationType(locationType);
            return ResponseEntity.ok(response);
        }
        List<SportFieldResponse> response = sportFieldService.getAllSportFields();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{fieldId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SportFieldResponse> updateSportField(
            @PathVariable Long fieldId,
            @Valid @RequestBody SportFieldRequest dto
    ) {
        SportFieldResponse response = sportFieldService.updateSportField(fieldId, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{fieldId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateSportField(
            @PathVariable Long fieldId
    ) {
        sportFieldService.deactivateSportField(fieldId);
        return ResponseEntity.noContent().build();
    }
}
