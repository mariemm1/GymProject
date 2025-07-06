package com.example.salledesport.model;

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
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String packName;
    private int nbrCours;
    private int durationInMonths;
    private double total_price;


    @Transient
    private List<String> coursNames = new ArrayList<>();


    // One-to-Many relationship with Subscribe
    @OneToMany(mappedBy = "abonnement", cascade = CascadeType.ALL)
    private List<Subscribe> subscriptions = new ArrayList<>();

    // Many-to-Many relationship with Cours
    @ManyToMany
    @JoinTable(
            name = "abonnement_cours", // Join table name
            joinColumns = @JoinColumn(name = "abonnement_id"), // Foreign key to Abonnement
            inverseJoinColumns = @JoinColumn(name = "cours_id") // Foreign key to Cours
    )
    private List<Cours> cours = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public int getNbrCours() {
        return nbrCours;
    }

    public void setNbrCours(int nbrCours) {
        this.nbrCours = nbrCours;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(int durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public List<String> getCoursNames() {
        return coursNames;
    }

    public void setCoursNames(List<String> coursNames) {
        this.coursNames = coursNames;
    }

    public List<Subscribe> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscribe> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }

    public void setCoursFromNames(List<Cours> availableCourses) {
        this.cours = availableCourses;
        calculateDetails(); // Recalculate number of courses and total price after setting the courses
    }

    public void calculateDetails() {
        // Calculate number of courses (based on the size of the cours list)
        this.nbrCours = cours.size();

        // Ensure durationInMonths is a valid number
        int months = this.durationInMonths;

        // Calculate the total price (sum of the prices of all courses * the number of months)
        this.total_price = cours.stream()
                .mapToDouble(c -> c.getPrice() * months) // Multiply course price by the duration
                .sum();
    }




}
