package br.com.microservice.cliente.usecase;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.dto.ClienteDTO;
import br.com.microservice.cliente.dto.usecase.UpdateClienteDTO;
import br.com.microservice.cliente.gateway.CrudClienteGateway;
import br.com.microservice.cliente.usecase.mapper.ClienteMapper;
import org.springframework.stereotype.Service;

@Service
public class UpdateClienteUseCase {

    private final CrudClienteGateway gateway;

    public UpdateClienteUseCase(CrudClienteGateway gateway) {
        this.gateway = gateway;
    }

    public ClienteDTO update(String id, UpdateClienteDTO clienteDTO) {
        Cliente cliente = gateway.findById(id)
                .orElseThrow(() -> new RuntimeException("UpdateClienteUseCase: id do cliente não encontrado"));

        if(clienteDTO.nome()!= null) {
            cliente.setNome(clienteDTO.nome());
        }

        if(clienteDTO.email() != null) {
            cliente.setEmail(clienteDTO.email());
        }

        if(clienteDTO.dataNascimento() != null) {
            cliente.setDataNascimento(clienteDTO.dataNascimento());
        }

        if(clienteDTO.telefones() != null) {
            cliente.setTelefone(clienteDTO.telefones());
        }

        if(clienteDTO.enderecos() != null) {
            cliente.setEnderecos(clienteDTO.enderecos());
        }

        return ClienteMapper.mapToDTO(gateway.save(cliente));
    }
}
