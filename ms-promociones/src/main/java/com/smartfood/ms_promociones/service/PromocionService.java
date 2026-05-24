package com.smartfood.ms_promociones.service;

import com.smartfood.ms_promociones.dto.PromocionRequestDTO;
import com.smartfood.ms_promociones.dto.PromocionResponseDTO;
import com.smartfood.ms_promociones.exception.PromocionInvalidaException;
import com.smartfood.ms_promociones.model.Promocion;
import com.smartfood.ms_promociones.repository.PromocionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PromocionService {
    private static final Logger logger = LoggerFactory.getLogger(PromocionService.class);
    private final PromocionRepository repository;

    public PromocionService(PromocionRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public PromocionResponseDTO validarCodigo(String codigo) {
        logger.info("Validando codigo: {}", codigo);
        Promocion promo = repository.findByCodigoAndActivaTrue(codigo)
                .orElseThrow(() -> new PromocionInvalidaException("Codigo promocional no valido o expirado"));
        return new PromocionResponseDTO(promo.getId(), promo.getCodigo(), promo.getPorcentajeDescuento(), promo.getActiva());
    }

    @Transactional
    public PromocionResponseDTO crearPromocion(PromocionRequestDTO dto) {
        Promocion promo = new Promocion(null, dto.getCodigo(), dto.getPorcentajeDescuento(), true);
        Promocion guardada = repository.save(promo);
        return new PromocionResponseDTO(guardada.getId(), guardada.getCodigo(), guardada.getPorcentajeDescuento(), guardada.getActiva());
    }
}