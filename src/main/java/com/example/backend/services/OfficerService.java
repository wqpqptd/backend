package com.example.backend.services;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Officer;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.exceptions.CustomExceptionHandler;
import com.example.backend.repositories.OfficerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OfficerService {
    @Autowired
    private OfficerRepository officerRepository;


    public Officer createOfficer(Officer officer) {
        Officer result;
        try {
            result = officerRepository.save(officer);
        } catch (Exception e) {
            throw new IllegalArgumentException(CustomErrorMessage.FAILED_CREATE);
        }
        return result;
    }

    public Optional<Officer> updateOfficer(Officer officer, int id) {
        var update = officerRepository.findById(id).map(result -> {
                result.setId(id);
                result.setName(officer.getName());
                result.setPhone(officer.getPhone());
                result.setEmail(officer.getEmail());
                return officerRepository.save(result);
            });
        if (update.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.FAILED_UPDATE);
        }
        return update;
    }

    public Optional<Officer> findOfficerById(int id){
        var result = officerRepository.findById(id);
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public List<Officer> officerList() {
        var result = officerRepository.findAll();
        if (result.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.NOT_GET_ALL_LIST);
        }
        return result;
    }

    public ResponseMessage deleteProfileById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = officerRepository.findById(id);
        if(isResult.isPresent()){
            officerRepository.deleteById(id);
            message.setMessage("delete officer by id successfully!");
        }else{
            message.setMessage("delete officer by id failed!");
        }
        return message;
    }
}
