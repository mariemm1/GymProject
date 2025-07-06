package com.example.salledesport.services;

import com.example.salledesport.model.Abonnement;
import com.example.salledesport.model.Cours;
import com.example.salledesport.repositories.AbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AbonnementService {

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Autowired
    private CoursService coursService;


    // Create a new abonnement
    public Abonnement createAbonnement(Abonnement abonnement) {
        abonnement.calculateDetails(); // Calculate details: nbrCours, total_price
        return abonnementRepository.save(abonnement);
    }

    public List<Abonnement> getAllAbonnements() {
        List<Abonnement> abonnements = abonnementRepository.findAll();

        // Map coursNames for each Abonnement
        for (Abonnement abonnement : abonnements) {
            List<String> coursNames = new ArrayList<>();
            for (Cours cours : abonnement.getCours()) {
                coursNames.add(cours.getName()); // Add course name to the list
            }
            abonnement.setCoursNames(coursNames); // Set coursNames list
        }
        return abonnements;
    }

    // Get an abonnement by ID
    public Abonnement getAbonnementById(Long id) {
        Optional<Abonnement> optionalAbonnement = abonnementRepository.findById(id);
        if (optionalAbonnement.isPresent()) {
            Abonnement abonnement = optionalAbonnement.get();

            // Map coursNames for this specific Abonnement
            List<String> coursNames = new ArrayList<>();
            for (Cours cours : abonnement.getCours()) {
                coursNames.add(cours.getName()); // Add course name to the list
            }
            abonnement.setCoursNames(coursNames); // Set coursNames list

            return abonnement;
        } else {
            return null; // Return null if the abonnement is not found
        }
    }



    // Method to update an existing abonnement
    public Abonnement updateAbonnement(Long id, Abonnement abonnement) {
        // Fetch the existing abonnement by ID
        Abonnement existingAbonnement = abonnementRepository.findById(id).orElse(null);
        if (existingAbonnement == null) {
            return null; // Return null if the abonnement is not found
        }

        // Update the pack name and duration
        existingAbonnement.setPackName(abonnement.getPackName());
        existingAbonnement.setDurationInMonths(abonnement.getDurationInMonths());

        // Fetch courses by names if provided
        if (abonnement.getCoursNames() != null && !abonnement.getCoursNames().isEmpty()) {
            List<Cours> availableCourses = coursService.findCoursesByNames(abonnement.getCoursNames());
            if (availableCourses.isEmpty()) {
                return null; // Return null if no matching courses found
            }
            existingAbonnement.setCours(availableCourses); // Set courses
        }

        // Recalculate the details (nbrCours, totalPrice)
        existingAbonnement.calculateDetails();

        // Save the updated abonnement
        return abonnementRepository.save(existingAbonnement);
    }



    // Delete an abonnement
    public void deleteAbonnement(Long id) {
        abonnementRepository.deleteById(id);
    }


    


}

