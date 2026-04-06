package com.mywaysai.smartprocurementvendormanagementsystem.dto;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class LoginResponse {
//
//    private String token;
//    private String role;
//
//    public LoginResponse(String token, String role) {
//        this.token = token;
//        this.role = role;
//    }
//
//}


public class LoginResponse {

    private String token;
    private String role;
    private Long id;
    private Long vendorId;

    public LoginResponse(String token, String role, Long id, Long vendorId) {
        this.token = token;
        this.role = role;
        this.id = id;
        this.vendorId = vendorId;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public Long getId() {   //  ADDED
        return id;
    }

    public Long getVendorId() { return vendorId; }
}