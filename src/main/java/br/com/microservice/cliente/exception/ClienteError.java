package br.com.microservice.cliente.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class ClienteError {

    // Exception para cliente não encontrado (HTTP 404)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class ClienteNotFoundException extends RuntimeException {
        public ClienteNotFoundException(String message) {
            super(message);
        }
    }

    // Exception para argumentos inválidos (HTTP 400)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static final class ClienteIllegalArgumentException extends IllegalArgumentException {
        public ClienteIllegalArgumentException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT) // HTTP 409
    public static final class ClienteAlreadyExistsException extends RuntimeException {
        public ClienteAlreadyExistsException(String message) {
            super(message);
        }
    }
}