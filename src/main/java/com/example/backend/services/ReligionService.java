package com.example.backend.services;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Religion;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.ReligionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReligionService {
    @Autowired
    private ReligionRepository religionRepository;

    public Religion createReligion(Religion religion) {
        Religion result;
        try {
            result = religionRepository.save(religion);
        } catch (Exception e) {
            throw new IllegalArgumentException(CustomErrorMessage.FAILED_CREATE);
        }
        return religion;
    }

    public Optional<Religion> updateReligion(Religion religion, int id) {
        var update = religionRepository.findById(id).map(result -> {
            result.setReligionName(religion.getReligionName());
            return religionRepository.save(result);
        });
        if(update.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.FAILED_UPDATE);
        }
        return update;
    }

    public List<Religion> religionList() {
        var result = religionRepository.findAll();
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_GET_ALL_LIST);
        }
        return result;
    }

    public Optional<Religion> findReligionById(int id) {
        var result = religionRepository.findById(id);
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public ResponseMessage deleteReligionById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = religionRepository.findById(id);
        if(isResult.isPresent()){
            religionRepository.deleteById(id);
            message.setMessage("delete religion by id successfully!");
        }else{
            message.setMessage("delete religion by id failed!");
        }
        return message;
    }
}
