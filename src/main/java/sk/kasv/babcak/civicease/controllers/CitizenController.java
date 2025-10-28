package sk.kasv.babcak.civicease.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kasv.babcak.civicease.models.Citizen;
import sk.kasv.babcak.civicease.repositories.CitizenRepository;
import sk.kasv.babcak.civicease.services.CitizenService;
import java.util.List;

@RestController
@RequestMapping("/api/citizens")
@Tag(name = "Citizen Controller", description = "APIs for managing citizens")
public class CitizenController {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private CitizenService citizenService;

    @GetMapping
    @Operation(summary = "Get all citizens")
    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get citizen by ID")
    public ResponseEntity<Citizen> getCitizenById(@PathVariable Long id) {
        return citizenRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new citizen")
    public ResponseEntity<Citizen> createCitizen(@RequestBody Citizen citizen) {
        Citizen savedCitizen = citizenService.createCitizen(citizen);
        return new ResponseEntity<>(savedCitizen, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing citizen")
    public ResponseEntity<?> updateCitizen(@PathVariable Long id, @RequestBody Citizen citizenDetails) {
        try {
            Citizen updatedCitizen = citizenService.updateCitizen(id, citizenDetails);
            return ResponseEntity.ok(updatedCitizen);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete citizen")
    public ResponseEntity<Void> deleteCitizen(@PathVariable Long id) {
        try {
            citizenService.deleteCitizen(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}