package com.smartfood.ms_promociones.repository;

import com.smartfood.ms_promociones.model.Promocion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests del repositorio de promociones con H2")
class PromocionRepositoryTest {

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager.persistAndFlush(new Promocion(null, "PROMO10", 10, true));
        entityManager.persistAndFlush(new Promocion(null, "BIENVENIDO20", 20, true));
        entityManager.persistAndFlush(new Promocion(null, "EXPIRADO", 15, false));
    }

    @Test
    @DisplayName("findByCodigoAndActivaTrue() debe retornar promocion activa")
    void findByCodigoAndActivaTrue_debeRetornarActiva() {
        Optional<Promocion> resultado = promocionRepository.findByCodigoAndActivaTrue("PROMO10");
        assertTrue(resultado.isPresent());
        assertEquals(10, resultado.get().getPorcentajeDescuento());
        assertTrue(resultado.get().getActiva());
    }

    @Test
    @DisplayName("findByCodigoAndActivaTrue() debe retornar vacio si esta inactiva")
    void findByCodigoAndActivaTrue_debeRetornarVacio_siInactiva() {
        Optional<Promocion> resultado = promocionRepository.findByCodigoAndActivaTrue("EXPIRADO");
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("findByCodigoAndActivaTrue() debe retornar vacio si codigo no existe")
    void findByCodigoAndActivaTrue_debeRetornarVacio_siNoExiste() {
        assertFalse(promocionRepository.findByCodigoAndActivaTrue("NOEXISTE").isPresent());
    }

    @Test
    @DisplayName("save() debe persistir nueva promocion")
    void save_debePersistir() {
        Promocion nueva = promocionRepository.save(new Promocion(null, "NUEVA50", 50, true));
        assertNotNull(nueva.getId());
        assertEquals("NUEVA50", nueva.getCodigo());
    }

    @Test
    @DisplayName("findAll() debe retornar todas las promociones")
    void findAll_debeRetornarTodas() {
        assertEquals(3, promocionRepository.findAll().size());
    }
}
