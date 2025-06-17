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
        if (drugRepository.count() == 0) {
            DrugEntity drug1 = new DrugEntity();
            drug1.setName("Paracetamol XYZ");
            drug1.setDescription("Drug used for pain relief and fever reduction.");
            drug1.setPrice(10.99);
            drug1.setQuantity(100);
            drug1.setManufacturer("PharmaCorp");

            DrugEntity drug2 = new DrugEntity();
            drug2.setName("Ibuprofen ABC");
            drug2.setDescription("Drug used for pain relief and inflammation.");
            drug2.setPrice(15.50);
            drug2.setQuantity(75);
            drug2.setManufacturer("MedicaPro");

            DrugEntity drug3 = new DrugEntity();
            drug3.setName("Vitamin C Plus");
            drug3.setDescription("Vitamin C supplement for immune support.");
            drug3.setPrice(22.00);
            drug3.setQuantity(200);
            drug3.setManufacturer("HealthLife");


            drugList = List.of(drug1, drug2, drug3);
            this.drugRepository.saveAll(drugList);
            System.out.println("INFO: Added sample drugs to the database.");
        } else {
            System.out.println("INFO: Drugs already exist in the database, skipping initialization.");
        }
    }
}
