package sk.kasv.babcak.civicease.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.kasv.babcak.civicease.dto.CreateReportRequest;
import sk.kasv.babcak.civicease.dto.UpdateReportRequest;
import sk.kasv.babcak.civicease.models.Citizen;
import sk.kasv.babcak.civicease.models.Department;
import sk.kasv.babcak.civicease.models.Report;
import sk.kasv.babcak.civicease.models.Report.ReportStatus;
import sk.kasv.babcak.civicease.models.Technician;
import sk.kasv.babcak.civicease.repositories.CitizenRepository;
import sk.kasv.babcak.civicease.repositories.DepartmentRepository;
import sk.kasv.babcak.civicease.repositories.ReportRepository;
import sk.kasv.babcak.civicease.repositories.TechnicianRepository;

@Service
public class ReportService {
    @Autowired 
    private ReportRepository reportRepository;

    @Autowired 
    private CitizenRepository citizenRepository;

    @Autowired 
    private DepartmentRepository departmentRepository;

    @Autowired 
    private TechnicianRepository technicianRepository;

    @Transactional
    public Report createReport(CreateReportRequest request) {
        Citizen citizen = citizenRepository.findById(request.getCitizenId())
                .orElseThrow(() -> new EntityNotFoundException("Citizen not found with id: " + request.getCitizenId()));

        Report report = new Report();
        report.setTitle(request.getTitle());
        report.setDescription(request.getDescription());
        report.setLocation(request.getLocation());
        report.setCitizen(citizen);

        if (request.getPriority() != null) {
            report.setPriority(request.getPriority());
        }

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + request.getDepartmentId()));
            report.setDepartment(department);
        }

        return reportRepository.save(report);
    }

    @Transactional
    public Report updateReport(Long id, UpdateReportRequest request) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Report not found with id: " + id));

        if (request.getTitle() != null) report.setTitle(request.getTitle());
        if (request.getDescription() != null) report.setDescription(request.getDescription());
        if (request.getLocation() != null) report.setLocation(request.getLocation());
        if (request.getStatus() != null) report.setStatus(request.getStatus());
        if (request.getPriority() != null) report.setPriority(request.getPriority());

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + request.getDepartmentId()));
            report.setDepartment(department);
        }

        return reportRepository.save(report);
    }

    @Transactional
    public Report assignTechnician(Long reportId, Long technicianId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Report not found with id: " + reportId));
        Technician technician = technicianRepository.findById(technicianId)
                .orElseThrow(() -> new EntityNotFoundException("Technician not found with id: " + technicianId));

        report.getAssignedTechnicians().add(technician);
        report.setStatus(ReportStatus.IN_PROGRESS);

        return reportRepository.save(report);
    }
}
