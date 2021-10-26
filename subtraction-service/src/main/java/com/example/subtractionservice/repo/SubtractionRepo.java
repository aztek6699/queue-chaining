package com.example.subtractionservice.repo;

import com.example.subtractionservice.model.SubtractionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtractionRepo extends JpaRepository<SubtractionDto, Long> {
}
