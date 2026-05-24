package com.smartfood.ms_promociones.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromocionRequestDTO {
    @NotBlank(message = "El codigo es obligatorio")
    private String codigo;

    @NotNull(message = "El porcentaje es obligatorio")
    @Min(value = 1, message = "Minimo 1% de descuento")
    private Integer porcentajeDescuento;
}