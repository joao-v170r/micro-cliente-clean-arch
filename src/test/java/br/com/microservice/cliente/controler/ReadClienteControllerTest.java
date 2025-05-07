package br.com.microservice.cliente.controler;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.gateway.CrudClienteGateway;
import br.com.microservice.cliente.usecase.ReadClienteUserCase;
import br.com.microservice.cliente.usecase.mapper.ClienteMapper;
import br.com.microservice.cliente.utils.ClienteMockData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ReadClienteController.class)
@AutoConfigureMockMvc
@Import(ReadClienteUserCase.class)
public class ReadClienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CrudClienteGateway gateway;

    @Autowired
    ObjectMapper mapper;

    @Test
    void readFindClienteSucess() throws Exception {
        Cliente mock = ClienteMockData.validCliente();

        when(gateway.findById(any()))
                .thenReturn(Optional.of(mock));

        String resultExpectedJson = mapper.writeValueAsString(ClienteMapper.mapToDTO(mock));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cliente/{id}", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultExpectedJson));
    }

    @Test
    void readFindAllSucess() throws Exception {
        Cliente mock = ClienteMockData.validCliente();

        when(gateway.findAll(any()))
                .thenReturn(List.of(mock));

        String resultExpectedJson = mapper.writeValueAsString(List.of(ClienteMapper.mapToDTO(mock)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/cliente")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultExpectedJson));
    }

    @Test
    void readFindClienteWithClienteNotFoundException() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/cliente/{id}", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Cliente não encontrado"));
    }

    @Test
    void readFindAllEmptyClientes() throws Exception {

        String resultExpectedJson = mapper.writeValueAsString(List.of());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/cliente")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultExpectedJson));
    }
}
