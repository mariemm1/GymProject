package com.example.salledesport.services;

import com.example.salledesport.model.Coach;
import com.example.salledesport.model.Cours;
import com.example.salledesport.repositories.CoachRepository;
import com.example.salledesport.repositories.CoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoursService {

    @Autowired
    CoursRepository coursRepository;

    @Autowired
    CoachRepository coachRepository;


    public Cours createCours(Cours cours) {
        return coursRepository.save(cours);}

    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    public Cours getCoursById(long id) {
        Optional<Cours>cours = coursRepository.findById(id);
        return cours.isPresent()?cours.get():null;
    }
    public Cours updateCours(Cours cours) {
        return coursRepository.save(cours);
    }

    public void deleteCours(long id) {
        coursRepository.deleteById(id);
    }

    public List<Cours> findByName(String name) {
        return coursRepository.findByName(name);
    }

    // Method to add a new course with an associated coach by coach's name
//    public String addCoursWithCoach(Cours coursRequest) {
//        // Get the coach's first name from the coursRequest
//        String coachFirstName = coursRequest.getCoach().getFirst_name();
//
//        // Find the coach based on the provided first name
//        Coach coach = coachRepository.findByFirstName(coachFirstName)
//                .orElseThrow(() -> new RuntimeException("Coach with name " + coachFirstName + " not found"));
//
//        // Set the coach for the course
//        coursRequest.setCoach(coach);
//
//        // Save the course
//        coursRepository.save(coursRequest);
//
//        coachRepository.save(coach);
//
//        return "Course added successfully";
//    }

    public Cours addCoursWithCoach(Cours coursRequest) {
        String coachFirstName = coursRequest.getCoach().getFirst_name();

        Coach coach = coachRepository.findByFirstName(coachFirstName)
                .orElseThrow(() -> new RuntimeException("Coach with name " + coachFirstName + " not found"));

        coursRequest.setCoach(coach);

        return coursRepository.save(coursRequest);
    }


    public List<Cours> findCoursesByNames(List<String> courseNames) {
        List<Cours> foundCourses = new ArrayList<>();
        List<String> notFoundCourses = new ArrayList<>();

        for (String courseName : courseNames) {
            List<Cours> courses = coursRepository.findByName(courseName);
            if (!courses.isEmpty()) {
                foundCourses.add(courses.get(0)); // Assuming course names are unique
            } else {
                notFoundCourses.add(courseName);
            }
        }

        if (!notFoundCourses.isEmpty()) {
            throw new IllegalArgumentException("Courses not found: " + String.join(", ", notFoundCourses));
        }

        return foundCourses;
    }
}
