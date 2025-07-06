package com.example.salledesport.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("Client")
public class Client extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    //@JsonManagedReference("client-subs")
    private List<Subscribe> subscriptions = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "client_courses",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Cours> bookedCourses = new ArrayList<>();

    public Client(String first_name, String last_name, String username, String email, String password) {
        super(first_name, last_name, username, email, password);
    }
}
