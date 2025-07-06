package com.example.salledesport.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)

@Entity
@Getter
@Setter
@DiscriminatorValue("Coach")
public class Coach extends User {

    private String speciality ;
    private String availability;
    private String hourly_rate;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL)
    //@JsonManagedReference("cours-coach")
    private List<Cours> cours = new ArrayList<>();

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(String hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }
}
