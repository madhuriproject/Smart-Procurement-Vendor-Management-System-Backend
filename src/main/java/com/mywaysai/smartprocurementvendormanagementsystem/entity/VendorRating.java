package com.mywaysai.smartprocurementvendormanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class VendorRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int qualityScore;
    private int deliveryScore;
    private int priceScore;

    @ManyToOne
    private Vendor vendor;

    @ManyToOne
    private User ratedBy;

    private String comments;
    private boolean isAdminRating;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime ratedAt;
}
