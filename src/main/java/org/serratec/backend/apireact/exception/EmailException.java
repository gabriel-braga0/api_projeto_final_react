package org.serratec.backend.apireact.exception;

public class EmailException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailException(String message) {
        super(message);
    }
}
