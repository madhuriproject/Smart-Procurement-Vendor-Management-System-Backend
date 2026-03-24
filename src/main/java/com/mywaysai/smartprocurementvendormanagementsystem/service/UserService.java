package com.mywaysai.smartprocurementvendormanagementsystem.service;


import java.io.IOException;
import java.util.List;

import com.mywaysai.smartprocurementvendormanagementsystem.dto.LoginRequest;
import com.mywaysai.smartprocurementvendormanagementsystem.dto.LoginResponse;
import com.mywaysai.smartprocurementvendormanagementsystem.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User save(User user);
    User createUser(User user);

//
//    User createUser(String username,
//                    String email,
//                    String password,
//                    Long roleId,
//                    Long departmentId,
//                    MultipartFile file) throws IOException;

    List<User> getAll();
    User getById(Long id);

    User update(Long id, User u);

    void delete(Long id);

    LoginResponse login(LoginRequest request);

}
