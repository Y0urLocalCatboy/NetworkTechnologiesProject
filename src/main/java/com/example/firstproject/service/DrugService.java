package com.example.firstproject.service;

import com.example.firstproject.controller.dto.drug.*;
import com.example.firstproject.service.errors.DrugNotFoundError;
import com.example.firstproject.structure.entity.DrugEntity;
import com.example.firstproject.structure.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
                .map(drug -> new GetDrugDto(drug.getId(),
                        drug.getName(),
                        drug.getDescription(),
                        drug.getPrice(),
                        drug.getQuantity(),
                        drug.getManufacturer()))
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
                .map(drug -> new GetDrugDto(drug.getId(),
                        drug.getName(),
                        drug.getDescription(),
                        drug.getPrice(),
                        drug.getQuantity(),
                        drug.getManufacturer()))
                .toList();
    }

    public GetDrugDto getDrugById(Long id) {
        var drugEntity = drugRepository.findById(id).orElseThrow(DrugNotFoundError::new);
        return new GetDrugDto(drugEntity.getId(), drugEntity.getName(), drugEntity.getDescription(), drugEntity.getPrice(), drugEntity.getQuantity(), drugEntity.getManufacturer());
    }

    public void deleteDrug(Long id) {
        if (!drugRepository.existsById(id)) {
            throw new DrugNotFoundError();
        }
        drugRepository.deleteById(id);
    }

    public GetDrugDto updateDrug(Long id, UpdateDrugDto updateDrugDto) {
        var drugEntity = drugRepository.findById(id)
                .orElseThrow(DrugNotFoundError::new);

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

    public GetDrugDto createDrug(AddDrugRequest request) {
        DrugEntity drugEntity = new DrugEntity();
        drugEntity.setName(request.getName());
        drugEntity.setDescription(request.getDescription());
        drugEntity.setPrice(request.getPrice());
        drugEntity.setQuantity(request.getStock());
        drugEntity.setManufacturer(request.getManufacturer());

        DrugEntity savedDrug = drugRepository.save(drugEntity);

        return new GetDrugDto(
                savedDrug.getId(),
                savedDrug.getName(),
                savedDrug.getDescription(),
                savedDrug.getPrice(),
                savedDrug.getQuantity(),
                savedDrug.getManufacturer()
        );
    }

    @Transactional
    public BuyDrugResponse processDrugPurchase(BuyDrugRequest request) {
        Optional<DrugEntity> drugOptional = drugRepository.findById(request.getDrugId());
        if (drugOptional.isEmpty()) {
            return new BuyDrugResponse(null, "Drug not found", false);
        }

        DrugEntity drug = drugOptional.get();

        if (drug.getQuantity() == null || drug.getQuantity() < request.getQuantity()) {
            return new BuyDrugResponse(null, "Not enough drugs in stock", false);
        }
        if (request.getQuantity() <= 0) {
            return new BuyDrugResponse(null, "Quantity to buy must be positive", false);
        }


        drug.setQuantity(drug.getQuantity() - request.getQuantity());
        drugRepository.save(drug);


        return new BuyDrugResponse(null, "Purchase successful", true);
    }
}