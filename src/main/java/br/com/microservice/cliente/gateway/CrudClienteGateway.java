package br.com.microservice.cliente.gateway;

import br.com.microservice.cliente.domain.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudClienteGateway {
    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findById(Long id);
    Boolean existId(Long id);
    List<Cliente> findAll();
    Cliente save(Cliente cliente);
    void deleteById(Long id);
}
