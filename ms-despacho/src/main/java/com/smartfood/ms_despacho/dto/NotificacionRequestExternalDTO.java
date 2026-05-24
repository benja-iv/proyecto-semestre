package com.smartfood.ms_despacho.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionRequestExternalDTO {
    private Long clienteId;
    private String mensaje;
}