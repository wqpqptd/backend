package com.example.backend.dto.request;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DetailProfileUpdateRequest {
    private int id;
    private LocalDate dateExamination;
    private Double resultTheoretical;
    private Double resultPractice;
    private int profileId;
    @Nullable
    private int driverLicenseId;

    public void setDriverLicenseId(Integer o) {
    }
}
