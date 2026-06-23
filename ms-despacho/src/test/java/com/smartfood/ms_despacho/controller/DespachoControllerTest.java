package com.smartfood.ms_despacho.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfood.ms_despacho.dto.DespachoRequestDTO;
import com.smartfood.ms_despacho.dto.DespachoResponseDTO;
import com.smartfood.ms_despacho.exception.DespachoNotFoundException;
import com.smartfood.ms_despacho.service.DespachoService;
import org.junit.jupiter.api.BeforeEach;
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

@WebMvcTest(DespachoController.class)
@DisplayName("Tests del DespachoController")
class DespachoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DespachoService despachoService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private DespachoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new DespachoResponseDTO(1L, 1L, "PREPARANDO", "Av. Principal 123", LocalDateTime.now());
    }

    @Test
    @DisplayName("POST /api/despachos debe retornar 201")
    void crearDespacho_debeRetornar201() throws Exception {
        when(despachoService.registrarDespacho(any())).thenReturn(responseDTO);
        mockMvc.perform(post("/api/despachos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new DespachoRequestDTO(1L, "Av. Principal 123"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("PREPARANDO"));
    }

    @Test
    @DisplayName("POST /api/despachos debe retornar 400 con datos invalidos")
    void crearDespacho_debeRetornar400() throws Exception {
        mockMvc.perform(post("/api/despachos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new DespachoRequestDTO(null, ""))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/despachos/{id} debe retornar 200 cuando existe")
    void obtenerPorId_debeRetornar200() throws Exception {
        when(despachoService.obtenerPorId(1L)).thenReturn(responseDTO);
        mockMvc.perform(get("/api/despachos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("PREPARANDO"));
    }

    @Test
    @DisplayName("GET /api/despachos/{id} debe retornar 404 cuando no existe")
    void obtenerPorId_debeRetornar404() throws Exception {
        when(despachoService.obtenerPorId(99L)).thenThrow(new DespachoNotFoundException(99L));
        mockMvc.perform(get("/api/despachos/99"))
                .andExpect(status().isNotFound());
    }
}
