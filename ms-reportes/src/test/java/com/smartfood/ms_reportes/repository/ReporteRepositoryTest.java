package com.smartfood.ms_reportes.repository;

import com.smartfood.ms_reportes.model.Reporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests del repositorio de reportes con H2")
class ReporteRepositoryTest {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Reporte reporte1;

    @BeforeEach
    void setUp() {
        reporte1 = entityManager.persistAndFlush(
                new Reporte(null, "VENTAS_DIARIAS", LocalDateTime.now(), "COMPLETADO"));
        entityManager.persistAndFlush(
                new Reporte(null, "STOCK_CRITICO", LocalDateTime.now(), "FALLIDO"));
    }

    @Test
    @DisplayName("findAll() debe retornar todos los reportes")
    void findAll_debeRetornarTodos() {
        List<Reporte> reportes = reporteRepository.findAll();
        assertEquals(2, reportes.size());
    }

    @Test
    @DisplayName("findById() debe retornar reporte cuando existe")
    void findById_debeRetornarReporte() {
        Optional<Reporte> resultado = reporteRepository.findById(reporte1.getId());
        assertTrue(resultado.isPresent());
        assertEquals("VENTAS_DIARIAS", resultado.get().getTipoReporte());
        assertEquals("COMPLETADO", resultado.get().getEstado());
    }

    @Test
    @DisplayName("findById() debe retornar vacio cuando no existe")
    void findById_debeRetornarVacio() {
        assertFalse(reporteRepository.findById(99999L).isPresent());
    }

    @Test
    @DisplayName("save() debe persistir nuevo reporte")
    void save_debePersistir() {
        Reporte nuevo = reporteRepository.save(
                new Reporte(null, "CLIENTES_ACTIVOS", LocalDateTime.now(), "COMPLETADO"));
        assertNotNull(nuevo.getId());
        assertEquals("CLIENTES_ACTIVOS", nuevo.getTipoReporte());
    }
}
