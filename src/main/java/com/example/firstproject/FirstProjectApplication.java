package com.example.firstproject;

import com.example.firstproject.structure.entity.DrugEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import com.example.firstproject.structure.repository.DrugRepository;

import java.util.List;

@SpringBootApplication
@RestController
public class FirstProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstProjectApplication.class, args);
    }
}

/*
build/resources/main/application.properties
 */