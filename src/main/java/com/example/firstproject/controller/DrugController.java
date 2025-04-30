package com.example.firstproject.controller;

import com.example.firstproject.controller.dto.drug.CreateDrugResponseDto;
import com.example.firstproject.controller.dto.drug.GetDrugDto;
import com.example.firstproject.service.DrugService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/drugs")
@PreAuthorize("isAuthenticated()")
public class DrugController {

    private final DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @GetMapping()
    public List<GetDrugDto > getAllDrugs() {
        return drugService.getAllDrugs();

    }

    @GetMapping("/{id}")
    public GetDrugDto
    getDrugById(@PathVariable Long id) {
        return drugService.getDrugById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreateDrugResponseDto createDrug(@Validated @RequestBody CreateDrugResponseDto drug) {
        var newDrug = drugService.createDrug(drug);
        return newDrug;
    }

//    @PostMapping
//    public ResponseEntity<CreateDrugResponseDto> createDrug(@Validated @RequestBody CreateDrugResponseDto drug) {
//        var newDrug = drugService.createDrug(drug);
//        return new ResponseEntity<>(newDrug, HttpStatus.BAD_GATEWAY);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDrug(@PathVariable Long id) {
        drugService.deleteDrug(id);
        return ResponseEntity.noContent().build();
    }
}
