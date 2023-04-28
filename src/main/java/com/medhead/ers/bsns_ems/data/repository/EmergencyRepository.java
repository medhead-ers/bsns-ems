package com.medhead.ers.bsns_ems.data.repository;

import com.medhead.ers.bsns_ems.domain.entity.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmergencyRepository extends JpaRepository<Emergency, UUID> {

}
