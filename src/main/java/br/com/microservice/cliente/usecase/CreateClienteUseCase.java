package br.com.microservice.cliente.usecase;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.domain.value_objects.CPF;
import br.com.microservice.cliente.dto.ClienteDTO;
import br.com.microservice.cliente.dto.usecase.CreateClienteDTO;
import br.com.microservice.cliente.exception.ClienteError;
import br.com.microservice.cliente.gateway.CrudClienteGateway;
import br.com.microservice.cliente.usecase.mapper.ClienteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateClienteUseCase {

    private final CrudClienteGateway gateway;

    public CreateClienteUseCase(CrudClienteGateway gateway) {
        this.gateway = gateway;
    }

    public ClienteDTO create(CreateClienteDTO clienteDTO) {
        var opCliente = gateway.findByCpf(clienteDTO.cpf());

        if(opCliente.isPresent()) {
            log.info(
                    "não foi possivel savar um usuario, CPF: XXX.XXX.{}-XX já utilizado",
                    clienteDTO.cpf().replaceAll("[^0-9]", "").substring(5,9)
            );
            throw new ClienteError.ClienteAlreadyExistsException("CPF já foi utilizado");//TODO implementar erros personalizados
        }

        var cliente = Cliente.criar(
                clienteDTO.nome(),
                new CPF(clienteDTO.cpf()),
                clienteDTO.email(),
                clienteDTO.dataNascimento(),
                clienteDTO.endereco(),
                clienteDTO.telefone()
        );

        return ClienteMapper.mapToDTO(gateway.save(cliente));
    }
}
