package com.smartfood.ms_despacho.repository;

import com.smartfood.ms_despacho.model.Despacho;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests del repositorio de despachos con H2")
class DespachoRepositoryTest {

    @Autowired
    private DespachoRepository despachoRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Despacho despacho1;

    @BeforeEach
    void setUp() {
        despacho1 = entityManager.persistAndFlush(
                new Despacho(null, 1L, "EN_CAMINO", "Av. Principal 123", LocalDateTime.now()));
        entityManager.persistAndFlush(
                new Despacho(null, 2L, "PREPARANDO", "Calle Los Olivos 456", LocalDateTime.now()));
    }

    @Test
    @DisplayName("findAll() debe retornar todos los despachos")
    void findAll_debeRetornarTodos() {
        assertEquals(2, despachoRepository.findAll().size());
    }

    @Test
    @DisplayName("findById() debe retornar despacho cuando existe")
    void findById_debeRetornarDespacho() {
        Optional<Despacho> resultado = despachoRepository.findById(despacho1.getId());
        assertTrue(resultado.isPresent());
        assertEquals("EN_CAMINO", resultado.get().getEstado());
    }

    @Test
    @DisplayName("findByPedidoId() debe retornar despacho del pedido")
    void findByPedidoId_debeRetornar() {
        Optional<Despacho> resultado = despachoRepository.findByPedidoId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Av. Principal 123", resultado.get().getDireccionEntrega());
    }

    @Test
    @DisplayName("findByPedidoId() debe retornar vacio si no existe")
    void findByPedidoId_debeRetornarVacio() {
        assertFalse(despachoRepository.findByPedidoId(99L).isPresent());
    }

    @Test
    @DisplayName("save() debe persistir nuevo despacho")
    void save_debePersistir() {
        Despacho nuevo = despachoRepository.save(
                new Despacho(null, 3L, "PREPARANDO", "Calle Nueva 789", LocalDateTime.now()));
        assertNotNull(nuevo.getId());
        assertEquals(3L, nuevo.getPedidoId());
    }
}
