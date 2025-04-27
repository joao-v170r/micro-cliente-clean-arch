package br.com.microservice.cliente.usecase;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.dto.ClienteDTO;
import br.com.microservice.cliente.exception.ClienteError;
import br.com.microservice.cliente.gateway.CrudClienteGateway;
import br.com.microservice.cliente.usecase.mapper.ClienteMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReadClienteUserCase {
    private final CrudClienteGateway gateway;

    public ReadClienteUserCase(CrudClienteGateway gateway) {
        this.gateway = gateway;
    }

    public ClienteDTO find(Long id) {
        Cliente cliente = gateway.findById(id).orElseThrow(
                () -> new ClienteError.ClienteNotFoundException("cliente não encontrado"));
        return ClienteMapper.mapToDTO(cliente);
    }

    public List<ClienteDTO> findAll(Pageable page) {
        List<Cliente> clientes = gateway.findAll(page);
        return clientes.stream().map(ClienteMapper::mapToDTO).toList();
    }
}
