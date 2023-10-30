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
@Table(name = "Examinations")
public class Examinations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "examinations_name")
    private String examinationsName;
    @Column(name = "examinations_date")
    private LocalDate examinationsDate;
    @Column(name = "examinations_description")
    private String examinationsDescription;
}
