package com.medhead.ers.bsns_ems.domain.service.definition;

import com.medhead.ers.bsns_ems.domain.entity.Emergency;

import java.util.List;
import java.util.UUID;

public interface EmergencyService {
    Emergency saveEmergency(Emergency emergency);
    List<Emergency> getAllEmergencies();
    Emergency getEmergencyById(UUID uuid);
    Emergency setEmergencyAsDispatched(UUID emergencyId, UUID hospitalId);
}
