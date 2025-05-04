package br.com.microservice.cliente.controler;

import br.com.microservice.cliente.domain.Cliente;
import br.com.microservice.cliente.dto.rest_controller.InputCreateClienteDTO;
import br.com.microservice.cliente.gateway.CrudClienteGateway;
import br.com.microservice.cliente.usecase.CreateClienteUseCase;
import br.com.microservice.cliente.usecase.DeleteClienteUseCase;
import br.com.microservice.cliente.utils.ClienteMockData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeleteClienteController.class)
@AutoConfigureMockMvc
@Import(DeleteClienteUseCase.class)
class DeleteClienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CrudClienteGateway gateway;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createSucess() throws Exception {
        InputCreateClienteDTO input = ClienteMockData.validInput();

        mockMvc.perform(
                        delete("/delete-cliente/{id}", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent());
    }

    @Test
    void createWithClienteAlreadyExistsException() throws Exception {
        InputCreateClienteDTO input = ClienteMockData.validInput();
        Cliente clienteMock = ClienteMockData.validCliente();

        when(gateway.findByCpf(any()))
                .thenReturn(Optional.of(clienteMock));

        mockMvc.perform(
                        post("/create-cliente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input))
                ).andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("CPF já foi utilizado")));
    }
}