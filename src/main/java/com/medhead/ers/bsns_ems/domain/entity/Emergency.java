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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public void setAsDispatched(UUID hospitalId) {
        this.setHospitalId(hospitalId);
        this.setStatus(EmergencyStatus.DISPATCHED);
    }
}
