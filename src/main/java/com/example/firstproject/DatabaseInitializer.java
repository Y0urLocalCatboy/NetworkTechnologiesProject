package com.example.firstproject;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final NamesRepository namesRepository;

    public DatabaseInitializer(NamesRepository namesRepository) {
        this.namesRepository = namesRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> names = new ArrayList<>();
        names.add("Gabriel");
        names.add("Aleksandra");
        names.add("Alfred");
        List<FirstProjectApplication.Name> nameList = names.stream()
                .map(name -> new FirstProjectApplication.Name(name))
                .collect(Collectors.toList());

        this.namesRepository.saveAll(nameList);
    }
}
