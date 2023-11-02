package com.example.backend.repositories;

import com.example.backend.entities.DetailProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailProfileRepository extends JpaRepository<DetailProfile, Integer> {
}
