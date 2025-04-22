package br.com.microservice.cliente.usecase;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.dto.ClienteDTO;
import br.com.microservice.cliente.dto.usecase.CriarClienteDTO;
import br.com.microservice.cliente.gateway.CrudClienteGateway;
import br.com.microservice.cliente.usecase.mapper.ClienteMapper;
import org.springframework.stereotype.Service;

@Service
public class CreateClienteUseCase {

    private final CrudClienteGateway gateway;

    public CreateClienteUseCase(CrudClienteGateway gateway) {
        this.gateway = gateway;
    }

    public ClienteDTO create(CriarClienteDTO clienteDTO) {
        var opCliente = gateway.findByCpf(clienteDTO.cpf());

        if(opCliente.isPresent()) {
            throw new RuntimeException("CPF já foi utilizado");//TODO implementar erros personalizados
        }

        var cliente = Cliente.criar(
                clienteDTO.nome(),
                clienteDTO.cpf(),
                clienteDTO.email(),
                clienteDTO.dataNascimento(),
                clienteDTO.endereco(),
                clienteDTO.telefone()
        );

        return ClienteMapper.mapToDTO(gateway.save(cliente));
    }
}
