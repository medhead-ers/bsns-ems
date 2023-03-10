package com.medhead.ers.bsns_ems.units;

import com.medhead.ers.bsns_ems.domain.entity.Emergency;
import com.medhead.ers.bsns_ems.domain.valueObject.EmergencyStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmergencyEntityTest {
    @Test
    void test_StatusAsDispatchedAfterSetAsDispatchedEmergency(){
        // Given
        Emergency emergency = Emergency.builder().status(EmergencyStatus.PENDING).build();
        // When
        emergency.setAsDispatched();
        // Then
        Assertions.assertEquals(EmergencyStatus.DISPATCHED, emergency.getStatus());
    }
}
