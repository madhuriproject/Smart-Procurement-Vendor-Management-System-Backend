package com.mywaysai.smartprocurementvendormanagementsystem.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import com.mywaysai.smartprocurementvendormanagementsystem.dto.LoginRequest;
import com.mywaysai.smartprocurementvendormanagementsystem.dto.LoginResponse;
import com.mywaysai.smartprocurementvendormanagementsystem.entity.Department;
import com.mywaysai.smartprocurementvendormanagementsystem.entity.Role;
import com.mywaysai.smartprocurementvendormanagementsystem.entity.Vendor;
import com.mywaysai.smartprocurementvendormanagementsystem.repository.DepartmentRepository;
import com.mywaysai.smartprocurementvendormanagementsystem.repository.RoleRepository;
import com.mywaysai.smartprocurementvendormanagementsystem.repository.VendorRepository;
import org.springframework.stereotype.Service;

import com.mywaysai.smartprocurementvendormanagementsystem.entity.User;
import com.mywaysai.smartprocurementvendormanagementsystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository  departmentRepository;

    private final VendorRepository vendorRepository;
    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User createUser(User user) {

        User user1 = new User();
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setUsername(user.getUsername());
        user1.setActive(true);

        Role role = roleRepository.findById(user.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Role not found"));


        Department department = departmentRepository.findById(user.getDepartment().getId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        user1.setDepartment(department);
        user1.setRole(role);
       // user1.setProfileImage(user.getProfileImage());
        return repository.save(user1);
    }





//    @Override
//    public User createUser(String username,
//                           String email,
//                           String password,
//                           Long roleId,
//                           Long departmentId,
//                           MultipartFile file) throws IOException {
//
//        User user = new User();
//        user.setUsername(username);
//        user.setEmail(email);
//        user.setPassword(password);
//        user.setActive(true);
//
//        //  Role
//        Role role = roleRepository.findById(roleId)
//                .orElseThrow(() -> new RuntimeException("Role not found"));
//
//        //  Department
//        Department department = departmentRepository.findById(departmentId)
//                .orElseThrow(() -> new RuntimeException("Department not found"));
//
//        user.setRole(role);
//        user.setDepartment(department);
//
//        //  Image Upload Logic
//        if (file != null && !file.isEmpty()) {
//
//            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//
//            String uploadDir = "uploads/";
//            File dir = new File(uploadDir);
//            if (!dir.exists()) dir.mkdirs();
//
//            Files.copy(
//                    file.getInputStream(),
//                    Paths.get(uploadDir + fileName),
//                    StandardCopyOption.REPLACE_EXISTING
//            );
//
//            user.setProfileImage(fileName);
//
//        } else {
//            user.setProfileImage("default.png");
//        }
//
//        return repository.save(user);
//    }
    @Override
    public List<User> getAll() {
        return repository.findAll();
    }
//
//    @Override
//    public User getById(Long id) {
//        return repository.findById(id).orElseThrow();
//    }
//
//    @Override
//    public void delete(Long id) {
//        repository.deleteById(id);
//    }


















    @Override
    public LoginResponse login(LoginRequest request) {

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (user.getRole() == null) {
            throw new RuntimeException("User role not assigned");
        }

        //  CHECK ONLY IF ROLE = VENDOR
        if (user.getRole().getRoleName().equalsIgnoreCase("VENDOR")) {

            Optional<Vendor> optionalVendor = vendorRepository.findByEmail(user.getEmail());

            // If vendor record exists → it is outside registered vendor
            if (optionalVendor.isPresent()) {//

                Vendor vendor = optionalVendor.get();

                if (!vendor.isApproved()) {
                    throw new RuntimeException("Vendor not approved by admin yet");
                }
            }

            // If NOT present → it means admin created vendor user
            // So allow login
        }

        return new LoginResponse(

                "dummy-token",
                user.getRole().getRoleName(),

                user.getId()
        );
    }


    @Override
    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User update(Long id, User u) {

        User existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setUsername(u.getUsername());
        existing.setEmail(u.getEmail());
        existing.setPassword(u.getPassword());
        existing.setActive(u.isActive());
        existing.setRole(u.getRole());
        existing.setDepartment(u.getDepartment());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
