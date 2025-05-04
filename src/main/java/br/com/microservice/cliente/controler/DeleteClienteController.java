package br.com.microservice.cliente.controler;

import br.com.microservice.cliente.usecase.DeleteClienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delete-cliente")
@Tag(name = "Cliente", description = "Endpoints que modificam,controla,cria e deleta clientes")
public class DeleteClienteController {

    final DeleteClienteUseCase useCase;

    public DeleteClienteController(DeleteClienteUseCase useCase) {
        this.useCase = useCase;
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta um cliente"
    )
    public ResponseEntity<Void> delete(@NotBlank @PathVariable String id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
