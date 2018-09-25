package rocks.vinu.springjpa.jpaupdatedemo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DataPendingException extends RuntimeException {

    public DataPendingException(String message) {
        super(message);
    }
}
