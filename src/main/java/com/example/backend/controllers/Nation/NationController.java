package com.example.backend.controllers.Nation;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Nation;
import com.example.backend.services.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/nation")
public class NationController {
    @Autowired
    private NationService nationService;

    @PostMapping()
    public Nation createNation(@RequestBody Nation nation) {
        return nationService.createNation(nation);
    }

    @PatchMapping("/{id}")
    public Optional<Nation> updateNation(@RequestBody Nation nation, @PathVariable(name = "id") int id) {
        return nationService.updateNation(nation, id);
    }

    @GetMapping("/{id}")
    public Optional<Nation> findNationById(@PathVariable(name = "id") int id) {
        return nationService.findNationById(id);
    }

    @GetMapping()
    public List<Nation> nationList() {
        return nationService.nationList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteNationById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(nationService.deleteNationById(id), HttpStatus.OK);
    }
}
