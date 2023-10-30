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
@Table(name = "Profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JoinColumn(name = "name")
    private String name;
    @JoinColumn(name = "date_of_birth")
    private LocalDate dateofbirth;
    @JoinColumn(name = "sex")
    private String sex;
    @JoinColumn(name = "id_card")
    private String idcard;
    @JoinColumn(name = "phone")
    private String phone;
    @JoinColumn(name = "image")
    private String image;
    @JoinColumn(name = "note")
    private String note;
    @JoinColumn(name = "nation_id")
    private int nationId;
    @JoinColumn(name = "religion_id")
    private int religionId;
    @JoinColumn(name = "province")
    private String province;
    @JoinColumn(name = "district")
    private String district;
    @JoinColumn(name = "wards")
    private String wards;
    @JoinColumn(name = "examinations_id")
    private int examinationsId;
}
