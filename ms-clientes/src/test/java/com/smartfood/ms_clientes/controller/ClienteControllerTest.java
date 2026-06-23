package com.smartfood.ms_clientes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfood.ms_clientes.dto.ClienteRequestDTO;
import com.smartfood.ms_clientes.dto.ClienteResponseDTO;
import com.smartfood.ms_clientes.exception.ClienteNotFoundException;
import com.smartfood.ms_clientes.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
@DisplayName("Tests del ClienteController con MockMvc")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private ClienteResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ClienteResponseDTO(1L, "Ana Morales", "ana@email.com", "+56912345678", "Av. Principal 123", LocalDateTime.now());
    }

    @Test
    @DisplayName("GET /api/clientes debe retornar 200 con lista de clientes")
    void obtenerTodos_debeRetornar200() throws Exception {
        when(clienteService.obtenerTodos()).thenReturn(List.of(responseDTO));
        mockMvc.perform(get("/api/clientes").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombre").value("Ana Morales"))
                .andExpect(jsonPath("$[0].email").value("ana@email.com"));
    }

    @Test
    @DisplayName("GET /api/clientes/{id} debe retornar 200 cuando existe")
    void obtenerPorId_debeRetornar200_cuandoExiste() throws Exception {
        when(clienteService.obtenerPorId(1L)).thenReturn(responseDTO);
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana Morales"));
    }

    @Test
    @DisplayName("GET /api/clientes/{id} debe retornar 404 cuando no existe")
    void obtenerPorId_debeRetornar404_cuandoNoExiste() throws Exception {
        when(clienteService.obtenerPorId(99L)).thenThrow(new ClienteNotFoundException(99L));
        mockMvc.perform(get("/api/clientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/clientes debe retornar 201 con datos validos")
    void registrarCliente_debeRetornar201() throws Exception {
        ClienteRequestDTO request = new ClienteRequestDTO("Ana Morales", "ana@email.com", "+56912345678", "Av. Principal 123");
        when(clienteService.registrarCliente(any(ClienteRequestDTO.class))).thenReturn(responseDTO);
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Ana Morales"));
    }

    @Test
    @DisplayName("POST /api/clientes debe retornar 400 con datos invalidos")
    void registrarCliente_debeRetornar400_cuandoDatosInvalidos() throws Exception {
        ClienteRequestDTO requestInvalido = new ClienteRequestDTO("", "no-es-email", "", "");
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/clientes/{id} debe retornar 200 cuando existe")
    void actualizarCliente_debeRetornar200() throws Exception {
        ClienteRequestDTO request = new ClienteRequestDTO("Ana Morales", "ana@email.com", "+56912345678", "Av. Principal 123");
        when(clienteService.actualizarCliente(eq(1L), any(ClienteRequestDTO.class))).thenReturn(responseDTO);
        mockMvc.perform(put("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana Morales"));
    }
}
