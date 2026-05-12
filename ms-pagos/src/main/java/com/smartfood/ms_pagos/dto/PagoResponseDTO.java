package com.smartfood.ms_pagos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PagoResponseDTO {
    private Long id;
    private Long pedidoId;
    private Double monto;
    private String metodoPago;
    private String estado;
    private LocalDateTime fechaPago;

    public PagoResponseDTO(Long id, Long pedidoId, Double monto, String metodoPago, String estado, LocalDateTime fechaPago) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.fechaPago = fechaPago;
    }
}