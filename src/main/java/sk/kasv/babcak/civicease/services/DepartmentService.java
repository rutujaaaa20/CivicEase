package sk.kasv.babcak.civicease.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.kasv.babcak.civicease.models.Department;
import sk.kasv.babcak.civicease.repositories.DepartmentRepository;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));

        existingDepartment.setName(departmentDetails.getName());
        existingDepartment.setDescription(departmentDetails.getDescription());
        existingDepartment.setContactEmail(departmentDetails.getContactEmail());
        existingDepartment.setContactPhone(departmentDetails.getContactPhone());

        return departmentRepository.save(existingDepartment);
    }

    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
