
package com.example.salledesport.RestController;

import com.example.salledesport.model.Abonnement;
import com.example.salledesport.model.Cours;
import com.example.salledesport.services.AbonnementService;
import com.example.salledesport.services.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Abonnement")
public class AbonnementRestController {

    @Autowired
    private AbonnementService abonnementService;

    @Autowired
    private CoursService coursService;

    // Create a new abonnement
    @PostMapping("/addAbonnement")
    public ResponseEntity<?> addAbonnement(@RequestBody Abonnement abonnement) {
        if (abonnement.getCoursNames() == null || abonnement.getCoursNames().isEmpty()) {
            return new ResponseEntity<>("Course names must not be empty", HttpStatus.BAD_REQUEST);
        }

        // Fetch the available courses from the database
        List<Cours> availableCourses = coursService.findCoursesByNames(abonnement.getCoursNames());

        if (availableCourses.isEmpty()) {
            return new ResponseEntity<>("Invalid course names or no matching courses found", HttpStatus.BAD_REQUEST);
        }

        // Set courses to the abonnement and calculate the details
        abonnement.setCoursFromNames(availableCourses);

        // Save the abonnement to the database
        Abonnement createdAbonnement = abonnementService.createAbonnement(abonnement);

        return new ResponseEntity<>(createdAbonnement, HttpStatus.CREATED);
    }

    // Get all abonnements
    @GetMapping("/allAbonnements")
    public ResponseEntity<List<Abonnement>> getAllAbonnements() {
        List<Abonnement> abonnements = abonnementService.getAllAbonnements();
        return new ResponseEntity<>(abonnements, HttpStatus.OK);
    }

    // Get a specific abonnement by ID
    @GetMapping("/abonnement/{id}")
    public ResponseEntity<Abonnement> getAbonnementById(@PathVariable Long id) {
        Abonnement abonnement = abonnementService.getAbonnementById(id);
        if (abonnement != null) {
            return new ResponseEntity<>(abonnement, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update an existing abonnement
    @PutMapping("/updateAbonnement/{id}")
    public ResponseEntity<Abonnement> updateAbonnement(@PathVariable Long id, @RequestBody Abonnement abonnement) {
        System.out.println("Received request to update abonnement with ID: " + id);
        Abonnement updatedAbonnement = abonnementService.updateAbonnement(id, abonnement);
        if (updatedAbonnement != null) {
            return new ResponseEntity<>(updatedAbonnement, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    // Delete an abonnement
//    @DeleteMapping("/deleteAbonnement/{id}")
//    public ResponseEntity<Void> deleteAbonnement(@PathVariable Long id) {
//        abonnementService.deleteAbonnement(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @DeleteMapping("/deleteAbonnement/{id}")
    public ResponseEntity<?> deleteAbonnement(@PathVariable Long id) {
        abonnementService.deleteAbonnement(id);
        return ResponseEntity.ok("Abonnement deleted successfully");
    }

}

