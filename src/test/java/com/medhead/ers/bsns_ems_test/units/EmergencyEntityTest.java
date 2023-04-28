package com.medhead.ers.bsns_ems_test.units;

import com.medhead.ers.bsns_ems.domain.entity.Emergency;
import com.medhead.ers.bsns_ems.domain.valueObject.EmergencyStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class EmergencyEntityTest {
    @Test
    void test_StatusAsDispatchedAfterSetAsDispatchedEmergency(){
        // Given
        Emergency emergency = Emergency.builder().status(EmergencyStatus.PENDING).build();
        UUID hospitalId = UUID.randomUUID();
        // When
        emergency.setAsDispatched(hospitalId);
        // Then
        Assertions.assertEquals(EmergencyStatus.DISPATCHED, emergency.getStatus());
        Assertions.assertEquals(hospitalId, emergency.getHospitalId());
    }
}
