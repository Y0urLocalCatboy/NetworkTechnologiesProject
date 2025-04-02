package com.example.firstproject;

import com.example.firstproject.structure.repository.DrugRepository;
import com.example.firstproject.structure.entity.DrugEntity;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final DrugRepository drugRepository;

    public DatabaseInitializer(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> drugs = new ArrayList<>();
        List<DrugEntity> drugList = drugs.stream()
                .map(drug -> new DrugEntity())
                .collect(Collectors.toList());

        this.drugRepository.saveAll(drugList);
    }
}
