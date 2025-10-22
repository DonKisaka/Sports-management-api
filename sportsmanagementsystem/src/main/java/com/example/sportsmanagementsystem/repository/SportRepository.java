package com.example.sportsmanagementsystem.repository;

import com.example.sportsmanagementsystem.model.SportField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<SportField, Long> {
    SportField findByNameContainingIgnoreCase(String sportName);
    SportField findByActiveTrue(Boolean active);
    Page<SportField> findAllByActiveTrue(Pageable pageable);
    SportField findByActiveTrueAndId(Long id);
}
