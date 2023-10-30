package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Driver_License")
public class DriverLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "license_date")
    private LocalDate licenseDate;
    @Column(name = "code")
    private int code;
    @Column(name = "driver_license_class_id")
    private int driverLicenseClassId;
    @Column(name = "driver_license_duration_id")
    private int driverLicenseDurationId;
}
