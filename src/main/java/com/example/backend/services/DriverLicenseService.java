package com.example.backend.services;

import com.example.backend.dto.response.DriverLicenseResponse;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.DriverLicense;
import com.example.backend.entities.DriverLicenseClass;
import com.example.backend.entities.DriverLicenseDuration;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.DriverLicenseClassRepository;
import com.example.backend.repositories.DriverLicenseDurationRepository;
import com.example.backend.repositories.DriverLicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DriverLicenseService {
    @Autowired
    private DriverLicenseRepository driverLicenseRepository;
    @Autowired
    private DriverLicenseClassRepository driverLicenseClassRepository ;
    @Autowired
    private DriverLicenseDurationRepository driverLicenseDurationRepository;

    public DriverLicense createDriverLicense(DriverLicense driverLicense) {
        DriverLicense result;
        try {
            result = driverLicenseRepository.save(driverLicense);
        } catch (Exception e) {
            throw new IllegalArgumentException(CustomErrorMessage.FAILED_CREATE);
        }
        return result;
    }

    public Optional<DriverLicense> updateDriverLicense(DriverLicense driverLicense) {
        var result = driverLicenseRepository.findById(driverLicense.getId()).map(update -> {
            update.setLicenseDate(driverLicense.getLicenseDate());
            update.setCode(driverLicense.getCode());
            update.setDriverLicenseClassId(driverLicense.getDriverLicenseClassId());
            update.setDriverLicenseDurationId(driverLicense.getDriverLicenseDurationId());
            return driverLicenseRepository.save(update);
        });
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.FAILED_UPDATE);
        }
        return result;
    }

    public List<DriverLicenseResponse> DriverLicenseList() {
        Map<Integer, String> className = driverLicenseClassRepository.findAll()
                .stream()
                .collect(Collectors.toMap(DriverLicenseClass::getId, DriverLicenseClass::getDriverLicenseClassName));

        Map<Integer, String> durationName = driverLicenseDurationRepository.findAll()
                .stream()
                .collect(Collectors.toMap(DriverLicenseDuration::getId, DriverLicenseDuration::getDuration));

        return driverLicenseRepository.findAll()
                .stream()
                .map(tmp -> {
                    DriverLicenseResponse driverLicenseResponse = new DriverLicenseResponse();
                    driverLicenseResponse.setId(tmp.getId());
                    driverLicenseResponse.setLicenseDate(tmp.getLicenseDate());
                    driverLicenseResponse.setCode(tmp.getCode());
                    driverLicenseResponse.setDriverLicenseClassName(className.get(tmp.getDriverLicenseClassId()));
                    driverLicenseResponse.setDriverLicenseDurationName(durationName.get(tmp.getDriverLicenseDurationId()));
                    return driverLicenseResponse;
                })
                .collect(Collectors.toList());

    }

    public Optional<DriverLicenseResponse> findDriverLicenseById(int id) {
        var result = driverLicenseRepository.findById(id).map(driverLicense -> {
            String driverLicenseClassName = driverLicenseClassRepository.findById(driverLicense.getDriverLicenseClassId()).map(DriverLicenseClass::getDriverLicenseClassName)
                    .orElse(null);

            String durationName = driverLicenseDurationRepository.findById(driverLicense.getDriverLicenseDurationId()).map(DriverLicenseDuration::getDuration)
                    .orElse(null);

            DriverLicenseResponse driverLicenseResponse = new DriverLicenseResponse();
            driverLicenseResponse.setId(driverLicense.getId());
            driverLicenseResponse.setLicenseDate(driverLicense.getLicenseDate());
            driverLicenseResponse.setCode(driverLicense.getCode());
            driverLicenseResponse.setDriverLicenseClassName(driverLicenseClassName);
            driverLicenseResponse.setDriverLicenseDurationName(durationName);
            return driverLicenseResponse;
        });
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }


    public ResponseMessage deleteDriverLicenseClassById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = driverLicenseRepository.findById(id);
        if(isResult.isPresent()){
            driverLicenseRepository.deleteById(id);
            message.setMessage("delete driver license by id successfully!");
        }else{
            message.setMessage("delete driver license by id failed!");
        }
        return message;
    }
}
