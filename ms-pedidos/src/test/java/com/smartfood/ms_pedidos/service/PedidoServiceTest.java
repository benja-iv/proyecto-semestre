package com.smartfood.ms_pedidos.service;

import com.smartfood.ms_pedidos.dto.PedidoRequestDTO;
import com.smartfood.ms_pedidos.dto.PedidoResponseDTO;
import com.smartfood.ms_pedidos.exception.PedidoNotFoundException;
import com.smartfood.ms_pedidos.model.Pedido;
import com.smartfood.ms_pedidos.repository.PedidoRepository;
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
@DisplayName("Tests unitarios de PedidoService")
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedidoEjemplo;
    private PedidoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        pedidoEjemplo = new Pedido(1L, "Juan Perez", "2 Hamburguesas", 15000.0, "PENDIENTE", LocalDateTime.now());
        requestDTO = new PedidoRequestDTO("Juan Perez", "2 Hamburguesas", 15000.0);
    }

    @Test
    @DisplayName("obtenerTodos() debe retornar lista de pedidos")
    void obtenerTodos_debeRetornarLista() {
        when(pedidoRepository.findAll()).thenReturn(List.of(pedidoEjemplo));
        List<PedidoResponseDTO> resultado = pedidoService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).getCliente());
    }

    @Test
    @DisplayName("obtenerTodos() debe retornar lista vacia")
    void obtenerTodos_listaVacia() {
        when(pedidoRepository.findAll()).thenReturn(List.of());
        assertTrue(pedidoService.obtenerTodos().isEmpty());
    }

    @Test
    @DisplayName("obtenerPorId() debe retornar pedido cuando existe")
    void obtenerPorId_debeRetornarPedido() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoEjemplo));
        PedidoResponseDTO resultado = pedidoService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getCliente());
        assertEquals("PENDIENTE", resultado.getEstado());
    }

    @Test
    @DisplayName("obtenerPorId() debe lanzar excepcion cuando no existe")
    void obtenerPorId_debeLanzarExcepcion() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(PedidoNotFoundException.class, () -> pedidoService.obtenerPorId(99L));
    }

    @Test
    @DisplayName("crearPedido() debe crear con estado PENDIENTE")
    void crearPedido_debeCrearConEstadoPendiente() {
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoEjemplo);
        PedidoResponseDTO resultado = pedidoService.crearPedido(requestDTO);
        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("actualizarEstado() debe cambiar el estado del pedido")
    void actualizarEstado_debeCambiarEstado() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoEjemplo));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoEjemplo);
        assertDoesNotThrow(() -> pedidoService.actualizarEstado(1L, "PAGADO"));
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("actualizarEstado() debe lanzar excepcion si pedido no existe")
    void actualizarEstado_debeLanzarExcepcion() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(PedidoNotFoundException.class, () -> pedidoService.actualizarEstado(99L, "PAGADO"));
    }
}
