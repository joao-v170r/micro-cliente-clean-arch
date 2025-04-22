package br.com.microservice.cliente.dto.usecase;

import br.com.microservice.cliente.domain.value_objects.Endereco;
import br.com.microservice.cliente.domain.value_objects.Telefone;

import java.time.LocalDate;

public record CriarClienteDTO (
        String nome,
        String cpf,
        String email,
        LocalDate dataNascimento,
        Endereco endereco,
        Telefone telefone
) {
}
