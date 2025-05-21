package br.com.microservice.cliente.usecase;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.domain.value_objects.CPF;
import br.com.microservice.cliente.domain.value_objects.Endereco;
import br.com.microservice.cliente.domain.value_objects.Telefone;
import br.com.microservice.cliente.dto.ClienteDTO;
import br.com.microservice.cliente.dto.usecase.UpdateClienteDTO;
import br.com.microservice.cliente.exception.ClienteError;
import br.com.microservice.cliente.gateway.CrudClienteGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateClienteUseCaseTest {

    @Mock
    private CrudClienteGateway gateway;

    @InjectMocks
    private UpdateClienteUseCase useCase;

    private final String ID_VALIDO = "123";
    private final String NOME_VALIDO = "João Silva";
    private final CPF CPF_VALIDO = new CPF("12345678909");
    private final String EMAIL_VALIDO = "joao@email.com";
    private final LocalDate DATA_NASCIMENTO_VALIDA = LocalDate.now().minusYears(20);
    private final Endereco ENDERECO_VALIDO = new Endereco("27521-000", "456", 789012, 210987);
    private final Telefone TELEFONE_VALIDO = new Telefone("999999999", "11");
    private final Set<Endereco> ENDERECOS_VALIDOS = Set.of(ENDERECO_VALIDO);
    private final Set<Telefone> TELEFONES_VALIDOS = Set.of(TELEFONE_VALIDO);

    private Cliente criarClienteValido() {
        return Cliente.reconstituir(
                ID_VALIDO,
                NOME_VALIDO,
                CPF_VALIDO,
                EMAIL_VALIDO,
                DATA_NASCIMENTO_VALIDA,
                ENDERECOS_VALIDOS,
                TELEFONES_VALIDOS
        );
    }

    @Test
    @DisplayName("Deve atualizar cliente com sucesso quando todos os campos são fornecidos")
    void deveAtualizarClienteComSucessoTodosCampos() {
        Cliente clienteExistente = criarClienteValido();
        when(gateway.findById(ID_VALIDO)).thenReturn(Optional.of(clienteExistente));
        when(gateway.save(any(Cliente.class))).thenReturn(clienteExistente);

        UpdateClienteDTO updateDTO = new UpdateClienteDTO(
                "Novo Nome",
                "novo@email.com",
                LocalDate.now().minusYears(25),
                ENDERECOS_VALIDOS,
                TELEFONES_VALIDOS
        );

        ClienteDTO resultado = useCase.update(ID_VALIDO, updateDTO);

        assertNotNull(resultado);
        assertEquals(updateDTO.nome(), resultado.nome());
        assertEquals(updateDTO.email(), resultado.email());
        assertEquals(updateDTO.dataNascimento(), resultado.dataNascimento());
    }

    @Test
    @DisplayName("Deve atualizar apenas nome quando somente nome é fornecido")
    void deveAtualizarApenasNome() {
        Cliente clienteExistente = criarClienteValido();
        when(gateway.findById(ID_VALIDO)).thenReturn(Optional.of(clienteExistente));
        when(gateway.save(any(Cliente.class))).thenReturn(clienteExistente);

        UpdateClienteDTO updateDTO = new UpdateClienteDTO(
                "Novo Nome",
                null,
                null,
                null,
                null
        );

        ClienteDTO resultado = useCase.update(ID_VALIDO, updateDTO);

        assertNotNull(resultado);
        assertEquals(updateDTO.nome(), resultado.nome());
        assertEquals(EMAIL_VALIDO, resultado.email());
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não for encontrado")
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(gateway.findById(ID_VALIDO)).thenReturn(Optional.empty());

        UpdateClienteDTO updateDTO = new UpdateClienteDTO(
                "Novo Nome",
                null,
                null,
                null,
                null
        );

        assertThrows(ClienteError.ClienteNotFoundException.class, () ->
                useCase.update(ID_VALIDO, updateDTO));
    }

    @Test
    @DisplayName("Deve manter dados existentes quando DTO estiver vazio")
    void deveManterDadosExistentesQuandoDTOVazio() {
        Cliente clienteExistente = criarClienteValido();
        when(gateway.findById(ID_VALIDO)).thenReturn(Optional.of(clienteExistente));
        when(gateway.save(any(Cliente.class))).thenReturn(clienteExistente);

        UpdateClienteDTO updateDTO = new UpdateClienteDTO(
                null,
                null,
                null,
                null,
                null
        );

        ClienteDTO resultado = useCase.update(ID_VALIDO, updateDTO);

        assertNotNull(resultado);
        assertEquals(NOME_VALIDO, resultado.nome());
        assertEquals(EMAIL_VALIDO, resultado.email());
        assertEquals(DATA_NASCIMENTO_VALIDA, resultado.dataNascimento());
    }
}