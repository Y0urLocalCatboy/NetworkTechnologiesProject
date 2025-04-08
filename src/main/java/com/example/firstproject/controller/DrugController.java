package com.example.firstproject.controller;

import com.example.firstproject.FirstProjectApplication;
import com.example.firstproject.controller.dto.CreateDrugDto;
import com.example.firstproject.controller.dto.CreateDrugResponseDto;
import com.example.firstproject.controller.dto.GetDrugDto;
import com.example.firstproject.service.DrugService;
import com.example.firstproject.structure.entity.DrugEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.firstproject.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/drugs")
public class DrugController {

    private DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @GetMapping()
    public List<GetDrugDto> getAllDrugs() {
        return drugService.getAllDrugs();

    }

    @GetMapping("/{id}")
    public GetDrugDto getDrugById(@PathVariable Long id) {
        return drugService.getDrugById(id);
    }

    @PostMapping
    public ResponseEntity<CreateDrugResponseDto> createDrug(@RequestBody CreateDrugResponseDto drug) {
        var newDrug = drugService.createDrug(drug);
        return new ResponseEntity<>(newDrug, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDrug(@PathVariable Long id) {
        drugService.deleteDrug(id);
        return ResponseEntity.noContent().build();
    }
}
