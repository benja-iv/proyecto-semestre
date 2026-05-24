package com.smartfood.ms_promociones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromocionResponseDTO {
    private Long id;
    private String codigo;
    private Integer porcentajeDescuento;
    private Boolean activa;
}