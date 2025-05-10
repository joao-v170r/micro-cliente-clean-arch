package br.com.microservice.cliente.gateway.database.mongo;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.exception.ClienteError;
import br.com.microservice.cliente.gateway.database.mongo.entity.ClienteEntity;
import br.com.microservice.cliente.gateway.database.mongo.mapper.ClienteMapper;
import br.com.microservice.cliente.gateway.database.mongo.repository.ClienteRepository;
import br.com.microservice.cliente.gateway.exception.mongo.ClienteMongoError;
import br.com.microservice.cliente.utils.ClienteMockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.AssertionErrors;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
class ClienteMongoGatewayTest {

    @Mock
    ClienteRepository repository;

    @InjectMocks
    ClienteMongoGateway gateway;

    @Test
    void findByCpf() {
        Cliente mock = ClienteMockData.validCliente();

        Mockito.when(repository.findByCpf(Mockito.any(String.class)))
                .thenReturn(Optional.of(ClienteMapper.mapToEntity(mock)));

        var opCliente = gateway.findByCpf(mock.getCpf().numero());

        assertTrue("Verifica função findByCpf do gateway mongo", opCliente.isPresent());
        AssertionErrors.assertEquals(
                "Verifica resultado retornado",
                mock.getId(),
                opCliente.get().getId()
        );
        verify(repository).findByCpf(mock.getCpf().numero());
    }

    @Test
    void findByCpfNull() {
        Assertions.assertThrows(
                ClienteMongoError.ClienteInvalidArgumentException.class,
                () -> gateway.findByCpf(null)
        );
    }

    @Test
    void findByCpfWithRuntimeException() {
        Mockito.when(repository.findByCpf(any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(
                ClienteMongoError.ClientePersistenceException.class,
                () -> gateway.findByCpf(ClienteMockData.validInput().cpf())
        );
    }

    @Test
    void findById() {
        Cliente mock = ClienteMockData.validCliente();

        Mockito.when(repository.findById(Mockito.any(String.class)))
                .thenReturn(Optional.of(ClienteMapper.mapToEntity(mock)));

        var opCliente = gateway.findById(mock.getId());

        assertTrue("Verifica função findByCpf do gateway mongo", opCliente.isPresent());
        AssertionErrors.assertEquals(
                "Verifica resultado retornado",
                mock.getId(),
                opCliente.get().getId()
        );
        verify(repository).findById(mock.getId());
    }

    @Test
    void findByIdNull() {
        Assertions.assertThrows(
                ClienteMongoError.ClienteInvalidArgumentException.class,
                () -> gateway.findById(null)
        );
    }

    @Test
    void findByIdWithRuntimeException() {
        Mockito.when(repository.findById(any())).thenThrow(new RuntimeException());
        Assertions.assertThrows(
                ClienteMongoError.ClientePersistenceException.class,
                () -> gateway.findById(UUID.randomUUID().toString())
        );
    }

    @Test
    void existId() {
        String id = UUID.randomUUID().toString();
        Mockito.when(repository.existsById(id)).thenReturn(true);
        assertTrue("Verifica se existe id", gateway.existId(id));
    }

    @Test
    void existIdWithRuntimeException() {
        String id = UUID.randomUUID().toString();
        Mockito.when(repository.existsById(id)).thenThrow(new RuntimeException());

        Assertions.assertThrows(
                ClienteMongoError.ClientePersistenceException.class,
                () -> gateway.existId(id)
        );
    }

    @Test
    void findAll() {
        Pageable pageable = Pageable.ofSize(10);
        List<ClienteEntity> mockResults = List.of(
                ClienteMockData.validClienteEntity(),
                ClienteMockData.validClienteEntity()
        );
        Mockito.when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(
                mockResults,
                PageRequest.of(0, 10),
                mockResults.size()
        ));

        var result = gateway.findAll(pageable);

        AssertionErrors.assertEquals(
                "Verificando retorno esperado",
                result.size(),
                mockResults.size()
        );

        result.forEach(e -> {
            int index = result.indexOf(e);
            AssertionErrors.assertEquals(
                    "Index: " +index+". Verificando id: " + e.getId() + " dos clientes do resultado",
                    e.getId(),
                    mockResults.get(index).getId()
            );
        });
    }

    @Test
    void save() {
        Cliente mock = ClienteMockData.validCliente();

        Mockito.when(repository.save(any(ClienteEntity.class)))
                .thenAnswer(i -> i.getArgument(0));

        Cliente cliente = gateway.save(mock);

        AssertionErrors.assertEquals(
                "Verificando id do resultado com o esperado",
                cliente.getId(),
                mock.getId()
        );
    }

    @Test
    void saveClienteNull() {
        Assertions.assertThrows(
                ClienteMongoError.ClienteInvalidArgumentException.class,
                () -> gateway.save(null)
        );
    }

    @Test
    void saveWithDataIntegrityViolationException() {
        Mockito.when(repository.save(any()))
                        .thenThrow(new DataIntegrityViolationException("Error Test"));

        Assertions.assertThrows(
                ClienteMongoError.ClienteConflictException.class,
                () -> gateway.save(ClienteMockData.validCliente())
        );
    }

    @Test
    void saveWithException() {
        Mockito.when(repository.save(any()))
                .thenThrow(new RuntimeException());

        Assertions.assertThrows(
                ClienteMongoError.ClientePersistenceException.class,
                () -> gateway.save(ClienteMockData.validCliente())
        );
    }

    @Test
    void deleteById() {
        String id = UUID.randomUUID().toString();

        Mockito.when(repository.existsById(id))
                .thenReturn(true);

        gateway.deleteById(id);
        Mockito.verify(repository).deleteById(any(String.class));

    }

    @Test
    void deleteByIdNull() {
        Assertions.assertThrows(
                ClienteMongoError.ClienteNotFoundException.class,
                () -> gateway.deleteById(null)
        );
    }

    @Test
    void deleteByIdWithException() {
        Mockito.when(repository.existsById(any(String.class)))
                        .thenReturn(true);
        Mockito.doThrow(new RuntimeException()).when(repository).deleteById(any());
        Assertions.assertThrows(
                ClienteMongoError.ClientePersistenceException.class,
                () -> gateway.deleteById(UUID.randomUUID().toString())
        );
    }
}