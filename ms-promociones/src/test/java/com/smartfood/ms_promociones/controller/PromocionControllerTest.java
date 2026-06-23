package com.smartfood.ms_promociones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfood.ms_promociones.dto.PromocionRequestDTO;
import com.smartfood.ms_promociones.dto.PromocionResponseDTO;
import com.smartfood.ms_promociones.exception.PromocionInvalidaException;
import com.smartfood.ms_promociones.service.PromocionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PromocionController.class)
@DisplayName("Tests del PromocionController")
class PromocionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PromocionService promocionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("GET /api/promociones/validar/{codigo} debe retornar 200 con codigo valido")
    void validarCodigo_debeRetornar200() throws Exception {
        when(promocionService.validarCodigo("PROMO10"))
                .thenReturn(new PromocionResponseDTO(1L, "PROMO10", 10, true));
        mockMvc.perform(get("/api/promociones/validar/PROMO10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("PROMO10"))
                .andExpect(jsonPath("$.porcentajeDescuento").value(10));
    }

    @Test
    @DisplayName("GET /api/promociones/validar/{codigo} debe retornar 400 con codigo invalido")
    void validarCodigo_debeRetornar400_codigoInvalido() throws Exception {
        when(promocionService.validarCodigo("INVALIDO"))
                .thenThrow(new PromocionInvalidaException("Codigo no valido"));
        mockMvc.perform(get("/api/promociones/validar/INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/promociones debe retornar 200 con datos validos")
    void crearPromocion_debeRetornar200() throws Exception {
        when(promocionService.crearPromocion(any()))
                .thenReturn(new PromocionResponseDTO(1L, "PROMO10", 10, true));
        mockMvc.perform(post("/api/promociones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PromocionRequestDTO("PROMO10", 10))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activa").value(true));
    }

    @Test
    @DisplayName("POST /api/promociones debe retornar 400 con datos invalidos")
    void crearPromocion_debeRetornar400() throws Exception {
        mockMvc.perform(post("/api/promociones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PromocionRequestDTO("", 0))))
                .andExpect(status().isBadRequest());
    }
}
