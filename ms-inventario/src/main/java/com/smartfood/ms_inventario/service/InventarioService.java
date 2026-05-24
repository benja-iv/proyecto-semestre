package com.smartfood.ms_inventario.service;

import com.smartfood.ms_inventario.dto.InventarioRequestDTO;
import com.smartfood.ms_inventario.dto.InventarioResponseDTO;
import com.smartfood.ms_inventario.exception.InventarioNotFoundException;
import com.smartfood.ms_inventario.model.Inventario;
import com.smartfood.ms_inventario.repository.InventarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InventarioService {

    private static final Logger logger = LoggerFactory.getLogger(InventarioService.class);
    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Transactional(readOnly = true)
    public InventarioResponseDTO consultarPorProductoId(Long productoId) {
        logger.info("Consultando inventario para producto ID: {}", productoId);
        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> {
                    logger.warn("Inventario no encontrado para producto ID: {}", productoId);
                    return new InventarioNotFoundException(productoId);
                });
        return mapearAResponseDTO(inventario);
    }

    @Transactional
    public InventarioResponseDTO registrarOActualizar(InventarioRequestDTO dto) {
        logger.info("Actualizando inventario para producto ID: {}", dto.getProductoId());
        Inventario inventario = inventarioRepository.findByProductoId(dto.getProductoId())
                .orElse(new Inventario());
        
        inventario.setProductoId(dto.getProductoId());
        inventario.setCantidadDisponible(dto.getCantidadDisponible());
        inventario.setUltimaActualizacion(LocalDateTime.now());
        
        Inventario guardado = inventarioRepository.save(inventario);
        logger.info("Inventario guardado. Producto ID: {}", guardado.getProductoId());
        
        return mapearAResponseDTO(guardado);
    }

    private InventarioResponseDTO mapearAResponseDTO(Inventario i) {
        return new InventarioResponseDTO(i.getId(), i.getProductoId(), i.getCantidadDisponible(), i.getUltimaActualizacion());
    }
}