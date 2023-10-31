package com.example.backend.controllers.Examination;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Examinations;
import com.example.backend.services.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;


@RestController
@RequestMapping("/examination")
public class ExaminationController {
    @Autowired
    private ExaminationService examinationService;

    @PostMapping()
    public Examinations createExamination(@RequestBody Examinations examination) {
        return examinationService.createExamination(examination);
    }
    @PatchMapping("/{id}")
    public Examinations updateExamination(@RequestBody Examinations examination, @PathVariable(name = "id") int id) {
        examination.setId(id);
        return examinationService.updateExamination(examination);
    }

    @GetMapping("/{id}")
    public Examinations findExaminationById(@PathVariable(name = "id") int id) {
        return examinationService.findExaminationById(id);
    }

    @GetMapping()
    public List<Examinations> examinationsList() {
        return examinationService.examinationsList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteExaminationById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(examinationService.deleteExaminationById(id), HttpStatus.OK);
    }
}
