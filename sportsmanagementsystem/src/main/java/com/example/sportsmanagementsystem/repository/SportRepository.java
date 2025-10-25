package com.example.sportsmanagementsystem.repository;

import com.example.sportsmanagementsystem.model.LocationType;
import com.example.sportsmanagementsystem.model.SportField;
import com.example.sportsmanagementsystem.model.SportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SportRepository extends JpaRepository<SportField, Long> {
    List<SportField> findAllByActiveTrue();
    boolean existsByName(String name);
    List<SportField> findBySportTypeAndActiveTrue(SportType sportType);
    List<SportField> findByLocationTypeAndActiveTrue(LocationType locationType);
}
