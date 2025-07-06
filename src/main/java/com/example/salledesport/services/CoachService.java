package com.example.salledesport.services;

import com.example.salledesport.model.Coach;
import com.example.salledesport.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoachService {
    @Autowired
    CoachRepository coachRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public Coach createCoach(Coach coach) {
        // Validate coach fields
        if (coach.getEmail() == null || coach.getPassword() == null) {
            throw new IllegalArgumentException("Email and password are required.");
        }

        // Hash password
        String encodedPassword = passwordEncoder.encode(coach.getPassword());
        coach.setPassword(encodedPassword);

        return coachRepository.save(coach);
    }


    public List<Coach> getAllCoach() {
        return coachRepository.findAll();}


    public Coach getCoachById(long id) {
        Optional <Coach> coach =coachRepository.findById(id);
        return coach.isPresent()?coach.get():null;
    }

    public Coach editCoach (Coach coach){
        return coachRepository.save(coach);
    }

    public void deleteCoachById(Long id){
        coachRepository.deleteById(id);
    }

}