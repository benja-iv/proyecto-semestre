package com.smartfood.ms_pagos.service;

import com.smartfood.ms_pagos.client.PedidoClient;
import com.smartfood.ms_pagos.dto.PagoRequestDTO;
import com.smartfood.ms_pagos.dto.PagoResponseDTO;
import com.smartfood.ms_pagos.exception.PagoNotFoundException;
import com.smartfood.ms_pagos.model.Pago;
import com.smartfood.ms_pagos.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios de PagoService")
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PedidoClient pedidoClient;

    @InjectMocks
    private PagoService pagoService;

    private Pago pagoEjemplo;

    @BeforeEach
    void setUp() {
        pagoEjemplo = new Pago(1L, 1L, 15000.0, "TARJETA_CREDITO", "APROBADO", LocalDateTime.now());
    }

    @Test
    @DisplayName("obtenerTodos() debe retornar lista de pagos")
    void obtenerTodos_debeRetornarLista() {
        when(pagoRepository.findAll()).thenReturn(List.of(pagoEjemplo));
        List<PagoResponseDTO> resultado = pagoService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("APROBADO", resultado.get(0).getEstado());
    }

    @Test
    @DisplayName("obtenerTodos() debe retornar lista vacia")
    void obtenerTodos_listaVacia() {
        when(pagoRepository.findAll()).thenReturn(List.of());
        assertTrue(pagoService.obtenerTodos().isEmpty());
    }

    @Test
    @DisplayName("obtenerPorId() debe retornar pago cuando existe")
    void obtenerPorId_debeRetornarPago() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoEjemplo));
        PagoResponseDTO resultado = pagoService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals("APROBADO", resultado.getEstado());
        assertEquals(15000.0, resultado.getMonto());
    }

    @Test
    @DisplayName("obtenerPorId() debe lanzar excepcion cuando no existe")
    void obtenerPorId_debeLanzarExcepcion() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(PagoNotFoundException.class, () -> pagoService.obtenerPorId(99L));
    }

    @Test
    @DisplayName("procesarPago() debe guardar pago con estado APROBADO")
    void procesarPago_debeGuardarConEstadoAprobado() {
        doNothing().when(pedidoClient).actualizarEstado(any(), any());
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoEjemplo);
        PagoResponseDTO resultado = pagoService.procesarPago(new PagoRequestDTO(1L, 15000.0, "TARJETA_CREDITO"));
        assertNotNull(resultado);
        assertEquals("APROBADO", resultado.getEstado());
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }
}
