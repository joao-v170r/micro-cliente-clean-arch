package br.com.microservice.cliente.dto.usecase;

import br.com.microservice.cliente.domain.value_objects.Endereco;
import br.com.microservice.cliente.domain.value_objects.Telefone;

import java.time.LocalDate;
import java.util.Set;

public record UpdateClienteDTO(
        String nome,
        String email,
        LocalDate dataNascimento,
        Set<Endereco> enderecos,
        Set<Telefone> telefones
) {
}
