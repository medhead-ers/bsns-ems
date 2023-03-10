package com.medhead.ers.bsns_ems.application.messaging.job;

import com.medhead.ers.bsns_ems.domain.service.definition.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmergencyDispatchedJob extends Job{

    @Autowired
    EmergencyService emergencyService;
    @Override
    public void process() throws Exception {
        emergencyService.setEmergencyAsDispatched(
                UUID.fromString(this.getEvent().getMetadata().get("emergencyId").toString())
        );
    }
}
