package com.example.backend.dto.request;

import com.example.backend.entities.Examinations;
import com.example.backend.entities.Nation;
import com.example.backend.entities.Religion;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequest {
    private int id;
    private String name;
    private LocalDate dateofbirth;
    private String sex;
    private String idcard;
    private String phone;
    private String image;
    private String note;
    private int nationId;
    private int religionId;
    private String province;
    private String district;
    private String wards;
    private int examinationsId;
}
