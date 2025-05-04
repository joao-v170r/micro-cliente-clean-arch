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
/*
Exem:
{
    "nome": "João da Silva",
    "cpf": "123.456.789-09",
    "email": "joao.silva@example.com",
    "dataNascimento": "1990-05-15",
    "cep": "01001-000",
    "enderecoCompleto": "Rua das Flores, 123, Centro, São Paulo/SP",
    "latitude": -2356834,
    "longitude": -4657744,
    "telefone": "987654321",
    "ddd": "11"
}*/
