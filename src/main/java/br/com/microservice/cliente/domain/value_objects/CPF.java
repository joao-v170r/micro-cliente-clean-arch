package br.com.microservice.cliente.domain.value_objects;
import java.util.regex.Pattern;
public record CPF(
        String numero
) {

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$");

    /**
     * Valida um CPF de acordo com as regras oficiais
     * @param cpf Número do CPF (com ou sem formatação)
     * @return true se o CPF é válido, false caso contrário
     */
    public static boolean validarCPF(String cpf) {
        // Verifica se o CPF está no formato correto
        if (cpf == null || !CPF_PATTERN.matcher(cpf).matches()) {
            return false;
        }

        // Remove caracteres não numéricos
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem 11 dígitos ou se é uma sequência de números repetidos
        if (cpfNumerico.length() != 11 || todosDigitosIguais(cpfNumerico)) {
            return false;
        }

        // Calcula e compara os dígitos verificadores
        try {
            int digito1 = calcularDigitoVerificador(cpfNumerico.substring(0, 9), 10);
            int digito2 = calcularDigitoVerificador(cpfNumerico.substring(0, 9) + digito1, 11);

            return cpfNumerico.equals(cpfNumerico.substring(0, 9) + digito1 + digito2);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Calcula um dígito verificador do CPF
     * @param parte Parte do CPF para cálculo
     * @param peso Peso inicial para cálculo
     * @return Dígito verificador calculado
     */
    private static int calcularDigitoVerificador(String parte, int peso) {
        int soma = 0;

        for (int i = 0; i < parte.length(); i++) {
            soma += Integer.parseInt(parte.substring(i, i + 1)) * peso--;
        }

        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    /**
     * Verifica se todos os dígitos do CPF são iguais
     * @param cpf Número do CPF
     * @return true se todos os dígitos forem iguais
     */
    private static boolean todosDigitosIguais(String cpf) {
        return cpf.chars().allMatch(digito -> digito == cpf.charAt(0));
    }

    // Versão que lança exceção para casos de uso mais rigorosos
    public CPF {
        if (!validarCPF(numero)) {
            throw new IllegalArgumentException("CPF inválido: " + numero);
        }
    }
}
