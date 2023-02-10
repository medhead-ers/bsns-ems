package com.medhead.ers.bsns_ems.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medhead.ers.bsns_ems.application.data.repository.EmergencyRepository;
import com.medhead.ers.bsns_ems.domain.entity.Emergency;
import com.medhead.ers.bsns_ems.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_ems.domain.valueObject.MedicalSpeciality;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class EmergencyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmergencyRepository emergencyRepository;
    @Test
    void test_createEmergency() throws Exception{
        // Given
        Emergency emergency = buildTestEmergency();
        // When
        mockMvc.perform(post("/emergencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emergency)))
        // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is(emergency.getDescription())))
                .andExpect(jsonPath("$.patientId", is(emergency.getPatientId())))
                .andExpect(jsonPath("$.medicalSpeciality", is(String.valueOf(emergency.getMedicalSpeciality()))))
                .andExpect(jsonPath("$.gpsCoordinates.longitude").value(is(emergency.getGpsCoordinates().getLongitude()), Double.class))
                .andExpect(jsonPath("$.gpsCoordinates.latitude").value(is(emergency.getGpsCoordinates().getLatitude()), Double.class));
    }

    @Test
    void test_getOneEmergency() throws Exception{
        // Given
        Emergency emergency = emergencyRepository.save(buildTestEmergency());
        // When
        mockMvc.perform(get("/emergencies/" + emergency.getId()))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(emergency.getDescription())))
                .andExpect(jsonPath("$.patientId", is(emergency.getPatientId())))
                .andExpect(jsonPath("$.medicalSpeciality", is(String.valueOf(emergency.getMedicalSpeciality()))))
                .andExpect(jsonPath("$.gpsCoordinates.longitude").value(is(emergency.getGpsCoordinates().getLongitude()), Double.class))
                .andExpect(jsonPath("$.gpsCoordinates.latitude").value(is(emergency.getGpsCoordinates().getLatitude()), Double.class));
    }
    @Test
    void test_failGetOneEmergency () throws Exception {
        // Given
        UUID emergencyId = UUID.randomUUID();
        // When
        mockMvc.perform(get("/emergencies/" + emergencyId))
        // Then
        .andExpect(status().isNotFound());
    }

    @Test
    void test_getEmergencies() throws Exception {
        // Given
        int totalEmergenciesInRepository = ((int) emergencyRepository.count());
        // When
        mockMvc.perform(get("/emergencies"))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(totalEmergenciesInRepository)));
    }

    private Emergency buildTestEmergency() {
        return Emergency.builder()
                .description("Test emergency")
                .gpsCoordinates(GPSCoordinates.builder()
                        .latitude(50.51746719004866)
                        .longitude(-0.05958983237771111).build())
                .medicalSpeciality(MedicalSpeciality.CARDIOLOGY)
                .patientId(UUID.randomUUID().toString())
                .build();
    }
}
