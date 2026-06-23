package com.smartfood.ms_inventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfood.ms_inventario.dto.InventarioRequestDTO;
import com.smartfood.ms_inventario.dto.InventarioResponseDTO;
import com.smartfood.ms_inventario.exception.InventarioNotFoundException;
import com.smartfood.ms_inventario.service.InventarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventarioController.class)
@DisplayName("Tests del InventarioController")
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventarioService inventarioService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    @DisplayName("GET /api/inventario/producto/{id} debe retornar 200 cuando existe")
    void consultar_debeRetornar200() throws Exception {
        when(inventarioService.consultarPorProductoId(1L))
            .thenReturn(new InventarioResponseDTO(1L, 1L, 50, LocalDateTime.now()));
        mockMvc.perform(get("/api/inventario/producto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadDisponible").value(50));
    }

    @Test
    @DisplayName("GET /api/inventario/producto/{id} debe retornar 404 cuando no existe")
    void consultar_debeRetornar404() throws Exception {
        when(inventarioService.consultarPorProductoId(99L)).thenThrow(new InventarioNotFoundException(99L));
        mockMvc.perform(get("/api/inventario/producto/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/inventario debe retornar 200")
    void registrar_debeRetornar200() throws Exception {
        when(inventarioService.registrarOActualizar(any()))
            .thenReturn(new InventarioResponseDTO(1L, 1L, 100, LocalDateTime.now()));
        mockMvc.perform(post("/api/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new InventarioRequestDTO(1L, 100))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadDisponible").value(100));
    }
}
