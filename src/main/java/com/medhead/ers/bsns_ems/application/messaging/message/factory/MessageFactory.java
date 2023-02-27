package com.medhead.ers.bsns_ems.application.messaging.message.factory;


import com.medhead.ers.bsns_ems.application.messaging.message.EmergencyCreatedMessage;
import com.medhead.ers.bsns_ems.domain.entity.Emergency;

public interface MessageFactory {
    static EmergencyCreatedMessage createEmergencyCreatedMessage(Emergency emergency) {
        return new EmergencyCreatedMessage(emergency);
    }
}
