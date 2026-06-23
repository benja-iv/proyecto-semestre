package com.smartfood.ms_reportes.service;

import com.smartfood.ms_reportes.client.PedidoClient;
import com.smartfood.ms_reportes.dto.PedidoResponseDTO;
import com.smartfood.ms_reportes.dto.ReporteRequestDTO;
import com.smartfood.ms_reportes.dto.ReporteResponseDTO;
import com.smartfood.ms_reportes.model.Reporte;
import com.smartfood.ms_reportes.repository.ReporteRepository;
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
@DisplayName("Tests unitarios de ReporteService")
class ReporteServiceTest {

    @Mock
    private ReporteRepository repository;

    @Mock
    private PedidoClient pedidoClient;

    @InjectMocks
    private ReporteService reporteService;

    private Reporte reporteEjemplo;

    @BeforeEach
    void setUp() {
        reporteEjemplo = new Reporte(1L, "VENTAS_DIARIAS", LocalDateTime.now(), "COMPLETADO");
    }

    @Test
    @DisplayName("generarReporte() debe retornar COMPLETADO cuando ms-pedidos responde")
    void generarReporte_debeRetornarCompletado() {
        when(pedidoClient.obtenerTodosLosPedidos()).thenReturn(
                List.of(new PedidoResponseDTO(1L, 1L, 15000.0, "PAGADO")));
        when(repository.save(any(Reporte.class))).thenReturn(reporteEjemplo);

        ReporteResponseDTO resultado = reporteService.generarReporte(new ReporteRequestDTO("VENTAS_DIARIAS"));

        assertNotNull(resultado);
        assertEquals("COMPLETADO", resultado.getEstado());
        assertEquals("VENTAS_DIARIAS", resultado.getTipoReporte());
        verify(repository, times(1)).save(any(Reporte.class));
    }

    @Test
    @DisplayName("generarReporte() debe retornar FALLIDO cuando ms-pedidos falla")
    void generarReporte_debeRetornarFallido_cuandoFeignFalla() {
        when(pedidoClient.obtenerTodosLosPedidos()).thenThrow(new RuntimeException("Servicio no disponible"));
        Reporte reporteFallido = new Reporte(2L, "VENTAS_DIARIAS", LocalDateTime.now(), "FALLIDO");
        when(repository.save(any(Reporte.class))).thenReturn(reporteFallido);

        ReporteResponseDTO resultado = reporteService.generarReporte(new ReporteRequestDTO("VENTAS_DIARIAS"));

        assertNotNull(resultado);
        assertEquals("FALLIDO", resultado.getEstado());
    }

    @Test
    @DisplayName("generarReporte() debe guardar reporte aunque lista de pedidos sea vacia")
    void generarReporte_debeGuardar_conListaVacia() {
        when(pedidoClient.obtenerTodosLosPedidos()).thenReturn(List.of());
        when(repository.save(any(Reporte.class))).thenReturn(reporteEjemplo);

        ReporteResponseDTO resultado = reporteService.generarReporte(new ReporteRequestDTO("STOCK_CRITICO"));

        assertNotNull(resultado);
        verify(repository, times(1)).save(any(Reporte.class));
    }
}
