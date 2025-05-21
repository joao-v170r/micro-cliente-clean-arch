package br.com.microservice.cliente.gateway;

import br.com.microservice.cliente.domain.Cliente;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudClienteGateway {
    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findById(String id);
    Boolean existId(String id);
    List<Cliente> findAll();
    Cliente save(Cliente cliente);
    void deleteById(String id);
    List<Cliente> findAll(Pageable page);
}
