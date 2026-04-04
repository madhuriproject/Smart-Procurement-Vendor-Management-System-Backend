package com.mywaysai.smartprocurementvendormanagementsystem.service;

import com.mywaysai.smartprocurementvendormanagementsystem.dto.VendorRatingRequest;
import com.mywaysai.smartprocurementvendormanagementsystem.entity.VendorRating;

import java.util.List;

public interface VendorRatingService {
    VendorRating createRating(VendorRatingRequest request);

    VendorRating createAdminRating(VendorRatingRequest request);

    List<VendorRating> getAdminRatings();

    List<VendorRating> getRatingsByVendor(Long vendorId);

    double calculateAverageScore(Long vendorId);

    Boolean deleteRating(Long vendorId);

    List<VendorRating> getAllRatings();

    VendorRating getRatingById(Long id);

    VendorRating rate(VendorRating rating);


}

