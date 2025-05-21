package br.com.microservice.cliente.gateway.exception.mongo;

import br.com.microservice.cliente.gateway.exception.GatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class ClienteMongoError {

    private final static String PREFIX_MONGO = "mongo_db_gateway:";

    // Erro quando o cliente não é encontrado (HTTP 404)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class ClienteNotFoundException extends RuntimeException implements GatewayException {
        public ClienteNotFoundException(String message) {
            super(PREFIX_MONGO + message);
        }
    }

    // Erro quando um argumento inválido é passado (HTTP 400)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static final class ClienteInvalidArgumentException extends IllegalArgumentException implements GatewayException {
        public ClienteInvalidArgumentException(String message) {
            super(PREFIX_MONGO + message);
        }
    }

    // Erro genérico de persistência (ex: falha no MongoDB) (HTTP 500)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static final class ClientePersistenceException extends RuntimeException implements GatewayException {
        public ClientePersistenceException(String message, Throwable cause) {
            super(PREFIX_MONGO + message, cause);
        }
    }

    // Erro quando há conflito (ex: CPF duplicado) (HTTP 409)
    @ResponseStatus(HttpStatus.CONFLICT)
    public static final class ClienteConflictException extends RuntimeException implements GatewayException {
        public ClienteConflictException(String message) {
            super(PREFIX_MONGO + message);
        }
    }
}