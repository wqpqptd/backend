package com.example.backend.services;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Examinations;
import com.example.backend.entities.Nation;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.ExaminationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExaminationService {
    @Autowired
    private ExaminationRepository examinationRepository;

    public Examinations createExamination(Examinations examination) {
        return examinationRepository.save(examination);
    }

    public Examinations updateExamination(Examinations examination) {
        var retrieved = examinationRepository.findById(examination.getId());
        if (retrieved.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }

        return examinationRepository.save(examination);
    }

    public Examinations findExaminationById(int id) {
        var retrieved = examinationRepository.findById(id);
        if (retrieved.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        var result = retrieved.get();
        return result;
    }

    public List<Examinations> examinationsList() {
        var result = examinationRepository.findAll();
        return result;
    }

    public ResponseMessage deleteExaminationById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = examinationRepository.findById(id);
        if(isResult.isPresent()){
            examinationRepository.deleteById(id);
            message.setMessage("delete officer by id successfully!");
        }else{
            message.setMessage("delete officer by id failed!");
        }
        return message;
    }
}
