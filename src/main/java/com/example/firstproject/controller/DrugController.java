package com.example.firstproject.controller;

import com.example.firstproject.controller.dto.drug.CreateDrugResponseDto;
import com.example.firstproject.controller.dto.drug.GetDrugDto;
import com.example.firstproject.controller.dto.drug.UpdateDrugDto;
import com.example.firstproject.service.DrugService;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<?> getAllDrugs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body("Invalid page or size parameters");
        }

        try {
            Page<GetDrugDto> drugPage = drugService.getAllDrugsPaged(page, size, sort, direction);
            return ResponseEntity.ok(drugPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
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

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GetDrugDto updateDrug(@PathVariable Long id, @RequestBody UpdateDrugDto updateDrugDto) {
        return drugService.updateDrug(id, updateDrugDto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GetDrugDto partialUpdateDrug(@PathVariable Long id, @RequestBody UpdateDrugDto updateDrugDto) {
        return drugService.updateDrug(id, updateDrugDto);
    }
}
