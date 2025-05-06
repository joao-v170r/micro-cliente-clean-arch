package br.com.microservice.cliente.usecase;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.exception.ClienteError;
import br.com.microservice.cliente.gateway.CrudClienteGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteClienteUseCase {

    private final CrudClienteGateway gateway;

    public DeleteClienteUseCase(CrudClienteGateway gateway) {
        this.gateway = gateway;
    }

    public void delete(String id) {
        Cliente cliente = gateway.findById(id).orElseThrow(() -> new ClienteError.ClienteNotFoundException("Cliente não encontrado"));
        gateway.deleteById(cliente.getId());
    }
}
