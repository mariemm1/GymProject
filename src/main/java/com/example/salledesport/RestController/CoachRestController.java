package com.example.salledesport.RestController;

import com.example.salledesport.model.Coach;
import com.example.salledesport.services.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Coach")
public class CoachRestController {

    @Autowired
    private CoachService coachService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping
    public List<Coach> getAllCoach(){
        return coachService.getAllCoach();
    }

    @GetMapping("/{id}")
    public Coach getGoachById(@PathVariable Long id){
        return coachService.getCoachById(id);
    }

    @PostMapping("/addCoach")
    public ResponseEntity<?> addCoach(@RequestBody Coach coach) {
        try {
            // Hash the password before saving
            String hashedPassword = passwordEncoder.encode(coach.getPassword());
            coach.setPassword(hashedPassword);

            // Save coach
            Coach createdCoach = coachService.createCoach(coach);

            return ResponseEntity.ok().body("Coach created successfully with ID: " + createdCoach.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating coach: " + e.getMessage());
        }
    }




//    @PutMapping
//    public Coach updateCoach (Coach coach){
//        return  coachService.editCoach(coach);
//    }

    @PutMapping
    public Coach updateCoach(@RequestBody Coach coach) {
        return coachService.editCoach(coach);
    }


    @DeleteMapping("/{id}")
    public void deleteCoach(@PathVariable Long id){
        coachService.deleteCoachById(id);
    }


}
