package com.example.firstproject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NamesRepository extends JpaRepository<FirstProjectApplication.Name, Long> {
}
