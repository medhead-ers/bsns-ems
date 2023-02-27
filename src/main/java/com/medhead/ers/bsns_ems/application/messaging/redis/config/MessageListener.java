package com.medhead.ers.bsns_ems.application.messaging.redis.config;

import com.medhead.ers.bsns_ems.application.messaging.event.Event;
import com.medhead.ers.bsns_ems.application.messaging.exception.CannotCreateEventFromJSONMessageException;
import com.medhead.ers.bsns_ems.application.messaging.exception.CannotProcessJobException;
import com.medhead.ers.bsns_ems.application.messaging.job.Job;
import com.medhead.ers.bsns_ems.application.messaging.service.definition.JobMapper;
import com.medhead.ers.bsns_ems.application.messaging.service.definition.MessageToEventConverter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
public class MessageListener {
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private MessageToEventConverter messageToEventConverter;
    private final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    public MessageListener(JobMapper jobMapper, MessageToEventConverter messageToEventConverter){
        this.jobMapper = jobMapper;
        this.messageToEventConverter = messageToEventConverter;
    }

    public void receiveMessage(String message) throws CannotProcessJobException {
        Event event = null;
        try {
            event = createEventFromMessage(message);
        } catch (CannotCreateEventFromJSONMessageException e) {
            logger.info("Message reçu de type inconnu ou malformé (pas d'événement éligible associé). Le message sera ignoré.");
            return;
        }
        logger.info("Message reçu de type : " + event.getEventType().toString() );
        if(jobMapper.checkIfJobExistForEvent(event)) {
            try {
                Job job = jobMapper.createJobFromEvent(event);
                logger.info("Traitement de l'événement de type : " + event.getEventType().toString() +". Job processor : "+ job.getClass().getSimpleName());
                job.process();
            } catch (Exception exception) {
                throw new CannotProcessJobException(exception);
            }
        }
        else {
            logger.info("Aucun job processor trouvé pour l'événement de type : " + event.getEventType().toString() + " - Message ignoré." );
        }
    }

    private Event createEventFromMessage(String message) throws CannotCreateEventFromJSONMessageException {
        try {
            return messageToEventConverter.convertMessageToEvent(message);
        } catch (Exception exception) {
            throw new CannotCreateEventFromJSONMessageException(message, exception);
        }
    }
}
