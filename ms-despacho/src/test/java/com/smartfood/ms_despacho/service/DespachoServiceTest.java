package com.smartfood.ms_despacho.service;

import com.smartfood.ms_despacho.client.NotificacionClient;
import com.smartfood.ms_despacho.client.PedidoClient;
import com.smartfood.ms_despacho.dto.DespachoRequestDTO;
import com.smartfood.ms_despacho.dto.DespachoResponseDTO;
import com.smartfood.ms_despacho.dto.PedidoResponseDTO;
import com.smartfood.ms_despacho.exception.DespachoNotFoundException;
import com.smartfood.ms_despacho.model.Despacho;
import com.smartfood.ms_despacho.repository.DespachoRepository;
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
@DisplayName("Tests unitarios de DespachoService")
class DespachoServiceTest {

    @Mock
    private DespachoRepository repository;

    @Mock
    private PedidoClient pedidoClient;

    @Mock
    private NotificacionClient notificacionClient;

    @InjectMocks
    private DespachoService despachoService;

    private Despacho despachoEjemplo;
    private PedidoResponseDTO pedidoResponse;

    @BeforeEach
    void setUp() {
        despachoEjemplo = new Despacho(1L, 1L, "PREPARANDO", "Av. Principal 123", LocalDateTime.now());
        pedidoResponse = new PedidoResponseDTO(1L, 1L, 15000.0, "PENDIENTE");
    }

    @Test
    @DisplayName("registrarDespacho() debe guardar y retornar el despacho")
    void registrarDespacho_debeGuardarYRetornar() {
        when(pedidoClient.obtenerPedidoPorId(1L)).thenReturn(pedidoResponse);
        when(repository.save(any(Despacho.class))).thenReturn(despachoEjemplo);
        doNothing().when(notificacionClient).enviar(any());

        DespachoResponseDTO resultado = despachoService.registrarDespacho(
                new DespachoRequestDTO(1L, "Av. Principal 123"));

        assertNotNull(resultado);
        assertEquals("PREPARANDO", resultado.getEstado());
        assertEquals("Av. Principal 123", resultado.getDireccionEntrega());
        verify(repository, times(1)).save(any(Despacho.class));
    }

    @Test
    @DisplayName("registrarDespacho() debe lanzar excepcion si ms-pedidos falla")
    void registrarDespacho_debeLanzarExcepcion_siFallaFeignPedidos() {
        when(pedidoClient.obtenerPedidoPorId(99L)).thenThrow(new RuntimeException("Conexion fallida"));
        assertThrows(RuntimeException.class,
                () -> despachoService.registrarDespacho(new DespachoRequestDTO(99L, "Calle X")));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("registrarDespacho() debe continuar si notificacion falla")
    void registrarDespacho_debeContinuar_siNotificacionFalla() {
        when(pedidoClient.obtenerPedidoPorId(1L)).thenReturn(pedidoResponse);
        when(repository.save(any(Despacho.class))).thenReturn(despachoEjemplo);
        doThrow(new RuntimeException("Notificacion no disponible")).when(notificacionClient).enviar(any());

        DespachoResponseDTO resultado = despachoService.registrarDespacho(
                new DespachoRequestDTO(1L, "Av. Principal 123"));

        assertNotNull(resultado);
        assertEquals("PREPARANDO", resultado.getEstado());
    }

    @Test
    @DisplayName("obtenerPorId() debe retornar despacho cuando existe")
    void obtenerPorId_debeRetornarDespacho() {
        when(repository.findById(1L)).thenReturn(Optional.of(despachoEjemplo));
        DespachoResponseDTO resultado = despachoService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getPedidoId());
    }

    @Test
    @DisplayName("obtenerPorId() debe lanzar excepcion cuando no existe")
    void obtenerPorId_debeLanzarExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(DespachoNotFoundException.class, () -> despachoService.obtenerPorId(99L));
    }
}
