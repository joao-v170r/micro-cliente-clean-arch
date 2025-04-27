package br.com.microservice.cliente.dto.rest_controller;

import br.com.microservice.cliente.domain.value_objects.Endereco;
import br.com.microservice.cliente.domain.value_objects.Telefone;

import java.util.List;

public record InputUpdateClienteDTO(
        String nome,
        String email,
        String dataNascimento,
        List<Endereco> enderecos,
        List<Telefone> telefones
) {
}
