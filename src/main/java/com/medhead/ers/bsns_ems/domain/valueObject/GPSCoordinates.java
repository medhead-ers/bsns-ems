package com.medhead.ers.bsns_ems.domain.valueObject;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GPSCoordinates {
    private double longitude;
    private double latitude;
}
