package com.smartfood.ms_pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
    private Long id;
    private String cliente;
    private String descripcion;
    private Double total;
    private String estado;
    private LocalDateTime fechaCreacion;
}