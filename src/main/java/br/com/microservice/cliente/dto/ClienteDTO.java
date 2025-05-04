package br.com.microservice.cliente.dto;

import br.com.microservice.cliente.domain.value_objects.Endereco;
import br.com.microservice.cliente.domain.value_objects.Telefone;

import java.time.LocalDate;
import java.util.Set;

public record ClienteDTO(
        String id,
        String nome,
        String cpf,
        String email,
        LocalDate dataNascimento,
        Set<Endereco> enderecos,
        Set<Telefone> telefones
) {
}
