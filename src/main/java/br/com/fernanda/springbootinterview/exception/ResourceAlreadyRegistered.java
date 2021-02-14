package br.com.fernanda.springbootinterview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceAlreadyRegistered extends RuntimeException {
    public ResourceAlreadyRegistered(String message) {
        super(message);
    }
}
