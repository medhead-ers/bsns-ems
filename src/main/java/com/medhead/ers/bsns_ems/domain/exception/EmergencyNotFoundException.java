package com.medhead.ers.bsns_ems.domain.exception;

import java.util.UUID;

public class EmergencyNotFoundException extends  RuntimeException{
    public EmergencyNotFoundException(UUID uuid) {
        super("Could not find emergency " + uuid);
    }
}