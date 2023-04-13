package com.medhead.ers.bsns_ems.domain.service.implementation;

import com.medhead.ers.bsns_ems.data.repository.EmergencyRepository;
import com.medhead.ers.bsns_ems.application.messaging.exception.MessagePublicationFailException;
import com.medhead.ers.bsns_ems.application.messaging.message.factory.MessageFactory;
import com.medhead.ers.bsns_ems.application.messaging.service.definition.MessagePublisher;
import com.medhead.ers.bsns_ems.domain.entity.Emergency;
import com.medhead.ers.bsns_ems.domain.exception.EmergencyNotFoundException;
import com.medhead.ers.bsns_ems.domain.service.definition.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmergencyServiceImpl implements EmergencyService {
    @Autowired
    EmergencyRepository emergencyRepository;
    @Autowired
    MessagePublisher messagePublisher;

    @Override
    public Emergency saveEmergency(Emergency emergency) {
            Emergency emergencyCreated = emergencyRepository.save(emergency);
            try {
                messagePublisher.publish(MessageFactory.createEmergencyCreatedMessage(emergencyCreated));
                return emergencyCreated;
            }
            // Pour le POC, aucune gestion d'erreur sur la publication de message ne sera implémentée.
            catch (MessagePublicationFailException e){return emergencyCreated;}
    }

    @Override
    public List<Emergency> getAllEmergencies() {
        return emergencyRepository.findAll();
    }

    @Override
    public Emergency getEmergencyById(UUID uuid) {
        return emergencyRepository.findById(uuid).orElseThrow(()->new EmergencyNotFoundException(uuid));
    }

    @Override
    public Emergency setEmergencyAsDispatched(UUID emergencyId, UUID hospitalId) {
        Emergency emergency = getEmergencyById(emergencyId);
        emergency.setAsDispatched(hospitalId);
        Emergency emergencyUpdated = emergencyRepository.save(emergency);
        try {
            messagePublisher.publish(MessageFactory.createEmergencyUpdatedMessage(emergencyUpdated));
            return emergencyUpdated;
        }
        // Pour le POC, aucune gestion d'erreur sur la publication de message ne sera implémentée.
        catch (MessagePublicationFailException e){return emergencyUpdated;}
    }
}
