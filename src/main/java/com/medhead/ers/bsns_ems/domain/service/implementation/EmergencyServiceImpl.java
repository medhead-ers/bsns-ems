package com.medhead.ers.bsns_ems.domain.service.implementation;

import com.medhead.ers.bsns_ems.application.data.repository.EmergencyRepository;
import com.medhead.ers.bsns_ems.domain.entity.Emergency;
import com.medhead.ers.bsns_ems.domain.exception.EmergencyNotFoundException;
import com.medhead.ers.bsns_ems.domain.service.definition.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmergencyServiceImpl implements EmergencyService {
    @Autowired
    EmergencyRepository emergencyRepository;
    @Override
    public Emergency saveEmergency(Emergency emergency){
            return emergencyRepository.save(emergency);
    }

    @Override
    public List<Emergency> getAllEmergencies() {
        return emergencyRepository.findAll();
    }

    @Override
    public Emergency getEmergencyById(UUID uuid) {
        return emergencyRepository.findById(uuid).orElseThrow(()->new EmergencyNotFoundException(uuid));
    }
}
