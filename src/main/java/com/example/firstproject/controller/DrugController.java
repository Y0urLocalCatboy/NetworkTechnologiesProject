package com.example.firstproject.controller;

import com.example.firstproject.controller.dto.drug.*;
import com.example.firstproject.service.DrugService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/drugs")
@PreAuthorize("isAuthenticated()")
public class DrugController {

    private final DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @GetMapping
    public ResponseEntity<?> getAllDrugs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body("Invalid page or size parameters");
        }
        Page<GetDrugDto> drugPage = drugService.getAllDrugsPaged(page, size, sort, direction);
        return ResponseEntity.ok(drugPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetDrugDto> getDrugById(@PathVariable Long id) {
        GetDrugDto drugDto = drugService.getDrugById(id);
        return ResponseEntity.ok(drugDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetDrugDto> createDrug(@Validated @RequestBody AddDrugRequest addDrugRequest) {
        GetDrugDto newDrug = drugService.createDrug(addDrugRequest);
        return new ResponseEntity<>(newDrug, HttpStatus.CREATED);
    }

    @PostMapping("/buy")
    public ResponseEntity<BuyDrugResponse> buyDrug(@Validated @RequestBody BuyDrugRequest buyDrugRequest) {
        BuyDrugResponse response = drugService.processDrugPurchase(buyDrugRequest);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteDrug(@PathVariable Long id) {
        drugService.deleteDrug(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetDrugDto> updateDrug(@PathVariable Long id, @Validated @RequestBody UpdateDrugDto updateDrugDto) {
        GetDrugDto updatedDrug = drugService.updateDrug(id, updateDrugDto);
        return ResponseEntity.ok(updatedDrug);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetDrugDto> partialUpdateDrug(@PathVariable Long id, @Validated @RequestBody UpdateDrugDto updateDrugDto) {
        GetDrugDto updatedDrug = drugService.updateDrug(id, updateDrugDto);
        return ResponseEntity.ok(updatedDrug);
    }
}