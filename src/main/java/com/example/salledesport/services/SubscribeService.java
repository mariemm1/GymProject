package com.example.salledesport.services;

import com.example.salledesport.model.Abonnement;
import com.example.salledesport.model.Client;
import com.example.salledesport.model.Subscribe;

import com.example.salledesport.repositories.AbonnementRepository;

import com.example.salledesport.repositories.ClientRepository;
import com.example.salledesport.repositories.SubscribeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;


@Service
public class SubscribeService {

    @Autowired
    private SubscribeRepository subscribeRepository;

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Autowired
    private ClientRepository clientRepository;



    public Subscribe AddSubscribe(Subscribe subscribe) {
        // Fetch Abonnement details
        Abonnement abonnement = abonnementRepository.findById(subscribe.getAbonnement().getId())
                .orElseThrow(() -> new RuntimeException("Abonnement not found"));

        // Fetch Client details
        Client client = clientRepository.findById(subscribe.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Ensure startDate is set
        if (subscribe.getStartDate() == null) {
            subscribe.setStartDate(LocalDate.now());
        }

        // Calculate endDate based on Abonnement duration
        if (abonnement.getDurationInMonths() > 0) {
            subscribe.setEndDate(subscribe.getStartDate().plusMonths(abonnement.getDurationInMonths()));
        } else {
            throw new IllegalArgumentException("Abonnement duration must be greater than 0.");
        }

        // Set related entities
        subscribe.setAbonnement(abonnement);
        subscribe.setClient(client);

        // Save the subscription
        return subscribeRepository.save(subscribe);
    }







    // Add a new subscription
    public Subscribe addSubscribe2(Subscribe subscribe) {
        // Fetch Abonnement details
        Abonnement abonnement = abonnementRepository.findById(subscribe.getAbonnement().getId())
                .orElseThrow(() -> new RuntimeException("Abonnement not found"));

        // Ensure startDate is set
        if (subscribe.getStartDate() == null) {
            subscribe.setStartDate(LocalDate.now());
        }

        // Calculate endDate based on Abonnement duration
        if (abonnement.getDurationInMonths() > 0) {
            subscribe.setEndDate(subscribe.getStartDate().plusMonths(abonnement.getDurationInMonths()));
        } else {
            throw new IllegalArgumentException("Abonnement duration must be greater than 0.");
        }

        // Set related entities
        subscribe.setAbonnement(abonnement);

        // Save the subscription
        return subscribeRepository.save(subscribe);
    }











    public List<Subscribe> getAllSubscribes() {
        return subscribeRepository.findAll();
    }


    public void deleteSubscribe(Long id) {subscribeRepository.deleteById(id);}


    public Subscribe getSubscribeById(Long id) {return subscribeRepository.findById(id).get();}

    public Subscribe updateSubscribe(Long id, Subscribe subscribe) {
        // Fetch the existing Subscribe object from the database
        Subscribe existingSubscribe = subscribeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscribe with ID " + id + " not found"));

        // Update the fields of the existing Subscribe object
        existingSubscribe.setModePayment(subscribe.getModePayment());
        existingSubscribe.setStartDate(subscribe.getStartDate());
        existingSubscribe.setEndDate(subscribe.getEndDate());

        // If the Abonnement has changed, update it as well
        if (subscribe.getAbonnement() != null) {
            Abonnement abonnement = abonnementRepository.findById(subscribe.getAbonnement().getId())
                    .orElseThrow(() -> new RuntimeException("Abonnement not found"));
            existingSubscribe.setAbonnement(abonnement);
        }

        // Save the updated Subscribe object to the database
        return subscribeRepository.save(existingSubscribe);
    }


    public List<Subscribe> getSubscriptionsByClient(Client client) {
        return subscribeRepository.findByClient(client);
    }


}