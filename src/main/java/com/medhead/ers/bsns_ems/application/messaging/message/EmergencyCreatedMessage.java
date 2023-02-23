package com.medhead.ers.bsns_ems.application.messaging.message;

import com.medhead.ers.bsns_ems.application.messaging.event.AvailableEvent;
import com.medhead.ers.bsns_ems.domain.entity.Emergency;
import org.json.JSONObject;

public class EmergencyCreatedMessage extends Message{
    public EmergencyCreatedMessage(Emergency emergency) {
        this.eventType = AvailableEvent.EmergencyCreated;
        this.setMetadata(
                new JSONObject().put("emergency", emergency).toMap()
        );
    }
}
