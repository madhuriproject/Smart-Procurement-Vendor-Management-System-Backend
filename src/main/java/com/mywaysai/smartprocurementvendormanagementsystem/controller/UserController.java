package com.mywaysai.smartprocurementvendormanagementsystem.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.mywaysai.smartprocurementvendormanagementsystem.dto.LoginRequest;
import com.mywaysai.smartprocurementvendormanagementsystem.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mywaysai.smartprocurementvendormanagementsystem.entity.User;
import com.mywaysai.smartprocurementvendormanagementsystem.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
//@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public User save(@RequestBody User u){
        return service.createUser(u);
    }













//    @PostMapping
//    public User save(
//            @RequestParam("username") String username,
//            @RequestParam("email") String email,
//            @RequestParam("password") String password,
//            @RequestParam("roleId") Long roleId,
//            @RequestParam("departmentId") Long departmentId,
//            @RequestParam(value = "file", required = false) MultipartFile file
//    ) throws IOException {
//
//        //  Only pass data to service
//        return service.createUser(username, email, password, roleId, departmentId, file);
//    }

    @GetMapping
    public List<User> all(){
        return service.getAll();
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id){
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User u){
        return service.update(id, u);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
