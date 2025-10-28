package sk.kasv.babcak.civicease.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kasv.babcak.civicease.dto.CreateReportRequest;
import sk.kasv.babcak.civicease.dto.UpdateReportRequest;
import sk.kasv.babcak.civicease.models.Report;
import sk.kasv.babcak.civicease.repositories.ReportRepository;
import sk.kasv.babcak.civicease.services.ReportService;

import java.util.List;

/**
 * REST Controller for managing municipal reports (City Issues, Complaints, etc.)
 */
@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:5173") // Allow frontend (Vite + React) to access backend
@Tag(name = "Report Controller", description = "API endpoints for managing municipal reports")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportService reportService;

    // -------------------- GET ALL REPORTS --------------------
    @Operation(summary = "Get all reports", description = "Fetches all reports from the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of reports"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        logger.info("Fetching all reports");
        List<Report> reports = reportRepository.findAll();
        return ResponseEntity.ok(reports);
    }

    // -------------------- GET REPORT BY ID --------------------
    @Operation(summary = "Get report by ID", description = "Retrieve a specific report by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the report"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(
            @Parameter(description = "ID of the report to retrieve") @PathVariable Long id) {
        logger.info("Fetching report with ID: {}", id);
        return reportRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Report not found with ID: " + id));
    }

    // -------------------- CREATE REPORT --------------------
    @Operation(summary = "Create a new report", description = "Creates a new municipal report in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Report created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody CreateReportRequest request) {
        try {
            logger.info("Creating new report: {}", request);
            Report savedReport = reportService.createReport(request);
            return new ResponseEntity<>(savedReport, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating report: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error creating report: " + e.getMessage());
        }
    }

    // -------------------- UPDATE REPORT --------------------
    @Operation(summary = "Update existing report", description = "Updates report details by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Report updated successfully"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(
            @Parameter(description = "ID of the report to update") @PathVariable Long id,
            @RequestBody UpdateReportRequest request) {
        try {
            logger.info("Updating report with ID: {}", id);
            Report updatedReport = reportService.updateReport(id, request);
            return ResponseEntity.ok(updatedReport);
        } catch (EntityNotFoundException e) {
            logger.error("Report not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating report: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error updating report: " + e.getMessage());
        }
    }

    // -------------------- DELETE REPORT --------------------
    @Operation(summary = "Delete a report", description = "Deletes a report by ID from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Report deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(
            @Parameter(description = "ID of the report to delete") @PathVariable Long id) {
        logger.info("Deleting report with ID: {}", id);
        if (!reportRepository.existsById(id)) {
            logger.warn("Report not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report not found with ID: " + id);
        }
        reportRepository.deleteById(id);
        logger.info("Report deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    // -------------------- ASSIGN TECHNICIAN --------------------
    @Operation(summary = "Assign a technician", description = "Assign a technician to a specific report")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Technician assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Report or Technician not found")
    })
    @PostMapping("/{reportId}/assign-technician/{technicianId}")
    public ResponseEntity<?> assignTechnician(
            @Parameter(description = "ID of the report") @PathVariable Long reportId,
            @Parameter(description = "ID of the technician") @PathVariable Long technicianId) {
        try {
            logger.info("Assigning technician {} to report {}", technicianId, reportId);
            Report updatedReport = reportService.assignTechnician(reportId, technicianId);
            return ResponseEntity.ok(updatedReport);
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error assigning technician: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error assigning technician: " + e.getMessage());
        }
    }
}
