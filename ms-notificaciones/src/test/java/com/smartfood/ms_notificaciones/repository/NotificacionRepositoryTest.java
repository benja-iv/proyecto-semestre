package com.smartfood.ms_notificaciones.repository;

import com.smartfood.ms_notificaciones.model.Notificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests del repositorio de notificaciones con H2")
class NotificacionRepositoryTest {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager.persistAndFlush(new Notificacion(null, 1L, "Pedido recibido", LocalDateTime.now()));
        entityManager.persistAndFlush(new Notificacion(null, 1L, "Pago procesado", LocalDateTime.now()));
        entityManager.persistAndFlush(new Notificacion(null, 2L, "Tu pedido llega hoy", LocalDateTime.now()));
    }

    @Test
    @DisplayName("findByClienteId() debe retornar notificaciones del cliente")
    void findByClienteId_debeRetornarNotificaciones() {
        List<Notificacion> resultado = notificacionRepository.findByClienteId(1L);
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("findByClienteId() debe retornar lista vacia si cliente sin notificaciones")
    void findByClienteId_listaVacia() {
        List<Notificacion> resultado = notificacionRepository.findByClienteId(99L);
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("save() debe persistir nueva notificacion")
    void save_debePersistir() {
        Notificacion nueva = notificacionRepository.save(
                new Notificacion(null, 3L, "Bienvenido", LocalDateTime.now()));
        assertNotNull(nueva.getId());
        assertEquals(3L, nueva.getClienteId());
    }

    @Test
    @DisplayName("findAll() debe retornar todos los registros")
    void findAll_debeRetornarTodos() {
        assertEquals(3, notificacionRepository.findAll().size());
    }
}
