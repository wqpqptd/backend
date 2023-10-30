package com.example.backend.controllers.Religion;

import com.example.backend.services.ReligionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("religion")
public class ReligionController {
    @Autowired
    private ReligionService religionService;


}
