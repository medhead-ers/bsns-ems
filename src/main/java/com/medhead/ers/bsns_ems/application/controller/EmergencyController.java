package com.medhead.ers.bsns_ems.application.controller;

import com.medhead.ers.bsns_ems.domain.entity.Emergency;
import com.medhead.ers.bsns_ems.domain.service.definition.EmergencyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
// Should be disabled in production
@CrossOrigin(origins = "http://localhost:3000")
public class EmergencyController {
    @Autowired
    EmergencyService emergencyService;
    @GetMapping("/emergencies")
    List<Emergency> all() {
        return emergencyService.getAllEmergencies();
    }

    @GetMapping("/emergencies/{uuid}")
    Emergency one(@PathVariable UUID uuid) {
        return emergencyService.getEmergencyById(uuid);
    }

    @PostMapping("/emergencies")
    @ResponseStatus(HttpStatus.CREATED)
    Emergency newEmergency(@Valid @RequestBody Emergency emergency) {
        return emergencyService.saveEmergency(emergency);
    }
}
