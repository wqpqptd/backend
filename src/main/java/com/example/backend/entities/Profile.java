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
@Table(name = "Profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @JoinColumn(name = "name")
    private String name;

    @NotNull
    @JoinColumn(name = "date_of_birth")
    private LocalDate dateofbirth;

    @NotNull
    @JoinColumn(name = "sex")
    private String sex;

    @NotNull
    @JoinColumn(name = "id_card")
    private String idcard;

    @NotNull
    @JoinColumn(name = "phone")
    private String phone;

    @NotNull
    @JoinColumn(name = "image")
    private String image;

    @NotNull
    @JoinColumn(name = "note")
    private String note;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nation_id")
    private Nation nationId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "religion_id")
    private Religion religionId;

    @NotNull
    @JoinColumn(name = "province")
    private String province;

    @NotNull
    @JoinColumn(name = "district")
    private String district;

    @NotNull
    @JoinColumn(name = "wards")
    private String wards;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examinations_id")
    private Examinations examinationsId; // Many-to-One relationship with Examinations entity

    public void setNation(Nation nation) {
        this.nationId=nation;
    }

    public void setReligion(Religion religion) {
        this.religionId=religion;
    }

    public void setExaminations(Examinations examinations) {
        this.examinationsId=examinations;
    }
}
