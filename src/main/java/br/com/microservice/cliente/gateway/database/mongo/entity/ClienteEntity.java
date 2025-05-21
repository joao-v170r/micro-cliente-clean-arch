package br.com.microservice.cliente.gateway.database.mongo.entity;

import br.com.microservice.cliente.domain.value_objects.Endereco;
import br.com.microservice.cliente.domain.value_objects.Telefone;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@Document(collection = "cliente")
public class ClienteEntity {
    @Id
    private String id;
    private String nome;
    @Indexed(unique = true)
    private String cpf;
    private String email;
    @Field("dt_nascimento")
    private LocalDate dataNascimento;
    private Set<Endereco> enderecos;
    private Set<Telefone> telefones;
}