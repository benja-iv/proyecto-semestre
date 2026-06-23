package com.smartfood.ms_carrito.service;

import com.smartfood.ms_carrito.client.ProductoClient;
import com.smartfood.ms_carrito.dto.ItemCarritoRequestDTO;
import com.smartfood.ms_carrito.dto.ProductoResponseDTO;
import com.smartfood.ms_carrito.exception.ItemCarritoNotFoundException;
import com.smartfood.ms_carrito.model.ItemCarrito;
import com.smartfood.ms_carrito.repository.ItemCarritoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios de CarritoService")
class CarritoServiceTest {

    @Mock
    private ItemCarritoRepository repository;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private CarritoService carritoService;

    private ItemCarrito itemEjemplo;
    private ProductoResponseDTO productoResponse;

    @BeforeEach
    void setUp() {
        itemEjemplo = new ItemCarrito(1L, 1L, 1L, 2);
        productoResponse = new ProductoResponseDTO(1L, "Hamburguesa", 5000.0, 50);
    }

    @Test
    @DisplayName("obtenerPorCliente() debe retornar items del cliente")
    void obtenerPorCliente_debeRetornarItems() {
        when(repository.findByClienteId(1L)).thenReturn(List.of(itemEjemplo));
        List<ItemCarrito> resultado = carritoService.obtenerPorCliente(1L);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getClienteId());
    }

    @Test
    @DisplayName("obtenerPorCliente() debe retornar lista vacia si no hay items")
    void obtenerPorCliente_listaVacia() {
        when(repository.findByClienteId(99L)).thenReturn(List.of());
        assertTrue(carritoService.obtenerPorCliente(99L).isEmpty());
    }

    @Test
    @DisplayName("agregarItem() debe guardar item cuando producto tiene stock")
    void agregarItem_debeGuardar_conStockSuficiente() {
        when(productoClient.obtenerProductoPorId(1L)).thenReturn(productoResponse);
        when(repository.save(any(ItemCarrito.class))).thenReturn(itemEjemplo);

        ItemCarrito resultado = carritoService.agregarItem(new ItemCarritoRequestDTO(1L, 1L, 2));

        assertNotNull(resultado);
        assertEquals(1L, resultado.getClienteId());
        assertEquals(2, resultado.getCantidad());
        verify(repository, times(1)).save(any(ItemCarrito.class));
    }

    @Test
    @DisplayName("agregarItem() debe lanzar excepcion cuando no hay stock suficiente")
    void agregarItem_debeLanzarExcepcion_sinStock() {
        ProductoResponseDTO sinStock = new ProductoResponseDTO(1L, "Hamburguesa", 5000.0, 1);
        when(productoClient.obtenerProductoPorId(1L)).thenReturn(sinStock);

        assertThrows(RuntimeException.class,
                () -> carritoService.agregarItem(new ItemCarritoRequestDTO(1L, 1L, 10)));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("agregarItem() debe lanzar excepcion si ms-catalogo no responde")
    void agregarItem_debeLanzarExcepcion_siFeignFalla() {
        when(productoClient.obtenerProductoPorId(1L)).thenThrow(new RuntimeException("Servicio no disponible"));
        assertThrows(RuntimeException.class,
                () -> carritoService.agregarItem(new ItemCarritoRequestDTO(1L, 1L, 2)));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("eliminarItem() debe eliminar item cuando existe")
    void eliminarItem_debeEliminar_cuandoExiste() {
        when(repository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> carritoService.eliminarItem(1L));
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("eliminarItem() debe lanzar excepcion cuando no existe")
    void eliminarItem_debeLanzarExcepcion_cuandoNoExiste() {
        when(repository.existsById(99L)).thenReturn(false);
        assertThrows(ItemCarritoNotFoundException.class, () -> carritoService.eliminarItem(99L));
        verify(repository, never()).deleteById(any());
    }
}
