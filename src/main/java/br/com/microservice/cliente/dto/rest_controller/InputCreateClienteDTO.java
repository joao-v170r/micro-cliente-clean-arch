package br.com.microservice.cliente.dto.rest_controller;

import br.com.microservice.cliente.domain.value_objects.Endereco;
import br.com.microservice.cliente.domain.value_objects.Telefone;

import java.time.LocalDate;

public record InputCreateClienteDTO(
        String nome,
        String cpf,
        String email,
        String dataNascimento,
        String cep,
        String enderecoCompleto,
        Integer latitude,
        Integer longitude,
        String telefone,
        String ddd
) {
}
