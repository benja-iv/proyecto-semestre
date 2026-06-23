package com.smartfood.ms_pagos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfood.ms_pagos.dto.PagoRequestDTO;
import com.smartfood.ms_pagos.dto.PagoResponseDTO;
import com.smartfood.ms_pagos.exception.PagoNotFoundException;
import com.smartfood.ms_pagos.service.PagoService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagoController.class)
@DisplayName("Tests del PagoController")
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService pagoService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private PagoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new PagoResponseDTO(1L, 1L, 15000.0, "TARJETA_CREDITO", "APROBADO", LocalDateTime.now());
    }

    @Test
    @DisplayName("GET /api/pagos debe retornar 200 con lista")
    void obtenerTodos_debeRetornar200() throws Exception {
        when(pagoService.obtenerTodos()).thenReturn(List.of(responseDTO));
        mockMvc.perform(get("/api/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("APROBADO"));
    }

    @Test
    @DisplayName("GET /api/pagos/{id} debe retornar 200 cuando existe")
    void obtenerPorId_debeRetornar200() throws Exception {
        when(pagoService.obtenerPorId(1L)).thenReturn(responseDTO);
        mockMvc.perform(get("/api/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monto").value(15000.0));
    }

    @Test
    @DisplayName("GET /api/pagos/{id} debe retornar 404 cuando no existe")
    void obtenerPorId_debeRetornar404() throws Exception {
        when(pagoService.obtenerPorId(99L)).thenThrow(new PagoNotFoundException(99L));
        mockMvc.perform(get("/api/pagos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/pagos debe retornar 201")
    void procesarPago_debeRetornar201() throws Exception {
        when(pagoService.procesarPago(any())).thenReturn(responseDTO);
        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PagoRequestDTO(1L, 15000.0, "TARJETA_CREDITO"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("APROBADO"));
    }

    @Test
    @DisplayName("POST /api/pagos debe retornar 400 con datos invalidos")
    void procesarPago_debeRetornar400() throws Exception {
        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PagoRequestDTO(null, -100.0, ""))))
                .andExpect(status().isBadRequest());
    }
}
