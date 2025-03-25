package com.example.firstproject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
public class FirstProjectApplication {

    @Autowired
    private NamesRepository namesRepository;

    public static void main(String[] args) {
        SpringApplication.run(FirstProjectApplication.class, args);
    }

    @PostMapping("/names")
    public Name addName(@RequestBody Name name) {
        return namesRepository.save(name);
    }

    @GetMapping("/names")
    public List<Name> getAllNames() {
        return namesRepository.findAll();
    }

    @Entity
    public static class Name {

        public Name(String name){
            this.name = name;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        protected Name(){}
    }

}

/*
build/resources/main/application.properties
 */