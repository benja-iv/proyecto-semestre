package com.smartfood.ms_reportes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfood.ms_reportes.dto.ReporteRequestDTO;
import com.smartfood.ms_reportes.dto.ReporteResponseDTO;
import com.smartfood.ms_reportes.service.ReporteService;
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

@WebMvcTest(ReporteController.class)
@DisplayName("Tests del ReporteController")
class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReporteService reporteService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    @DisplayName("POST /api/reportes/generar debe retornar 200 con datos validos")
    void generar_debeRetornar200() throws Exception {
        when(reporteService.generarReporte(any()))
                .thenReturn(new ReporteResponseDTO(1L, "VENTAS_DIARIAS", LocalDateTime.now(), "COMPLETADO"));
        mockMvc.perform(post("/api/reportes/generar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ReporteRequestDTO("VENTAS_DIARIAS"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoReporte").value("VENTAS_DIARIAS"))
                .andExpect(jsonPath("$.estado").value("COMPLETADO"));
    }

    @Test
    @DisplayName("POST /api/reportes/generar debe retornar 200 con estado FALLIDO")
    void generar_debeRetornar200_estadoFallido() throws Exception {
        when(reporteService.generarReporte(any()))
                .thenReturn(new ReporteResponseDTO(2L, "VENTAS_DIARIAS", LocalDateTime.now(), "FALLIDO"));
        mockMvc.perform(post("/api/reportes/generar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ReporteRequestDTO("VENTAS_DIARIAS"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("FALLIDO"));
    }

    @Test
    @DisplayName("POST /api/reportes/generar debe retornar 400 con tipo vacio")
    void generar_debeRetornar400_tipoVacio() throws Exception {
        mockMvc.perform(post("/api/reportes/generar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ReporteRequestDTO(""))))
                .andExpect(status().isBadRequest());
    }
}
