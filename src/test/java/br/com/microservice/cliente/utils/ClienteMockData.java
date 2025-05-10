package br.com.microservice.cliente.utils;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.domain.value_objects.CPF;
import br.com.microservice.cliente.domain.value_objects.Endereco;
import br.com.microservice.cliente.domain.value_objects.Telefone;
import br.com.microservice.cliente.dto.rest_controller.InputCreateClienteDTO;
import br.com.microservice.cliente.dto.rest_controller.InputUpdateClienteDTO;
import br.com.microservice.cliente.gateway.database.mongo.entity.ClienteEntity;
import br.com.microservice.cliente.gateway.database.mongo.mapper.ClienteMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ClienteMockData {

    // Formato de data esperado (ajuste conforme necessário)
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // CPFs válidos (apenas números)
    private static final String[] VALID_CPFS = {
            "52998224725",
            "39813614666",
            "74668869066",
            "43256776013",
            "94433627005"
    };

    // Gerar data de nascimento válida (maior de 18 anos)
    private static String generateValidBirthDate() {
        LocalDate today = LocalDate.now();
        LocalDate birthDate = today.minusYears(18).minusDays(1);
        return birthDate.toString();
    }

    // Gerar data de nascimento inválida (menor de idade)
    private static String generateUnderageBirthDate() {
        LocalDate today = LocalDate.now();
        LocalDate birthDate = today.minusYears(17);
        return birthDate.toString();
    }

    // DTO válido básico
    public static InputCreateClienteDTO validInput() {
        return new InputCreateClienteDTO(
                "João da Silva",
                VALID_CPFS[0],
                "joao.silva@example.com",
                generateValidBirthDate(),
                "01001000", // CEP do centro de São Paulo
                "Praça da Sé, 100 - Centro, São Paulo/SP",
                -23558774,
                -46665591,
                "987654321",
                "11"
        );
    }

    // Variações de DTOs válidos para diferentes cenários
    public static InputCreateClienteDTO validInputWithDifferentCpf() {
        return new InputCreateClienteDTO(
                "Maria Oliveira",
                VALID_CPFS[1],
                "maria.oliveira@example.com",
                generateValidBirthDate(),
                "20040030", // CEP do centro do Rio
                "Av. Rio Branco, 1 - Centro, Rio de Janeiro/RJ",
                -2291141,
                -4315474,
                "912345678",
                "21"
        );
    }

    // DTOs inválidos para testes negativos
    public static InputCreateClienteDTO invalidInputWithEmptyName() {
        InputCreateClienteDTO valid = validInput();
        return new InputCreateClienteDTO(
                "", // Nome vazio
                valid.cpf(),
                valid.email(),
                valid.dataNascimento(),
                valid.cep(),
                valid.enderecoCompleto(),
                valid.latitude(),
                valid.longitude(),
                valid.telefone(),
                valid.ddd()
        );
    }

    public static ClienteEntity validClienteEntity() {
        Cliente clienteDTO = validCliente();
        return ClienteMapper.mapToEntity(clienteDTO);
    }

    public static InputCreateClienteDTO invalidInputWithInvalidCpf() {
        InputCreateClienteDTO valid = validInput();
        return new InputCreateClienteDTO(
                valid.nome(),
                "11111111111", // CPF inválido
                valid.email(),
                valid.dataNascimento(),
                valid.cep(),
                valid.enderecoCompleto(),
                valid.latitude(),
                valid.longitude(),
                valid.telefone(),
                valid.ddd()
        );
    }

    public static InputCreateClienteDTO invalidInputWithUnderage() {
        InputCreateClienteDTO valid = validInput();
        return new InputCreateClienteDTO(
                valid.nome(),
                valid.cpf(),
                valid.email(),
                generateUnderageBirthDate(), // Data de nascimento inválida
                valid.cep(),
                valid.enderecoCompleto(),
                valid.latitude(),
                valid.longitude(),
                valid.telefone(),
                valid.ddd()
        );
    }

    // DTO com email inválido
    public static InputCreateClienteDTO invalidInputWithBadEmail() {
        InputCreateClienteDTO valid = validInput();
        return new InputCreateClienteDTO(
                valid.nome(),
                valid.cpf(),
                "email-invalido", // Email inválido
                valid.dataNascimento(),
                valid.cep(),
                valid.enderecoCompleto(),
                valid.latitude(),
                valid.longitude(),
                valid.telefone(),
                valid.ddd()
        );
    }

    public static InputUpdateClienteDTO validInputUpdateClienteDTO() {
        InputCreateClienteDTO valid = validInputWithDifferentCpf();
        return new InputUpdateClienteDTO(
                valid.nome(),
                valid.email(),
                valid.dataNascimento(),
                List.of(new Endereco(
                        valid.cep(),
                        valid.enderecoCompleto(),
                        valid.latitude(),
                        valid.longitude()
                )),
                List.of(new Telefone(
                        valid.telefone(),
                        valid.ddd()
                ))
        );
    }

    public static Cliente validCliente() {
        InputCreateClienteDTO valid = validInput();
        var endereco = new Endereco(
                valid.cep(),
                valid.enderecoCompleto(),
                valid.latitude(),
                valid.longitude()
        );
        var telefone = new Telefone(
                valid.telefone(),
                valid.ddd()
        );
        return Cliente.reconstituir(
                UUID.randomUUID().toString(),
                valid.nome(),
                new CPF(valid.cpf()),
                valid.email(),
                LocalDate.parse(valid.dataNascimento()),
                Set.of(endereco),
                Set.of(telefone)

        );
    }
}