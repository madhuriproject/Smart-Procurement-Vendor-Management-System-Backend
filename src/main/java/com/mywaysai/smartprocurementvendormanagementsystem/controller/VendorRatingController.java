package com.mywaysai.smartprocurementvendormanagementsystem.controller;

import com.mywaysai.smartprocurementvendormanagementsystem.dto.VendorRatingRequest;
import com.mywaysai.smartprocurementvendormanagementsystem.entity.VendorRating;
import com.mywaysai.smartprocurementvendormanagementsystem.service.VendorRatingServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vendor-ratings")
public class VendorRatingController {

    private final VendorRatingServiceImpl service;

    public VendorRatingController(VendorRatingServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public VendorRating create(@RequestBody VendorRatingRequest request) {
        return service.createRating(request);
    }

    @GetMapping
    public List<VendorRating> getAll() {
        return service.getAllRatings();
    }

    @GetMapping("/{id}")
    public VendorRating getById(@PathVariable Long id) {
        return service.getRatingById(id);
    }

    // Admin-specific endpoints
    @PostMapping("/admin/rate")
    public ResponseEntity<?> adminRateVendor(@RequestBody VendorRatingRequest request) {
        System.out.println(request.isAdminRating());
        try {
//            request.setAdminRating(true);
            if (!request.isAdminRating()) {

                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Admin rating flag must be set to true 🔴"));
            }
            VendorRating rating = service.createAdminRating(request);
            System.out.println();
            return ResponseEntity.ok(rating);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/admin/ratings")
    public ResponseEntity<List<VendorRating>> getAdminRatings() {
        return ResponseEntity.ok(service.getAdminRatings());
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorRating>> getRatingsByVendor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(service.getRatingsByVendor(vendorId));
    }

    @GetMapping("/vendor/{vendorId}/average")
    public ResponseEntity<?> getAverageScore(@PathVariable Long vendorId) {
        double average = service.calculateAverageScore(vendorId);
        return ResponseEntity.ok(Map.of(
            "vendorId", vendorId,
            "averageScore", average
        ));
    }
}

