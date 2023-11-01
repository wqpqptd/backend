package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

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
    @NotNull
    @Column(name = "date_examination")
    private LocalDate dateExamination;
    @NotNull
    @Column(name = "result_theoretical")
    private Double resultTheoretical;
    @NotNull
    @Column(name = "result_practice")
    private Double resultPractice;
    @NotNull
    @Column(name = "profile_id")
    private int profileId;
    @NotNull
    @Column(name = "profile_nation_id")
    private int nationId;
    @NotNull
    @Column(name = "profile_religion_id")
    private int religionId;
    @NotNull
    @Column(name = "driver_license_id")
    private int driverLicenseId;
    @NotNull
    @Column(name = "examinations_id")
    private int examinationsId;
}
