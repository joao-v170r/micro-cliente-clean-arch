package br.com.microservice.cliente.gateway.database.mongo.mapper;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.domain.value_objects.CPF;
import br.com.microservice.cliente.dto.ClienteDTO;
import br.com.microservice.cliente.gateway.database.mongo.entity.ClienteEntity;

public class ClienteMapper {
    public static Cliente mapToDomain(ClienteEntity dto) {
        return Cliente.reconstituir(
                dto.getId(),
                dto.getNome(),
                new CPF(dto.getCpf()),
                dto.getEmail(),
                dto.getDataNascimento(),
                dto.getEnderecos(),
                dto.getTelefones()
        );
    }

    public static ClienteEntity mapToEntity(Cliente domain) {
        return new ClienteEntity(
                domain.getId(),
                domain.getNome(),
                domain.getCpf().numero(),
                domain.getEmail(),
                domain.getDataNascimento(),
                domain.getEnderecos(),
                domain.getTelefones()
        );
    }
}
