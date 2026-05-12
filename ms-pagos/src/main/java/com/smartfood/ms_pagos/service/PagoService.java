package com.smartfood.ms_pagos.service;

import org.springframework.stereotype.Service;
import com.smartfood.ms_pagos.dto.PagoRequestDTO;
import com.smartfood.ms_pagos.dto.PagoResponseDTO;
import com.smartfood.ms_pagos.model.Pago;
import com.smartfood.ms_pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoResponseDTO procesarPago(PagoRequestDTO dto) {
        Pago pago = new Pago(null, dto.getPedidoId(), dto.getMonto(), dto.getMetodoPago(), "COMPLETADO", LocalDateTime.now());
        Pago guardado = pagoRepository.save(pago);
        
        return new PagoResponseDTO(guardado.getId(), guardado.getPedidoId(), guardado.getMonto(), guardado.getMetodoPago(), guardado.getEstado(), guardado.getFechaPago());
    }

    
}