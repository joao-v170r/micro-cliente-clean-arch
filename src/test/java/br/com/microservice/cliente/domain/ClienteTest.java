package br.com.microservice.cliente.domain;

import br.com.microservice.cliente.domain.value_objects.CPF;
import br.com.microservice.cliente.domain.value_objects.Endereco;
import br.com.microservice.cliente.domain.value_objects.Telefone;
import br.com.microservice.cliente.exception.ClienteError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private final String NOME_VALIDO = "João Silva";
    private final CPF CPF_VALIDO = new CPF("12345678909");
    private final String EMAIL_VALIDO = "joao@email.com";
    private final LocalDate DATA_NASCIMENTO_VALIDA = LocalDate.now().minusYears(20);
    private final Endereco ENDERECO_VALIDO = new Endereco("27521-000", "456", 789012, 210987);
    private final Telefone TELEFONE_VALIDO = new Telefone("999999999", "11");

    @Nested
    @DisplayName("Testes de criação do Cliente")
    class CriacaoCliente {
        @Test
        @DisplayName("Deve criar cliente com sucesso")
        void deveCriarClienteComSucesso() {
            Cliente cliente = Cliente.criar(
                    NOME_VALIDO,
                    CPF_VALIDO,
                    EMAIL_VALIDO,
                    DATA_NASCIMENTO_VALIDA,
                    ENDERECO_VALIDO,
                    TELEFONE_VALIDO
            );

            assertNotNull(cliente);
            assertEquals(NOME_VALIDO, cliente.getNome());
            assertEquals(CPF_VALIDO, cliente.getCpf());
            assertEquals(EMAIL_VALIDO, cliente.getEmail());
            assertEquals(DATA_NASCIMENTO_VALIDA, cliente.getDataNascimento());
            assertTrue(cliente.getEnderecos().contains(ENDERECO_VALIDO));
            assertTrue(cliente.getTelefones().contains(TELEFONE_VALIDO));
        }

        @Test
        @DisplayName("Deve reconstituir cliente com sucesso")
        void deveReconstituirClienteComSucesso() {
            String id = "123";
            Cliente cliente = Cliente.reconstituir(
                    id,
                    NOME_VALIDO,
                    CPF_VALIDO,
                    EMAIL_VALIDO,
                    DATA_NASCIMENTO_VALIDA,
                    Set.of(ENDERECO_VALIDO),
                    Set.of(TELEFONE_VALIDO)
            );

            assertEquals(id, cliente.getId());
        }
    }

    @Nested
    @DisplayName("Testes de validação do nome")
    class ValidacaoNome {
        @Test
        @DisplayName("Deve lançar exceção quando nome for inválido")
        void deveLancarExcecaoQuandoNomeForInvalido() {
            assertThrows(ClienteError.ClienteIllegalArgumentException.class, () ->
                    Cliente.criar("", CPF_VALIDO, EMAIL_VALIDO, DATA_NASCIMENTO_VALIDA,
                            ENDERECO_VALIDO, TELEFONE_VALIDO));
        }
    }

    @Nested
    @DisplayName("Testes de validação do email")
    class ValidacaoEmail {
        @Test
        @DisplayName("Deve lançar exceção quando email for vazio")
        void deveLancarExcecaoQuandoEmailForVazio() {
            assertThrows(ClienteError.ClienteIllegalArgumentException.class, () ->
                    Cliente.criar(NOME_VALIDO, CPF_VALIDO, "", DATA_NASCIMENTO_VALIDA,
                            ENDERECO_VALIDO, TELEFONE_VALIDO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando email não tiver @")
        void deveLancarExcecaoQuandoEmailNaoTiverArroba() {
            assertThrows(ClienteError.ClienteIllegalArgumentException.class, () ->
                    Cliente.criar(NOME_VALIDO, CPF_VALIDO, "emailinvalido.com",
                            DATA_NASCIMENTO_VALIDA, ENDERECO_VALIDO, TELEFONE_VALIDO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando email não tiver domínio válido")
        void deveLancarExcecaoQuandoEmailNaoTiverDominioValido() {
            assertThrows(ClienteError.ClienteIllegalArgumentException.class, () ->
                    Cliente.criar(NOME_VALIDO, CPF_VALIDO, "email@", DATA_NASCIMENTO_VALIDA,
                            ENDERECO_VALIDO, TELEFONE_VALIDO));
        }
    }

    @Nested
    @DisplayName("Testes de validação da data de nascimento")
    class ValidacaoDataNascimento {
        @Test
        @DisplayName("Deve lançar exceção quando idade for menor que 11 anos")
        void deveLancarExcecaoQuandoIdadeForMenorQue11Anos() {
            LocalDate dataInvalida = LocalDate.now().minusYears(10);
            assertThrows(IllegalArgumentException.class, () ->
                    Cliente.criar(NOME_VALIDO, CPF_VALIDO, EMAIL_VALIDO, dataInvalida,
                            ENDERECO_VALIDO, TELEFONE_VALIDO));
        }
    }

    @Nested
    @DisplayName("Testes dos métodos de atualização")
    class MetodosAtualizacao {
        @Test
        @DisplayName("Deve atualizar endereços com sucesso")
        void deveAtualizarEnderecosComSucesso() {
            Cliente cliente = Cliente.criar(NOME_VALIDO, CPF_VALIDO, EMAIL_VALIDO,
                    DATA_NASCIMENTO_VALIDA, ENDERECO_VALIDO, TELEFONE_VALIDO);

            Endereco novoEndereco = new Endereco("27521-000", "456", 789012, 210987);
            cliente.setEnderecos(Set.of(novoEndereco));

            assertTrue(cliente.getEnderecos().contains(novoEndereco));
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar atualizar endereços vazios")
        void deveLancarExcecaoAoAtualizarEnderecosVazios() {
            Cliente cliente = Cliente.criar(NOME_VALIDO, CPF_VALIDO, EMAIL_VALIDO,
                    DATA_NASCIMENTO_VALIDA, ENDERECO_VALIDO, TELEFONE_VALIDO);

            assertThrows(ClienteError.ClienteIllegalArgumentException.class, () ->
                    cliente.setEnderecos(Set.of()));
        }
    }

    @Test
    @DisplayName("Deve calcular idade corretamente")
    void deveCalcularIdadeCorretamente() {
        Cliente cliente = Cliente.criar(NOME_VALIDO, CPF_VALIDO, EMAIL_VALIDO,
                DATA_NASCIMENTO_VALIDA, ENDERECO_VALIDO, TELEFONE_VALIDO);

        assertEquals(20, cliente.getIdade());
    }

    @Test
    @DisplayName("Deve validar idade corretamente")
    void deveValidarIdadeCorretamente() {
        Cliente cliente = Cliente.criar(NOME_VALIDO, CPF_VALIDO, EMAIL_VALIDO,
                DATA_NASCIMENTO_VALIDA, ENDERECO_VALIDO, TELEFONE_VALIDO);

        assertTrue(cliente.isIdadeValida(12));
        assertFalse(cliente.isIdadeValida(11));
    }
}