package com.example.salledesport.RestController;

import com.example.salledesport.model.Cours;
import com.example.salledesport.services.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cours")
public class CoursRestController {

    @Autowired
    private CoursService coursService;

    @GetMapping
    public List<Cours> getAllCours(){
        return coursService.getAllCours();
    }

    @GetMapping("/{id}")
    public Cours getCoursById(@PathVariable Long id){
        return coursService.getCoursById(id);
    }

//    @PostMapping("/{addCours}")
//    public Cours addCours(@RequestBody Cours cours){
//        return coursService.createCours(cours);
//    }

    @PostMapping("/addCours")
    public ResponseEntity<Cours> addCours(@RequestBody Cours cours) {
        Cours createdCours = coursService.addCoursWithCoach(cours);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCours);
    }




//    @PutMapping
//    public Cours updateCours(Cours cours){
//        return coursService.updateCours(cours);
//    }

    @PutMapping
    public Cours updateCours(@RequestBody Cours cours) {
        return coursService.updateCours(cours);
    }


    @DeleteMapping("/{id}")
    public void deleteCours(@PathVariable Long id){
        coursService.deleteCours(id);
    }

}
