package sk.kasv.babcak.civicease.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kasv.babcak.civicease.models.Technician;
import sk.kasv.babcak.civicease.repositories.TechnicianRepository;
import sk.kasv.babcak.civicease.services.TechnicianService;
import java.util.List;

@RestController
@RequestMapping("/api/technicians")
@Tag(name = "Technician Controller", description = "APIs for managing technicians")
public class TechnicianController {

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private TechnicianService technicianService;

    @GetMapping
    @Operation(summary = "Get all technicians")
    public List<Technician> getAllTechnicians() {
        return technicianRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get technician by ID")
    public ResponseEntity<Technician> getTechnicianById(@PathVariable Long id) {
        return technicianRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new technician")
    public ResponseEntity<?> createTechnician(@RequestBody Technician technician) {
        try {
            Technician savedTechnician = technicianService.createTechnician(technician);
            return new ResponseEntity<>(savedTechnician, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing technician")
    public ResponseEntity<?> updateTechnician(@PathVariable Long id, @RequestBody Technician technicianDetails) {
        try {
            Technician updatedTechnician = technicianService.updateTechnician(id, technicianDetails);
            return ResponseEntity.ok(updatedTechnician);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete technician")
    public ResponseEntity<Void> deleteTechnician(@PathVariable Long id) {
        try {
            technicianService.deleteTechnician(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}