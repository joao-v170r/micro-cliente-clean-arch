package br.com.microservice.cliente.usecase;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.gateway.CrudClienteGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ReadClienteUserCase {
    private final CrudClienteGateway gateway;


    public ReadClienteUserCase(CrudClienteGateway gateway) {
        this.gateway = gateway;
    }

    public Cliente find(Long id) {
        Cliente cliente = gateway.findById(id).orElseThrow();
    }
}
