package com.example.salledesport.RestController;

import com.example.salledesport.model.Admin;
import com.example.salledesport.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Admin")
public class AdminRestController {
    @Autowired
    AdminService adminService;

    @GetMapping
    public List<Admin> GetAllAdmin(){
        return adminService.getAllAdmins();
    }

//    @PostMapping
//    public Admin createAdmin(@RequestBody Admin admin){
//        return adminService.createAdmin(admin);
//    }

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin){
        Admin created = adminService.createAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}

