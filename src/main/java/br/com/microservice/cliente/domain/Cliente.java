package br.com.microservice.cliente.domain;

import br.com.microservice.cliente.domain.value_objects.CPF;
import br.com.microservice.cliente.domain.value_objects.Endereco;
import br.com.microservice.cliente.domain.value_objects.Telefone;
import br.com.microservice.cliente.exception.ClienteError;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Getter
public class Cliente {

    public static final int TAMANHO_NOME_MIN = 2;
    public static final int IDADE_MIN = 11;

    private final Long id;
    private String nome;
    private final CPF cpf;
    private String email;
    private LocalDate dataNascimento;
    private Set<Endereco> enderecos;
    private Set<Telefone> telefones;

    private Cliente(Long id, String nome, CPF cpf, String email, LocalDate dataNascimento, Set<Endereco> enderecos, Set<Telefone> telefones) {
        this.id = id;
        this.nome = validaNome(nome);
        this.cpf = cpf;
        this.email = validaEmail(email);
        this.dataNascimento = validaDataNascimento(dataNascimento);
        this.enderecos = enderecos;
        this.telefones = telefones;
    }

    public static Cliente criar(
            String nome,
            String cpf,
            String email,
            LocalDate dataNascimento,
            Endereco endereco,
            Telefone telefone
    ) {
        return new Cliente(
                null,
                nome,
                new CPF(cpf),
                email,
                dataNascimento,
                Set.of(endereco),
                Set.of(telefone)
        );
    }

    public static Cliente reconstituir(
            Long id,
            String nome,
            CPF cpf,
            String email,
            LocalDate dataNascimento,
            Set<Endereco> enderecos,
            Set<Telefone> telefones
    ) {
        return new Cliente(
                id,
                nome,
                cpf,
                email,
                dataNascimento,
                enderecos,
                telefones
        );
    }

    private String validaEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ClienteError.ClienteIllegalArgumentException("email esta vazio");
        }

        // Verifica se tem @ e pelo menos um . após o @
        int posArroba = email.indexOf('@');
        if (posArroba <= 0 || posArroba == email.length() - 1) {
            throw new ClienteError.ClienteIllegalArgumentException("o (@) do email está invalido");
        }

        String dominio = email.substring(posArroba + 1);
        if(!dominio.contains(".") || dominio.lastIndexOf('.') == dominio.length() - 1){
            throw new ClienteError.ClienteIllegalArgumentException("o (.) do email está invalido");
        }

        return email;
    }

    private LocalDate validaDataNascimento(LocalDate dtNascimento) {
        long idade = dtNascimento.until(LocalDate.now(), ChronoUnit.YEARS);
        if(idade <= 11) {
            log.info("Cliente: idade deve ser maior que 11 anos");
            throw new IllegalArgumentException("data de nascimento invalida");
        }
        return dtNascimento;
    }

    private String validaNome(String nome) {
        if(nome.trim().length() < TAMANHO_NOME_MIN) {
            throw new ClienteError.ClienteIllegalArgumentException("nome não pode ser vazio");
        }
        return nome;
    }

    public Long getIdade() {
        return dataNascimento.until(LocalDate.now(), ChronoUnit.YEARS);
    }

    public Boolean isIdadeValida(Integer idade) {
        return idade > IDADE_MIN;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = validaDataNascimento(dataNascimento);
    }

    public void setNome(String nome) {
        this.nome = validaNome(nome);
    }

    public void setEmail(String email) {
        this.email = validaEmail(email);
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        if(enderecos.isEmpty()) {
            throw new ClienteError.ClienteIllegalArgumentException("nao pode ter enderecos vazios");
        }
        this.enderecos = enderecos;
    }

    public void setTelefone(Set<Telefone> telefones) {
        if(telefones.isEmpty()) {
            throw new ClienteError.ClienteIllegalArgumentException("nao pode ter telefones vazios");
        }
        this.telefones = telefones;
    }
}
