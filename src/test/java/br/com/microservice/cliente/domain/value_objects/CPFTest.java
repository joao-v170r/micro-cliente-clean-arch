package br.com.microservice.cliente.domain.value_objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CPFTest {

    @Nested
    @DisplayName("Testes de criação de CPF")
    class CriacaoCPF {
        @Test
        @DisplayName("Deve criar CPF válido com sucesso")
        void deveCriarCPFValidoComSucesso() {
            assertDoesNotThrow(() -> new CPF("529.982.247-25"));
        }

        @Test
        @DisplayName("Deve criar CPF válido sem formatação")
        void deveCriarCPFValidoSemFormatacao() {
            assertDoesNotThrow(() -> new CPF("52998224725"));
        }
    }

    @Nested
    @DisplayName("Testes de validação de CPF")
    class ValidacaoCPF {
        @ParameterizedTest
        @DisplayName("Deve rejeitar CPFs inválidos")
        @ValueSource(strings = {
                "123.456.789-00",  // CPF com dígitos verificadores inválidos
                "111.111.111-11",  // CPF com números repetidos
                "123.456.789",     // CPF incompleto
                "12345678900a",    // CPF com letra
                "",                // CPF vazio
                "123.456.789-0"    // CPF com tamanho incorreto
        })
        void deveRejeitarCPFsInvalidos(String cpfInvalido) {
            assertThrows(IllegalArgumentException.class,
                    () -> new CPF(cpfInvalido));
        }

        @Test
        @DisplayName("Deve rejeitar CPF nulo")
        void deveRejeitarCPFNulo() {
            assertThrows(IllegalArgumentException.class,
                    () -> new CPF(null));
        }
    }

    @Nested
    @DisplayName("Testes do método validarCPF")
    class MetodoValidarCPF {
        @Test
        @DisplayName("Deve validar CPF correto")
        void deveValidarCPFCorreto() {
            assertTrue(CPF.validarCPF("529.982.247-25"));
            assertTrue(CPF.validarCPF("52998224725"));
        }

        @ParameterizedTest
        @DisplayName("Deve identificar CPFs inválidos")
        @ValueSource(strings = {
                "000.000.000-00",
                "999.999.999-99",
                "111.111.111-11",
                "222.222.222-22",
                "333.333.333-33"
        })
        void deveIdentificarCPFsComDigitosRepetidos(String cpf) {
            assertFalse(CPF.validarCPF(cpf));
        }
    }

    @Nested
    @DisplayName("Testes de igualdade e hash code")
    class IgualdadeHashCode {
        @Test
        @DisplayName("Deve considerar CPFs iguais")
        void deveConsiderarCPFsIguais() {
            CPF cpf1 = new CPF("529.982.247-25");
            CPF cpf2 = new CPF("529.982.247-25");

            assertEquals(cpf1, cpf2);
            assertEquals(cpf1.hashCode(), cpf2.hashCode());
        }

        @Test
        @DisplayName("Deve manter valor original do número")
        void deveManterValorOriginalDoNumero() {
            String numeroOriginal = "529.982.247-25";
            CPF cpf = new CPF(numeroOriginal);
            assertEquals(numeroOriginal, cpf.numero());
        }
    }
}