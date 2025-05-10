package br.com.microservice.cliente.gateway.database.mongo;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.gateway.CrudClienteGateway;
import br.com.microservice.cliente.gateway.database.mongo.entity.ClienteEntity;
import br.com.microservice.cliente.gateway.database.mongo.mapper.ClienteMapper;
import br.com.microservice.cliente.gateway.database.mongo.repository.ClienteRepository;
import br.com.microservice.cliente.gateway.exception.mongo.ClienteMongoError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClienteMongoGateway implements CrudClienteGateway {

    private final ClienteRepository repository;

    @Override
    public Optional<Cliente> findByCpf(String cpf) {

        if (cpf == null || cpf.isBlank()) {
            throw new ClienteMongoError.ClienteInvalidArgumentException("cpf não pode ser vazio ou nulo.");
        }

        try {
            Optional<ClienteEntity> entity = repository.findByCpf(cpf);
            return entity.map(ClienteMapper::mapToDomain);

        } catch (Exception e) {
            log.error("Falha ao buscar cliente por CPF: {}", cpf, e);
            throw new ClienteMongoError.ClientePersistenceException("erro ao acessar o banco de dados.", e);
        }
    }

    @Override
    public Optional<Cliente> findById(String id) {

        if (id == null || id.isBlank()) {
            throw new ClienteMongoError.ClienteInvalidArgumentException("id do cliente inválido.");
        }

        try {
            Optional<ClienteEntity> entity = repository.findById(id);
            return entity.map(ClienteMapper::mapToDomain);

        } catch (Exception e) {
            log.error("Falha ao buscar cliente por ID: {}", id, e);
            throw new ClienteMongoError.ClientePersistenceException("erro ao acessar o banco de dados.", e);
        }
    }

    @Override
    public Boolean existId(String id) {
        try {
            return repository.existsById(id);
        } catch (Exception e) {
            log.error("Falha ao verificar existência do cliente com ID: {}", id, e);
            throw new ClienteMongoError.ClientePersistenceException("erro ao acessar o banco de dados.", e);
        }
    }

    @Override
    public List<Cliente> findAll() {
        try {
            return repository.findAll()
                    .stream()
                    .map(ClienteMapper::mapToDomain)
                    .toList();
        } catch (Exception e) {
            log.error("Falha ao listar clientes.", e);
            throw new ClienteMongoError.ClientePersistenceException("erro ao acessar o banco de dados.", e);
        }
    }

    @Override
    public Cliente save(Cliente cliente) {

        if (cliente == null) {
            throw new ClienteMongoError.ClienteInvalidArgumentException("cliente não pode ser nulo.");
        }
        try {
            ClienteEntity entity = ClienteMapper.mapToEntity(cliente);
            ClienteEntity savedEntity = repository.save(entity);
            return ClienteMapper.mapToDomain(savedEntity);
        } catch (DataIntegrityViolationException e) {
            log.error("Conflito ao salvar cliente: {}", cliente.getEmail(), e);
            throw new ClienteMongoError.ClienteConflictException("cliente já cadastrado com este CPF.");
        } catch (Exception e) {
            log.error("Falha ao salvar cliente.", e);
            throw new ClienteMongoError.ClientePersistenceException("erro ao persistir cliente.", e);
        }
    }

    @Override
    public void deleteById(String id) {

        if (!repository.existsById(id)) {
            throw new ClienteMongoError.ClienteNotFoundException("cliente não encontrado para exclusão.");
        }

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            log.error("Falha ao excluir cliente com ID: {}", id, e);
            throw new ClienteMongoError.ClientePersistenceException("erro ao excluir cliente.", e);
        }
    }

    @Override
    public List<Cliente> findAll(Pageable page) {
        return repository.findAll(page).map(ClienteMapper::mapToDomain).stream().toList();
    }
}