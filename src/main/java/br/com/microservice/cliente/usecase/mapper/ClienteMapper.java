package br.com.microservice.cliente.usecase.mapper;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.domain.value_objects.CPF;
import br.com.microservice.cliente.dto.ClienteDTO;

public class ClienteMapper {
    public static Cliente mapToDomain(ClienteDTO dto) {
        return Cliente.reconstituir(
                dto.id(),
                dto.nome(),
                new CPF(dto.cpf()),
                dto.email(),
                dto.dataNascimento(),
                dto.enderecos(),
                dto.telefones()
        );
    }

    public static ClienteDTO mapToDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf().numero(),
                cliente.getEmail(),
                cliente.getDataNascimento(),
                cliente.getEnderecos(),
                cliente.getTelefones()
        );
    }
}
