package com.example.salledesport.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name; //  Yoga, Pilates, CrossFit

    private double price;

    private String time; // when the class is scheduled (e.g., 9:00 AM - 10:00 AM)

    private String duration; // e.g., 1 hour, 45 minutes

    private long capacity; // Maximum number of clients allowed in the class

    private long enrolledClients = 0; // Track the current enrollment

    private boolean availability_status = true; // Indicates if the class is fully booked or available for booking


    @ManyToOne
    @JoinColumn(name = "coach_id")
    //@JsonBackReference("cours-coach")
    private Coach coach;

    // Many-to-Many relationship with Abonnement
    @ManyToMany(mappedBy = "cours")
    private List<Abonnement> abonnements = new ArrayList<>();


    @ManyToMany(mappedBy = "bookedCourses")
    private List<Client> clients = new ArrayList<>();

    public boolean isAvailable() {
        return capacity > 0;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public long getEnrolledClients() {
        return enrolledClients;
    }

    public void setEnrolledClients(long enrolledClients) {
        this.enrolledClients = enrolledClients;
    }

    public boolean isAvailability_status() {
        return availability_status;
    }

    public void setAvailability_status(boolean availability_status) {
        this.availability_status = availability_status;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public List<Abonnement> getAbonnements() {
        return abonnements;
    }

    public void setAbonnements(List<Abonnement> abonnements) {
        this.abonnements = abonnements;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void bookClient(Client client) {
        if (clients.contains(client)) {
            throw new RuntimeException("Client has already booked this course!");
        }

        if (isAvailable()) {
            enrolledClients++;  // Increment the enrolled clients count
            capacity--;          // Decrease the remaining capacity
            clients.add(client); // Add the client to the course's booked clients list
            updateAvailabilityStatus(); // Update availability status
            System.out.println("Booking success: " + name + ", enrolled clients: " + enrolledClients + ", capacity: " + capacity); // Debugging log
        } else {
            throw new RuntimeException("The course is fully booked!");
        }
    }

    private void updateAvailabilityStatus() {
        this.availability_status = capacity > 0;
    }

   /* public boolean isAvailable() {
        return capacity > enrolledClients;
    }


    public void bookClient(Client client) {
        if (clients.contains(client)) {
            throw new RuntimeException("Client has already booked this course!");
        }

        if (isAvailable()) {
            enrolledClients++;  // Increment the enrolled clients count
            capacity--;          // Decrease the remaining capacity
            clients.add(client); // Add the client to the course's booked clients list
            updateAvailabilityStatus(); // Update availability status
        } else {
            throw new RuntimeException("The course is fully booked!");
        }
    }

    private void updateAvailabilityStatus() {
        this.availability_status = capacity > enrolledClients;
    }*/



}
