package com.example.backend.services;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.DriverLicenseClass;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.DriverLicenseClassRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DriverLicenseClassService {
    @Autowired
    private DriverLicenseClassRepository driverLicenseClassRepository;

    public DriverLicenseClass createDriverLicenseClass(DriverLicenseClass driverLicenseClass) {
        DriverLicenseClass create;
        try {
            create = driverLicenseClassRepository.save(driverLicenseClass);
        }catch (Exception e) {
            throw new IllegalArgumentException(CustomErrorMessage.FAILED_CREATE);
        }
        return create;
    }

    public Optional<DriverLicenseClass> updateDriverLicenseClass(DriverLicenseClass driverLicenseClass) {
        var result = driverLicenseClassRepository.findById(driverLicenseClass.getId()).map(update -> {
            update.setDriverLicenseClassName(driverLicenseClass.getDriverLicenseClassName());
            return driverLicenseClassRepository.save(update);
        });
        if (result.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.FAILED_UPDATE);
        }
        return result;
    }

    public List<DriverLicenseClass> DriverLicenseClassList() {
        var result = driverLicenseClassRepository.findAll();
        return result;
    }

    public Optional<DriverLicenseClass> findDriverLicenseClassById(int id) {
        var result = driverLicenseClassRepository.findById(id);
        if (result.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public ResponseMessage deleteDriverLicenseClassById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = driverLicenseClassRepository.findById(id);
        if(isResult.isPresent()){
            driverLicenseClassRepository.deleteById(id);
            message.setMessage("delete driver license class by id successfully!");
        }else{
            message.setMessage("delete driver license class by id failed!");
        }
        return message;
    }
}
