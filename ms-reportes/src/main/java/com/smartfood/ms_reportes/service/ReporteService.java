package com.smartfood.ms_reportes.service;

import com.smartfood.ms_reportes.client.PedidoClient;
import com.smartfood.ms_reportes.dto.PedidoResponseDTO;
import com.smartfood.ms_reportes.dto.ReporteRequestDTO;
import com.smartfood.ms_reportes.dto.ReporteResponseDTO;
import com.smartfood.ms_reportes.model.Reporte;
import com.smartfood.ms_reportes.repository.ReporteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteService {
    private static final Logger logger = LoggerFactory.getLogger(ReporteService.class);
    private final ReporteRepository repository;
    private final PedidoClient pedidoClient;

    public ReporteService(ReporteRepository repository, PedidoClient pedidoClient) {
        this.repository = repository;
        this.pedidoClient = pedidoClient;
    }

    @Transactional
    public ReporteResponseDTO generarReporte(ReporteRequestDTO dto) {
        logger.info("Recopilando datos desde ms-pedidos mediante OpenFeign para el reporte: {}", dto.getTipoReporte());
        String estadoFinal = "COMPLETADO";

        try {
            List<PedidoResponseDTO> pedidos = pedidoClient.obtenerTodosLosPedidos();
            logger.info("Se recuperaron {} pedidos exitosamente para el calculo del reporte", pedidos.size());
        } catch (Exception e) {
            logger.error("Error al comunicarse con ms-pedidos: {}. El reporte cambiara a FALLIDO", e.getMessage());
            estadoFinal = "FALLIDO";
        }

        Reporte reporte = new Reporte(null, dto.getTipoReporte(), LocalDateTime.now(), estadoFinal);
        Reporte guardado = repository.save(reporte);
        return new ReporteResponseDTO(guardado.getId(), guardado.getTipoReporte(), guardado.getFechaGeneracion(), guardado.getEstado());
    }
}