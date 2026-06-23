package com.smartfood.ms_inventario.repository;

import com.smartfood.ms_inventario.model.Inventario;
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
@DisplayName("Tests del repositorio de inventario con H2")
class InventarioRepositoryTest {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Inventario inv1;

    @BeforeEach
    void setUp() {
        inv1 = entityManager.persistAndFlush(new Inventario(null, 1L, 50, LocalDateTime.now()));
        entityManager.persistAndFlush(new Inventario(null, 2L, 30, LocalDateTime.now()));
    }

    @Test
    @DisplayName("findByProductoId() debe retornar inventario cuando existe")
    void findByProductoId_debeRetornar() {
        Optional<Inventario> resultado = inventarioRepository.findByProductoId(1L);
        assertTrue(resultado.isPresent());
        assertEquals(50, resultado.get().getCantidadDisponible());
    }

    @Test
    @DisplayName("findByProductoId() debe retornar vacio cuando no existe")
    void findByProductoId_debeRetornarVacio() {
        assertFalse(inventarioRepository.findByProductoId(99L).isPresent());
    }

    @Test
    @DisplayName("findAll() debe retornar todos los registros")
    void findAll_debeRetornarTodos() {
        assertEquals(2, inventarioRepository.findAll().size());
    }

    @Test
    @DisplayName("save() debe persistir nuevo inventario")
    void save_debePersistir() {
        Inventario nuevo = inventarioRepository.save(new Inventario(null, 3L, 20, LocalDateTime.now()));
        assertNotNull(nuevo.getId());
        assertEquals(3L, nuevo.getProductoId());
    }
}
