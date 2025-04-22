package br.com.microservice.cliente.gateway.database.mongo.repository;

import br.com.microservice.cliente.gateway.database.mongo.entity.ClienteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends MongoRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByCpf(String cpf);
    void deleteById(UUID id);
}
