package sk.kasv.babcak.civicease.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.kasv.babcak.civicease.models.Citizen;
import sk.kasv.babcak.civicease.repositories.CitizenRepository;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    public Citizen createCitizen(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    public Citizen updateCitizen(Long id, Citizen citizenDetails) {
        Citizen existingCitizen = citizenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Citizen not found with id: " + id));

        existingCitizen.setFirstName(citizenDetails.getFirstName());
        existingCitizen.setLastName(citizenDetails.getLastName());
        existingCitizen.setEmail(citizenDetails.getEmail());
        existingCitizen.setPhone(citizenDetails.getPhone());
        existingCitizen.setAddress(citizenDetails.getAddress());

        return citizenRepository.save(existingCitizen);
    }

    public void deleteCitizen(Long id) {
        if (!citizenRepository.existsById(id)) {
            throw new EntityNotFoundException("Citizen not found with id: " + id);
        }
        citizenRepository.deleteById(id);
    }
}
