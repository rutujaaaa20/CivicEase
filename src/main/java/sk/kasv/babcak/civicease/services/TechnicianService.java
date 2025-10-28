package sk.kasv.babcak.civicease.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.kasv.babcak.civicease.models.Department;
import sk.kasv.babcak.civicease.models.Technician;
import sk.kasv.babcak.civicease.repositories.DepartmentRepository;
import sk.kasv.babcak.civicease.repositories.TechnicianRepository;

@Service
public class TechnicianService {

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public Technician createTechnician(Technician technician) {
        if (technician.getDepartment() == null || technician.getDepartment().getId() == null) {
            throw new IllegalArgumentException("Department ID is required for a new technician.");
        }

        Department department = departmentRepository.findById(technician.getDepartment().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Department not found with id: " + technician.getDepartment().getId()
                ));

        technician.setDepartment(department);
        return technicianRepository.save(technician);
    }

    @Transactional
    public Technician updateTechnician(Long id, Technician technicianDetails) {
        Technician existingTechnician = technicianRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Technician not found with id: " + id));

        existingTechnician.setFirstName(technicianDetails.getFirstName());
        existingTechnician.setLastName(technicianDetails.getLastName());
        existingTechnician.setEmail(technicianDetails.getEmail());
        existingTechnician.setPhone(technicianDetails.getPhone());
        existingTechnician.setSpecialization(technicianDetails.getSpecialization());
        existingTechnician.setAvailable(technicianDetails.getAvailable());

        if (technicianDetails.getDepartment() != null && technicianDetails.getDepartment().getId() != null) {
            Department department = departmentRepository.findById(technicianDetails.getDepartment().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Department not found with id: " + technicianDetails.getDepartment().getId()
                    ));
            existingTechnician.setDepartment(department);
        }

        return technicianRepository.save(existingTechnician);
    }

    public void deleteTechnician(Long id) {
        if (!technicianRepository.existsById(id)) {
            throw new EntityNotFoundException("Technician not found with id: " + id);
        }
        technicianRepository.deleteById(id);
    }
}
