package com.medhead.ers.bsns_ems.application.messaging.message;

import com.medhead.ers.bsns_ems.application.messaging.event.AvailableEvent;
import com.medhead.ers.bsns_ems.domain.entity.Emergency;
import org.json.JSONObject;

public class EmergencyUpdatedMessage extends Message{
    public EmergencyUpdatedMessage(Emergency emergency) {
        this.eventType = AvailableEvent.EmergencyUpdated;
        this.setMetadata(
                new JSONObject().put("emergency", emergency).toMap()
        );
    }
}
