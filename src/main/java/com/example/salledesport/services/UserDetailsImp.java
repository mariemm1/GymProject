package com.example.salledesport.services;

import com.example.salledesport.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@NoArgsConstructor

public class UserDetailsImp implements UserDetails {

    public static final long serialVersionUID = 1L;

    private Long id;

    private String first_name;

    private String last_name;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImp(Long id,String first_name,String last_name, String username, String email, String password, Collection<? extends GrantedAuthority> authorities){

        this.id=id;
        this.first_name=first_name;
        this.last_name=last_name;
        this.username=username;
        this.email=email;
        this.password=password;
        this.authorities=authorities;

    }

    public static UserDetailsImp build(User user){

        List<GrantedAuthority> authorities1 = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImp(
                user.getId(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities1);
    }


    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // Return the authorities from the user
    }



}
