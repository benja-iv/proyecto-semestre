package com.smartfood.ms_inventario.service;

import com.smartfood.ms_inventario.dto.InventarioRequestDTO;
import com.smartfood.ms_inventario.dto.InventarioResponseDTO;
import com.smartfood.ms_inventario.exception.InventarioNotFoundException;
import com.smartfood.ms_inventario.model.Inventario;
import com.smartfood.ms_inventario.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios de InventarioService")
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    private Inventario inventarioEjemplo;

    @BeforeEach
    void setUp() {
        inventarioEjemplo = new Inventario(1L, 10L, 50, LocalDateTime.now());
    }

    @Test
    @DisplayName("consultarPorProductoId() debe retornar inventario cuando existe")
    void consultar_debeRetornarInventario() {
        when(inventarioRepository.findByProductoId(10L)).thenReturn(Optional.of(inventarioEjemplo));
        InventarioResponseDTO resultado = inventarioService.consultarPorProductoId(10L);
        assertNotNull(resultado);
        assertEquals(10L, resultado.getProductoId());
        assertEquals(50, resultado.getCantidadDisponible());
    }

    @Test
    @DisplayName("consultarPorProductoId() debe lanzar excepcion cuando no existe")
    void consultar_debeLanzarExcepcion() {
        when(inventarioRepository.findByProductoId(99L)).thenReturn(Optional.empty());
        assertThrows(InventarioNotFoundException.class, () -> inventarioService.consultarPorProductoId(99L));
    }

    @Test
    @DisplayName("registrarOActualizar() debe actualizar inventario existente")
    void registrarOActualizar_debeActualizar() {
        when(inventarioRepository.findByProductoId(10L)).thenReturn(Optional.of(inventarioEjemplo));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioEjemplo);
        InventarioResponseDTO resultado = inventarioService.registrarOActualizar(new InventarioRequestDTO(10L, 100));
        assertNotNull(resultado);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    @DisplayName("registrarOActualizar() debe crear nuevo inventario si no existe")
    void registrarOActualizar_debeCrear() {
        when(inventarioRepository.findByProductoId(20L)).thenReturn(Optional.empty());
        Inventario nuevo = new Inventario(2L, 20L, 30, LocalDateTime.now());
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(nuevo);
        InventarioResponseDTO resultado = inventarioService.registrarOActualizar(new InventarioRequestDTO(20L, 30));
        assertNotNull(resultado);
        assertEquals(20L, resultado.getProductoId());
    }
}
