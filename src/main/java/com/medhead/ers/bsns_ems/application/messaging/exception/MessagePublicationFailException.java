package com.medhead.ers.bsns_ems.application.messaging.exception;

public class MessagePublicationFailException extends Exception {
    public MessagePublicationFailException(Exception exception) {
        super(exception.getMessage());
    }
}
