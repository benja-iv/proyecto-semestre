package com.smartfood.ms_despacho.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespachoResponseDTO {
    private Long id;
    private Long pedidoId;
    private String estado;
    private String direccionEntrega;
    private LocalDateTime fechaActualizacion;
}