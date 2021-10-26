package com.example.transactionservice.repo;

import com.example.transactionservice.model.TransactionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionDto, Long> {
}
