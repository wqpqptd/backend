package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Detail_Profile")
public class DetailProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date_examination")
    private LocalDate dateExamination;
    @Column(name = "result_theoretical")
    private Double resultTheoretical;
    @Column(name = "result_practice")
    private Double resultPractice;
    @Column(name = "profile_id")
    private int profileId;
    @Column(name = "profile_nation_id")
    private int nationId;
    @Column(name = "profile_religion_id")
    private int religionId;
    @Column(name = "driver_license_id")
    private int driverLicenseId;
    @Column(name = "examinations_id")
    private int examinationsId;
}
