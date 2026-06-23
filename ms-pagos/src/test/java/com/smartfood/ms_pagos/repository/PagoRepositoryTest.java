package com.smartfood.ms_pagos.repository;

import com.smartfood.ms_pagos.model.Pago;
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
@DisplayName("Tests del repositorio de pagos con H2")
class PagoRepositoryTest {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Pago pago1;

    @BeforeEach
    void setUp() {
        pago1 = entityManager.persistAndFlush(new Pago(null, 1L, 15000.0, "TARJETA_CREDITO", "APROBADO", LocalDateTime.now()));
        entityManager.persistAndFlush(new Pago(null, 2L, 8500.0, "DEBITO", "APROBADO", LocalDateTime.now()));
    }

    @Test
    @DisplayName("findAll() debe retornar todos los pagos")
    void findAll_debeRetornarTodos() {
        assertEquals(2, pagoRepository.findAll().size());
    }

    @Test
    @DisplayName("findById() debe retornar pago cuando existe")
    void findById_debeRetornarPago() {
        Optional<Pago> resultado = pagoRepository.findById(pago1.getId());
        assertTrue(resultado.isPresent());
        assertEquals("APROBADO", resultado.get().getEstado());
    }

    @Test
    @DisplayName("findById() debe retornar vacio cuando no existe")
    void findById_debeRetornarVacio() {
        assertFalse(pagoRepository.findById(99999L).isPresent());
    }

    @Test
    @DisplayName("save() debe persistir nuevo pago")
    void save_debePersistirPago() {
        Pago nuevo = pagoRepository.save(new Pago(null, 3L, 25000.0, "EFECTIVO", "PENDIENTE", LocalDateTime.now()));
        assertNotNull(nuevo.getId());
        assertEquals("PENDIENTE", nuevo.getEstado());
    }
}
