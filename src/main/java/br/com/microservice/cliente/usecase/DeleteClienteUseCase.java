package br.com.microservice.cliente.usecase;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.gateway.CrudClienteGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteClienteUseCase {

    private final CrudClienteGateway gateway;

    public DeleteClienteUseCase(CrudClienteGateway gateway) {
        this.gateway = gateway;
    }

    public void delete(Long id) {
        Cliente cliente = gateway.findById(id).orElseThrow(RuntimeException::new);
        gateway.deleteById(cliente.getId());
    }
}
