package com.example.backendassignment.repository;

import com.example.backendassignment.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataRepository extends JpaRepository<Data, Long> {

    Optional<Data> findByCode(String code);
}
