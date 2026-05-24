package com.smartfood.ms_notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionRequestDTO {
    @NotNull(message = "El cliente ID es obligatorio")
    private Long clienteId;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;
}