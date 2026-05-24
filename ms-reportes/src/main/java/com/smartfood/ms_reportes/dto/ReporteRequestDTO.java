package com.smartfood.ms_reportes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteRequestDTO {
    @NotBlank(message = "El tipo de reporte es obligatorio")
    private String tipoReporte;
}