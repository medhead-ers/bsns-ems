package com.medhead.ers.bsns_ems.domain.entity;

import com.medhead.ers.bsns_ems.domain.valueObject.EmergencyStatus;
import com.medhead.ers.bsns_ems.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_ems.domain.valueObject.MedicalSpeciality;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Emergency {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private UUID id;

    @NotEmpty
    private String description;

    @NotNull
    private UUID patientId;

    @NotNull
    @Embedded
    private GPSCoordinates gpsCoordinates;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MedicalSpeciality medicalSpeciality;

    @NotNull
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EmergencyStatus status = EmergencyStatus.PENDING;

    @Nullable
    private UUID hospitalId;

    public void setAsDispatched(UUID hospitalId) {
        this.setHospitalId(hospitalId);
        this.setStatus(EmergencyStatus.DISPATCHED);
    }
}
