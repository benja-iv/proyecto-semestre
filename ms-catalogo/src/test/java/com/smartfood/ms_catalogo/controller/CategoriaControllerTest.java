package com.smartfood.ms_catalogo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfood.ms_catalogo.dto.CategoriaRequestDTO;
import com.smartfood.ms_catalogo.dto.CategoriaResponseDTO;
import com.smartfood.ms_catalogo.exception.CategoriaNotFoundException;
import com.smartfood.ms_catalogo.service.CategoriaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
@DisplayName("Tests del CategoriaController")
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("GET /api/categorias debe retornar 200 con lista")
    void obtenerTodas_debeRetornar200() throws Exception {
        when(categoriaService.obtenerTodas()).thenReturn(List.of(new CategoriaResponseDTO(1L, "Pizzas")));
        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Pizzas"));
    }

    @Test
    @DisplayName("GET /api/categorias/{id} debe retornar 200 cuando existe")
    void obtenerPorId_debeRetornar200() throws Exception {
        when(categoriaService.obtenerPorId(1L)).thenReturn(new CategoriaResponseDTO(1L, "Pizzas"));
        mockMvc.perform(get("/api/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pizzas"));
    }

    @Test
    @DisplayName("GET /api/categorias/{id} debe retornar 404 cuando no existe")
    void obtenerPorId_debeRetornar404() throws Exception {
        when(categoriaService.obtenerPorId(99L)).thenThrow(new CategoriaNotFoundException(99L));
        mockMvc.perform(get("/api/categorias/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/categorias debe retornar 201")
    void crearCategoria_debeRetornar201() throws Exception {
        when(categoriaService.crearCategoria(any())).thenReturn(new CategoriaResponseDTO(1L, "Pizzas"));
        mockMvc.perform(post("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CategoriaRequestDTO("Pizzas"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Pizzas"));
    }

    @Test
    @DisplayName("POST /api/categorias debe retornar 400 con nombre vacio")
    void crearCategoria_debeRetornar400_nombreVacio() throws Exception {
        mockMvc.perform(post("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CategoriaRequestDTO(""))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/categorias/{id} debe retornar 200")
    void actualizarCategoria_debeRetornar200() throws Exception {
        when(categoriaService.actualizarCategoria(eq(1L), any())).thenReturn(new CategoriaResponseDTO(1L, "Bebidas"));
        mockMvc.perform(put("/api/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CategoriaRequestDTO("Bebidas"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Bebidas"));
    }
}
