package com.smartfood.ms_pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfood.ms_pedidos.dto.PedidoRequestDTO;
import com.smartfood.ms_pedidos.dto.PedidoResponseDTO;
import com.smartfood.ms_pedidos.exception.PedidoNotFoundException;
import com.smartfood.ms_pedidos.service.PedidoService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
@DisplayName("Tests del PedidoController")
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private PedidoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new PedidoResponseDTO(1L, "Juan Perez", "2 Hamburguesas", 15000.0, "PENDIENTE", LocalDateTime.now());
    }

    @Test
    @DisplayName("GET /api/pedidos debe retornar 200 con lista")
    void obtenerTodos_debeRetornar200() throws Exception {
        when(pedidoService.obtenerTodos()).thenReturn(List.of(responseDTO));
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cliente").value("Juan Perez"));
    }

    @Test
    @DisplayName("GET /api/pedidos/{id} debe retornar 200 cuando existe")
    void obtenerPorId_debeRetornar200() throws Exception {
        when(pedidoService.obtenerPorId(1L)).thenReturn(responseDTO);
        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    @DisplayName("GET /api/pedidos/{id} debe retornar 404 cuando no existe")
    void obtenerPorId_debeRetornar404() throws Exception {
        when(pedidoService.obtenerPorId(99L)).thenThrow(new PedidoNotFoundException(99L));
        mockMvc.perform(get("/api/pedidos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/pedidos debe retornar 201 con datos validos")
    void crearPedido_debeRetornar201() throws Exception {
        PedidoRequestDTO request = new PedidoRequestDTO("Juan Perez", "2 Hamburguesas", 15000.0);
        when(pedidoService.crearPedido(any())).thenReturn(responseDTO);
        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cliente").value("Juan Perez"));
    }

    @Test
    @DisplayName("PUT /api/pedidos/{id}/estado debe retornar 204")
    void actualizarEstado_debeRetornar204() throws Exception {
        doNothing().when(pedidoService).actualizarEstado(1L, "PAGADO");
        mockMvc.perform(put("/api/pedidos/1/estado").param("estado", "PAGADO"))
                .andExpect(status().isNoContent());
    }
}
