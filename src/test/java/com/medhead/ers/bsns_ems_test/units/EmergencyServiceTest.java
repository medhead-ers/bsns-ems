package com.medhead.ers.bsns_ems_test.units;

import com.medhead.ers.bsns_ems.BsnsEmsApplication;
import com.medhead.ers.bsns_ems.data.repository.EmergencyRepository;
import com.medhead.ers.bsns_ems.domain.entity.Emergency;
import com.medhead.ers.bsns_ems.domain.service.implementation.EmergencyServiceImpl;
import com.medhead.ers.bsns_ems.domain.valueObject.EmergencyStatus;
import com.medhead.ers.bsns_ems.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_ems.domain.valueObject.MedicalSpeciality;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

@DirtiesContext
@SpringBootTest(classes = BsnsEmsApplication.class)
class EmergencyServiceTest {
    @Autowired
    EmergencyServiceImpl emergencyService;
    @Autowired
    EmergencyRepository emergencyRepository;

    @Test
    void test_setEmergencyAsDispatched(){
        // Given
        Emergency emergency = buildTestEmergency();
        emergency.setStatus(EmergencyStatus.PENDING);
        emergency = emergencyRepository.save(emergency);
        // When
        emergencyService.setEmergencyAsDispatched(emergency.getId());
        // Then
        Emergency emergencyFromDb = emergencyService.getEmergencyById(emergency.getId());
        Assertions.assertEquals(EmergencyStatus.DISPATCHED, emergencyFromDb.getStatus());
    }

    private Emergency buildTestEmergency(){
        return Emergency.builder()
                .description("Test Emergency")
                .gpsCoordinates(GPSCoordinates.builder()
                        .latitude(50.51746719004866)
                        .longitude(-0.05958983237771111).build())
                .medicalSpeciality(MedicalSpeciality.IMMUNOLOGY)
                .patientId(UUID.randomUUID().toString())
                .build();
    }
}
