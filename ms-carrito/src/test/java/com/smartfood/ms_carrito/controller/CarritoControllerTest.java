package com.smartfood.ms_carrito.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfood.ms_carrito.dto.ItemCarritoRequestDTO;
import com.smartfood.ms_carrito.exception.ItemCarritoNotFoundException;
import com.smartfood.ms_carrito.model.ItemCarrito;
import com.smartfood.ms_carrito.service.CarritoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarritoController.class)
@DisplayName("Tests del CarritoController")
class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarritoService carritoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("GET /api/carrito/cliente/{id} debe retornar 200 con lista")
    void obtenerPorCliente_debeRetornar200() throws Exception {
        when(carritoService.obtenerPorCliente(1L))
                .thenReturn(List.of(new ItemCarrito(1L, 1L, 1L, 2)));
        mockMvc.perform(get("/api/carrito/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clienteId").value(1))
                .andExpect(jsonPath("$[0].cantidad").value(2));
    }

    @Test
    @DisplayName("GET /api/carrito/cliente/{id} debe retornar 200 con lista vacia")
    void obtenerPorCliente_listaVacia() throws Exception {
        when(carritoService.obtenerPorCliente(99L)).thenReturn(List.of());
        mockMvc.perform(get("/api/carrito/cliente/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("POST /api/carrito debe retornar 200 con datos validos")
    void agregarItem_debeRetornar200() throws Exception {
        when(carritoService.agregarItem(any())).thenReturn(new ItemCarrito(1L, 1L, 1L, 2));
        mockMvc.perform(post("/api/carrito")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ItemCarritoRequestDTO(1L, 1L, 2))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    @DisplayName("POST /api/carrito debe retornar 400 con datos invalidos")
    void agregarItem_debeRetornar400() throws Exception {
        mockMvc.perform(post("/api/carrito")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ItemCarritoRequestDTO(null, null, 0))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/carrito/{id} debe retornar 204 cuando existe")
    void eliminarItem_debeRetornar204() throws Exception {
        doNothing().when(carritoService).eliminarItem(1L);
        mockMvc.perform(delete("/api/carrito/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/carrito/{id} debe retornar 404 cuando no existe")
    void eliminarItem_debeRetornar404() throws Exception {
        doThrow(new ItemCarritoNotFoundException(99L)).when(carritoService).eliminarItem(99L);
        mockMvc.perform(delete("/api/carrito/99"))
                .andExpect(status().isNotFound());
    }
}
