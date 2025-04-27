package br.com.microservice.cliente.controler;

import br.com.microservice.cliente.domain.value_objects.Endereco;
import br.com.microservice.cliente.dto.ClienteDTO;
import br.com.microservice.cliente.dto.rest_controller.InputUpdateClienteDTO;
import br.com.microservice.cliente.dto.usecase.UpdateClienteDTO;
import br.com.microservice.cliente.usecase.UpdateClienteUseCase;
import br.com.microservice.cliente.usecase.mapper.ClienteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.stream.Collectors;

@RestController
@RequestMapping("update-cliente")
@Tag(name = "Cliente", description = "Endpoints que modificam,controla,cria e deleta clientes")
public class UpdateClienteController {
    final UpdateClienteUseCase useCase;

    public UpdateClienteController(UpdateClienteUseCase useCase) {
        this.useCase = useCase;
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza alguns dados de um cliente"
    )
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @Valid @RequestBody InputUpdateClienteDTO input){
        return ResponseEntity.ok(useCase.update(
                id,
                new UpdateClienteDTO(
                        input.nome(),
                        input.email(),
                        LocalDate.parse(input.dataNascimento()),
                        new HashSet<>(input.enderecos()),
                        new HashSet<>(input.telefones())
                )
        ));
    }
}
