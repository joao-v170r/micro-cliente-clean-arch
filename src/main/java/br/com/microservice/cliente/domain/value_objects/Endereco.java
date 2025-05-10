package br.com.microservice.cliente.domain.value_objects;

import java.util.Objects;

public record Endereco(
    String cep,
    String enderecoCompleto,
    Integer latitude,
    Integer longitude
    ){

    public Endereco {
        if(cep.isEmpty() || !validCEP(cep)) {
            throw new IllegalArgumentException("cep está invalido");
        }
        if(enderecoCompleto.isEmpty()){
            throw new IllegalArgumentException("endereço completo e obrigatorio");
        }
        Objects.requireNonNull(latitude, "latitude é obrigatorio");
        Objects.requireNonNull(latitude, "longitude é obrigatorio");
    }

    public Boolean validCEP(String cep) {
        return cep.matches("^[0-9]{5}-?[0-9]{3}$");
    }

}
/*
{
    "nome": "João Silva",
    "email": "joao.silva@example.com",
    "dataNascimento": "1990-01-01",
    "enderecos": [
        {
        "enderecoCompleto": "Rua Exemplo,123, São Paulo, SP",
        "latitude": 10,
        "longitude": 10,
        "cep": "01234-567"
        }
    ],
    "telefones": [
        {
        "ddd": "11",
        "numero": "987654321"
       }
    ]
}*/
