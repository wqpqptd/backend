package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

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
    @NotNull
    @Column(name = "license_date")
    private LocalDate licenseDate;
    @NotNull
    @Column(name = "code")
    private int code;
    @NotNull
    @Column(name = "driver_license_class_id")
    private int driverLicenseClassId;
    @NotNull
    @Column(name = "driver_license_duration_id")
    private int driverLicenseDurationId;
}
