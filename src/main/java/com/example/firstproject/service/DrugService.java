package com.example.firstproject.service;

import com.example.firstproject.controller.dto.drug.CreateDrugResponseDto;
import com.example.firstproject.controller.dto.drug.CreateDrugDto;
import com.example.firstproject.controller.dto.drug.DrugDto;
import com.example.firstproject.controller.dto.drug.GetDrugDto;
import com.example.firstproject.controller.dto.drug.UpdateDrugDto;
import com.example.firstproject.service.errors.DrugNotFoundError;
import com.example.firstproject.service.models.DrugModel;
import com.example.firstproject.structure.entity.DrugEntity;
import com.example.firstproject.structure.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugService {

    private final DrugRepository drugRepository;

    @Autowired
    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    public List<GetDrugDto> getAllDrugs() {
        var drugs = drugRepository.findAll();
        return drugs.stream()
                .map(drug -> new GetDrugDto(drug.getId(), drug.getName(), drug.getDescription(), drug.getPrice(), drug.getQuantity(), drug.getManufacturer()))
                .toList();
    }

    public Page<GetDrugDto> getAllDrugsPaged(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DrugEntity> drugsPage = drugRepository.findAll(pageable);

        return drugsPage.map(drug -> 
                new GetDrugDto(
                    drug.getId(), 
                    drug.getName(), 
                    drug.getDescription(), 
                    drug.getPrice(), 
                    drug.getQuantity(), 
                    drug.getManufacturer()
                )
        );
    }

    public List<GetDrugDto> getDrugsByName(String name) {
        var drugs = drugRepository.findAll().stream()
                .filter(drug -> drug.getName().equalsIgnoreCase(name))
                .toList();
        return drugs.stream()
                .map(drug -> new GetDrugDto(drug.getId(), drug.getName(), drug.getDescription(), drug.getPrice(), drug.getQuantity(), drug.getManufacturer()))
                .toList();
    }

    public GetDrugDto getDrugById(Long id) {
        var drugEntity = drugRepository.findById(id).orElseThrow(() -> new DrugNotFoundError());
        return new GetDrugDto(drugEntity.getId(), drugEntity.getName(), drugEntity.getDescription(), drugEntity.getPrice(), drugEntity.getQuantity(), drugEntity.getManufacturer());
    }

    public CreateDrugResponseDto createDrug(CreateDrugResponseDto drug) {
        var drugEntity = new DrugEntity();
        drugEntity.setName(drug.getName());
        drugEntity.setDescription(drug.getDescription());
        drugEntity.setPrice(drug.getPrice());
        drugEntity.setQuantity(drug.getQuantity());
        drugEntity.setManufacturer(drug.getManufacturer());
        var savedDrug = drugRepository.save(drugEntity);

        return new CreateDrugResponseDto(savedDrug.getId(), savedDrug.getName(), savedDrug.getDescription(), savedDrug.getPrice(), savedDrug.getQuantity(), savedDrug.getManufacturer());
    }

    public void deleteDrug(Long id) {
    if(!drugRepository.existsById(id)) {
         new DrugNotFoundError();
    }
        drugRepository.deleteById(id);
    }

    public GetDrugDto updateDrug(Long id, UpdateDrugDto updateDrugDto) {
        var drugEntity = drugRepository.findById(id)
                .orElseThrow(() -> new DrugNotFoundError());

        if (updateDrugDto.getName() != null) {
            drugEntity.setName(updateDrugDto.getName());
        }

        if (updateDrugDto.getDescription() != null) {
            drugEntity.setDescription(updateDrugDto.getDescription());
        }

        if (updateDrugDto.getPrice() != null) {
            drugEntity.setPrice(updateDrugDto.getPrice());
        }

        if (updateDrugDto.getQuantity() != null) {
            drugEntity.setQuantity(updateDrugDto.getQuantity());
        }

        if (updateDrugDto.getManufacturer() != null) {
            drugEntity.setManufacturer(updateDrugDto.getManufacturer());
        }

        var savedDrug = drugRepository.save(drugEntity);

        return new GetDrugDto(
                savedDrug.getId(),
                savedDrug.getName(),
                savedDrug.getDescription(),
                savedDrug.getPrice(),
                savedDrug.getQuantity(),
                savedDrug.getManufacturer()
        );
    }

    public DrugDto create(CreateDrugDto drug) {
        var drugModel = new DrugModel(null, drug.getName(), drug.getDescription(), drug.getPrice());

        var drugEntity = new DrugEntity();
        drugEntity.setName(drugModel.getName());
        drugEntity.setDescription(drugModel.getDescription());
        drugEntity.setPrice(drugModel.getPrice());

        drugRepository.save(drugEntity);

        return new DrugDto(
                drugEntity.getId(),
                drugEntity.getName(),
                drugEntity.getPrice(),
                drugEntity.getDescription()
        );
    }
}
