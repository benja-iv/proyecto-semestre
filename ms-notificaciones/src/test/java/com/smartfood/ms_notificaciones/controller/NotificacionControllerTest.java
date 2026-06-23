package com.smartfood.ms_notificaciones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfood.ms_notificaciones.dto.NotificacionRequestDTO;
import com.smartfood.ms_notificaciones.dto.NotificacionResponseDTO;
import com.smartfood.ms_notificaciones.service.NotificacionService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificacionController.class)
@DisplayName("Tests del NotificacionController")
class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificacionService notificacionService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private NotificacionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new NotificacionResponseDTO(1L, 1L, "Tu pedido fue recibido", LocalDateTime.now());
    }

    @Test
    @DisplayName("POST /api/notificaciones debe retornar 200")
    void enviar_debeRetornar200() throws Exception {
        when(notificacionService.enviarNotificacion(any())).thenReturn(responseDTO);
        mockMvc.perform(post("/api/notificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new NotificacionRequestDTO(1L, "Tu pedido fue recibido"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Tu pedido fue recibido"));
    }

    @Test
    @DisplayName("POST /api/notificaciones debe retornar 400 con datos invalidos")
    void enviar_debeRetornar400_datosInvalidos() throws Exception {
        mockMvc.perform(post("/api/notificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new NotificacionRequestDTO(null, ""))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/notificaciones/cliente/{id} debe retornar 200 con lista")
    void obtenerPorCliente_debeRetornar200() throws Exception {
        when(notificacionService.obtenerPorCliente(1L)).thenReturn(List.of(responseDTO));
        mockMvc.perform(get("/api/notificaciones/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clienteId").value(1));
    }

    @Test
    @DisplayName("GET /api/notificaciones/cliente/{id} debe retornar lista vacia")
    void obtenerPorCliente_listaVacia() throws Exception {
        when(notificacionService.obtenerPorCliente(eq(99L))).thenReturn(List.of());
        mockMvc.perform(get("/api/notificaciones/cliente/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
