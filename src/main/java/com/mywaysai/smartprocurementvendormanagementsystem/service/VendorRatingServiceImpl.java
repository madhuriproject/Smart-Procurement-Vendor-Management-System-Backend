package com.mywaysai.smartprocurementvendormanagementsystem.service;

//import org.springframework.stereotype.Service;
//
//import com.mywaysai.smartprocurementvendormanagementsystem.entity.VendorRating;
//import com.mywaysai.smartprocurementvendormanagementsystem.repository.VendorRatingRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class VendorRatingServiceImpl implements VendorRatingService {
//
//    private final VendorRatingRepository repository;
//
//    public VendorRating rate(VendorRating rating){
//        return repository.save(rating);
//    }
//
import com.mywaysai.smartprocurementvendormanagementsystem.dto.VendorRatingRequest;
import com.mywaysai.smartprocurementvendormanagementsystem.entity.User;
import com.mywaysai.smartprocurementvendormanagementsystem.entity.Vendor;
import com.mywaysai.smartprocurementvendormanagementsystem.entity.VendorRating;
import com.mywaysai.smartprocurementvendormanagementsystem.repository.UserRepository;
import com.mywaysai.smartprocurementvendormanagementsystem.repository.VendorRatingRepository;
import com.mywaysai.smartprocurementvendormanagementsystem.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

    @Service
    public class VendorRatingServiceImpl implements VendorRatingService {

        private final VendorRatingRepository ratingRepository;
        private final VendorRepository vendorRepository;
        private final UserRepository userRepository;

        public VendorRatingServiceImpl(VendorRatingRepository ratingRepository,
                                       VendorRepository vendorRepository,
                                       UserRepository userRepository) {
            this.ratingRepository = ratingRepository;
            this.vendorRepository = vendorRepository;
            this.userRepository = userRepository;
        }

        public Long getVendorId(String email){
            Vendor v = vendorRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Vendor not found with email: " + email));
            return v.getId();
        }

        @Override
        public VendorRating createRating(VendorRatingRequest request) {

            Vendor vendor = vendorRepository.findById(request.getVendorId())
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));

            VendorRating rating = new VendorRating();
            rating.setVendor(vendor);
            rating.setQualityScore(request.getQualityScore());
            rating.setDeliveryScore(request.getDeliveryScore());
            rating.setPriceScore(request.getPriceScore());
            rating.setComments(request.getComments());
            rating.setAdminRating(request.isAdminRating());
            rating.setRatedAt(LocalDateTime.now());

            if (request.getRatedByUserId() != null) {
                User user = userRepository.findById(request.getRatedByUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                rating.setRatedBy(user);
            }

            return ratingRepository.save(rating);
        }

        @Override
        public VendorRating createAdminRating(VendorRatingRequest request) {
            if (!request.isAdminRating()) {
                throw new RuntimeException("This must be marked as admin rating");
            }

            Vendor vendor = vendorRepository.findById(request.getVendorId())
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));

            User user = userRepository.findById(request.getRatedByUserId())
                    .orElseThrow(() -> new RuntimeException("Admin user not found"));

            VendorRating rating = new VendorRating();
            rating.setVendor(vendor);
            rating.setQualityScore(request.getQualityScore());
            rating.setDeliveryScore(request.getDeliveryScore());
            rating.setPriceScore(request.getPriceScore());
            rating.setComments(request.getComments());
            rating.setAdminRating(true);
            rating.setRatedBy(user);
            rating.setRatedAt(LocalDateTime.now());

            return ratingRepository.save(rating);
        }

        @Override
        public List<VendorRating> getAdminRatings() {
            return ratingRepository.findAll().stream()
                    .filter(VendorRating::isAdminRating)
                    .toList();
        }

        @Override
        public List<VendorRating> getRatingsByVendor(Long vendorId) {
            return ratingRepository.findAll().stream()
                    .filter(r -> r.getVendor().getId().equals(vendorId))
                    .toList();
        }

        @Override
        public double calculateAverageScore(Long vendorId) {
            List<VendorRating> ratings = getRatingsByVendor(vendorId);
            if (ratings.isEmpty()) return 0.0;
            return ratings.stream()
                    .mapToDouble(r -> (r.getQualityScore() + r.getDeliveryScore() + r.getPriceScore()) / 3.0)
                    .average()
                    .orElse(0.0);
        }

        @Override
        public Boolean deleteRating(Long vendorId){
            List<VendorRating> ratings = getRatingsByVendor(vendorId);
            if(!ratings.isEmpty()){
                for(VendorRating r : ratings){
                    ratingRepository.delete(r);
                }
                return true;
            }

            return false;
        }

        @Override
        public List<VendorRating> getAllRatings() {
            return ratingRepository.findAll();
        }

        @Override
        public VendorRating getRatingById(Long id) {
            return ratingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Rating not found"));
        }

        @Override
        public VendorRating rate(VendorRating rating) {
            return null;
        }
    }


