package com.example.salledesport.services;

import com.example.salledesport.model.Client;
import com.example.salledesport.model.Cours;
import com.example.salledesport.repositories.ClientRepository;
import com.example.salledesport.repositories.CoursRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CoursRepository coursRepository;

    public List<Client> getAllClient(){
        return clientRepository.findAll();
    }
    public Client getClientById(Long id){
        Optional<Client> client=clientRepository.findById(id);
        return client.isPresent()? client.get():null;
    }

    public Client createClient(Client client){
        return clientRepository.save(client);
    }

    public Client editClient(Client client){
        return clientRepository.save(client);
    }

    public void deleteClientById(Long id){
        clientRepository.deleteById(id);
    }


    @Transactional
    public String bookCours(Long clientId, Long coursId) {
        // Fetch the client and course from the database
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Attempt to book the course for the client
        try {
            // Ensure that the client hasn't already booked this course
            if (client.getBookedCourses().contains(cours)) {
                throw new RuntimeException("Client has already booked this course!");
            }

            // Attempt to book the course
            cours.bookClient(client);  // Updates enrolledClients and capacity

            // Add the course to the client's booked courses list
            client.getBookedCourses().add(cours);

            // Save the client and course to the database
            clientRepository.save(client);
            coursRepository.save(cours);

            return "Client " + client.getId() + " successfully booked the course: " + cours.getName();
        } catch (RuntimeException e) {
            return e.getMessage();  // Return specific error message
        }
    }

}
