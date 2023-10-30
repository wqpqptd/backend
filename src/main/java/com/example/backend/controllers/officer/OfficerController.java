package com.example.backend.controllers.officer;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Officer;
import com.example.backend.services.OfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("officer")
public class OfficerController {
    @Autowired
    private OfficerService officerService;

    @PostMapping("")
    public Officer createOfficer(@RequestBody Officer officer) {
        return officerService.createOfficer(officer);
    }

    @PatchMapping("/{id}")
    public Optional<Officer> updateOfficer(@RequestBody Officer officer, @PathVariable(name = "id") int id) {
        return officerService.updateOfficer(officer, id);
    }

    @GetMapping("")
    public List<Officer> officerList() {
        return officerService.officerList();
    }

    @GetMapping("/{id}")
    public Optional<Officer> findOfficerById(@PathVariable(name = "id") int id) {
        return officerService.findOfficerById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteOfficerById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(officerService.deleteProfileById(id), HttpStatus.OK);
    }
}
