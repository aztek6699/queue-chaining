package com.example.additionservice.repo;

import com.example.additionservice.model.AdditionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionRepo extends JpaRepository<AdditionDto, Long> {
}
