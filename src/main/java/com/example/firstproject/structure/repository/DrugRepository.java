package com.example.firstproject.structure.repository;

import com.example.firstproject.structure.entity.DrugEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrugRepository extends JpaRepository<DrugEntity, Long> {
}
