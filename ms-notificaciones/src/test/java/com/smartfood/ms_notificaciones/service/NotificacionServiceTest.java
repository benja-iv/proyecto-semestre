package com.smartfood.ms_notificaciones.service;

import com.smartfood.ms_notificaciones.dto.NotificacionRequestDTO;
import com.smartfood.ms_notificaciones.dto.NotificacionResponseDTO;
import com.smartfood.ms_notificaciones.model.Notificacion;
import com.smartfood.ms_notificaciones.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios de NotificacionService")
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository repository;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacionEjemplo;

    @BeforeEach
    void setUp() {
        notificacionEjemplo = new Notificacion(1L, 1L, "Tu pedido fue recibido", LocalDateTime.now());
    }

    @Test
    @DisplayName("enviarNotificacion() debe guardar y retornar la notificacion")
    void enviarNotificacion_debeGuardarYRetornar() {
        when(repository.save(any(Notificacion.class))).thenReturn(notificacionEjemplo);
        NotificacionResponseDTO resultado = notificacionService.enviarNotificacion(
                new NotificacionRequestDTO(1L, "Tu pedido fue recibido"));
        assertNotNull(resultado);
        assertEquals(1L, resultado.getClienteId());
        assertEquals("Tu pedido fue recibido", resultado.getMensaje());
        verify(repository, times(1)).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("obtenerPorCliente() debe retornar lista de notificaciones del cliente")
    void obtenerPorCliente_debeRetornarLista() {
        when(repository.findByClienteId(1L)).thenReturn(List.of(notificacionEjemplo));
        List<NotificacionResponseDTO> resultado = notificacionService.obtenerPorCliente(1L);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tu pedido fue recibido", resultado.get(0).getMensaje());
    }

    @Test
    @DisplayName("obtenerPorCliente() debe retornar lista vacia si no hay notificaciones")
    void obtenerPorCliente_listaVacia() {
        when(repository.findByClienteId(99L)).thenReturn(List.of());
        List<NotificacionResponseDTO> resultado = notificacionService.obtenerPorCliente(99L);
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}
