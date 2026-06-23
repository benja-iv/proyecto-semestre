package com.smartfood.ms_promociones.service;

import com.smartfood.ms_promociones.dto.PromocionRequestDTO;
import com.smartfood.ms_promociones.dto.PromocionResponseDTO;
import com.smartfood.ms_promociones.exception.PromocionInvalidaException;
import com.smartfood.ms_promociones.model.Promocion;
import com.smartfood.ms_promociones.repository.PromocionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios de PromocionService")
class PromocionServiceTest {

    @Mock
    private PromocionRepository repository;

    @InjectMocks
    private PromocionService promocionService;

    private Promocion promocionEjemplo;

    @BeforeEach
    void setUp() {
        promocionEjemplo = new Promocion(1L, "PROMO10", 10, true);
    }

    @Test
    @DisplayName("validarCodigo() debe retornar promocion cuando codigo es valido y activo")
    void validarCodigo_debeRetornarPromocion() {
        when(repository.findByCodigoAndActivaTrue("PROMO10")).thenReturn(Optional.of(promocionEjemplo));
        PromocionResponseDTO resultado = promocionService.validarCodigo("PROMO10");
        assertNotNull(resultado);
        assertEquals("PROMO10", resultado.getCodigo());
        assertEquals(10, resultado.getPorcentajeDescuento());
        assertTrue(resultado.getActiva());
    }

    @Test
    @DisplayName("validarCodigo() debe lanzar excepcion si codigo no existe o esta inactivo")
    void validarCodigo_debeLanzarExcepcion_codigoInvalido() {
        when(repository.findByCodigoAndActivaTrue("INVALIDO")).thenReturn(Optional.empty());
        assertThrows(PromocionInvalidaException.class, () -> promocionService.validarCodigo("INVALIDO"));
    }

    @Test
    @DisplayName("validarCodigo() debe lanzar excepcion si promocion esta inactiva")
    void validarCodigo_debeLanzarExcepcion_promocionInactiva() {
        when(repository.findByCodigoAndActivaTrue("EXPIRADO")).thenReturn(Optional.empty());
        assertThrows(PromocionInvalidaException.class, () -> promocionService.validarCodigo("EXPIRADO"));
    }

    @Test
    @DisplayName("crearPromocion() debe guardar y retornar la promocion activa")
    void crearPromocion_debeGuardarYRetornar() {
        when(repository.save(any(Promocion.class))).thenReturn(promocionEjemplo);
        PromocionResponseDTO resultado = promocionService.crearPromocion(new PromocionRequestDTO("PROMO10", 10));
        assertNotNull(resultado);
        assertEquals("PROMO10", resultado.getCodigo());
        assertTrue(resultado.getActiva());
        verify(repository, times(1)).save(any(Promocion.class));
    }
}
