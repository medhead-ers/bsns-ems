package com.medhead.ers.bsns_ems.integration;

import com.medhead.ers.bsns_ems.application.messaging.redis.config.MessageListener;
import com.medhead.ers.bsns_ems.domain.entity.Emergency;
import com.medhead.ers.bsns_ems.domain.service.definition.EmergencyService;
import com.medhead.ers.bsns_ems.domain.valueObject.EmergencyStatus;
import com.medhead.ers.bsns_ems.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_ems.domain.valueObject.MedicalSpeciality;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.UUID;

import static com.medhead.ers.bsns_ems.utils.FileReader.readFile;

@SpringBootTest
@DirtiesContext
@ExtendWith(OutputCaptureExtension.class)
public class EmergencyDispatchedJobProcessorTest {

    private final static String MOCK_MESSAGE_RESOURCES_PATH = "src/test/resources/mock/message/";

    @Autowired
    private MessageListener messageListener;
    @Autowired
    private EmergencyService emergencyService;

    @Test
    void test_successUpdateEmergencyStatusWhenEmergencyWasDispatched(CapturedOutput output) throws IOException {
        String eventName = "EmergencyDispatched";
        String jobProcessorName = eventName + "Job";

        // Given
        Emergency emergencyInDB = emergencyService.saveEmergency(buildTestEmergency());
        String emergencyDispatchedMessageFromFile = buildEmergencyDispatchedMessage(emergencyInDB.getId());
        // Then
        Assertions.assertDoesNotThrow( ()-> {
            // When
            messageListener.receiveMessage(emergencyDispatchedMessageFromFile);
        });
        emergencyInDB = emergencyService.getEmergencyById(emergencyInDB.getId());

        // ... Then
        Assertions.assertTrue(output.getAll().contains("Message reçu de type : " + eventName));
        Assertions.assertTrue(output.getAll().contains("Traitement de l'événement de type : " + eventName + ". Job processor : "+jobProcessorName));
        Assertions.assertTrue(output.getAll().contains("Fin de traitement de l'événement de type : " + eventName +" - Événement traité avec succès."));
        Assertions.assertEquals(EmergencyStatus.DISPATCHED, emergencyInDB.getStatus());
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

    private String buildEmergencyDispatchedMessage(UUID uuid) throws IOException {
        return readFile(MOCK_MESSAGE_RESOURCES_PATH + "emergency_dispatched.message")
                .replace("#EMERGENCY_UUID#", uuid.toString());
    }
}
