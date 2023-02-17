package com.medhead.ers.bsns_ems.application.data.sample;

import com.medhead.ers.bsns_ems.application.data.repository.EmergencyRepository;
import com.medhead.ers.bsns_ems.domain.entity.Emergency;
import com.medhead.ers.bsns_ems.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_ems.domain.valueObject.MedicalSpeciality;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class DataSampleLoader {
    @Bean
    CommandLineRunner initDatabase(EmergencyRepository emergencyRepository) {
        Emergency emergency = Emergency.builder()
                .description("Test Emergency")
                .gpsCoordinates(GPSCoordinates.builder()
                        .latitude(50.51746719004866)
                        .longitude(-0.05958983237771111).build())
                .medicalSpeciality(MedicalSpeciality.CARDIOLOGY)
                .patientId(UUID.randomUUID().toString())
                .build();
        return args -> {
            emergencyRepository.save(emergency);
        };
    }
}
