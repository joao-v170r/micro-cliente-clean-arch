package br.com.microservice.cliente.controler;

import br.com.microservice.cliente.domain.value_objects.Endereco;
import br.com.microservice.cliente.domain.value_objects.Telefone;
import br.com.microservice.cliente.dto.ClienteDTO;
import br.com.microservice.cliente.dto.rest_controller.InputCreateClienteDTO;
import br.com.microservice.cliente.dto.usecase.CreateClienteDTO;
import br.com.microservice.cliente.usecase.CreateClienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/create-cliente")
@Tag(name = "Cliente", description = "Endpoints que modificam,controla,cria e deleta clientes")
public class CreateClienteController {
    final CreateClienteUseCase useCase;

    public CreateClienteController(CreateClienteUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    @Operation(
            summary = "Cria uma novo cliente"
    )
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody InputCreateClienteDTO input) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(useCase.create(
                new CreateClienteDTO(
                    input.nome(),
                    input.cpf(),
                    input.email(),
                    LocalDate.parse(input.dataNascimento()),
                    new Endereco(
                            input.cep(),
                            input.enderecoCompleto(),
                            input.latitude(),
                            input.longitude()
                    ),
                    new Telefone(
                            input.telefone(),
                            input.ddd()
                    )
                )
        ));
    }
}
